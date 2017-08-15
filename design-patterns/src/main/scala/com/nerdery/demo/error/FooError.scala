package com.nerdery.demo.error

import com.nerdery.demo.domain.Bar
import monix.eval.Task

/**
  * Enumeration of all error types within the Foo domain. In a larger application we might choose
  * to expand upon this to have a top-level application error type with things like FooError,
  * BarError, and more that represent each domain.
  */
sealed trait FooError extends Throwable

object FooError {
  /**
    * Helper that lifts an error from an Either into the Task, forcing the Task to fail. This allows
    * the Task to short-circuit next time map/flatMap are called. The return value is a Task that
    * represents the computed value, and may be a failed task with the same error that was in the
    * Either.
    *
    * Note that this is specific to FooError, we could easily make it any Throwable.
    *
    * @param t The Task to transform.
    * @tparam T The result type.
    * @return Task (potentially failed) representing the result.
    */
  def liftFromTask[T](t: Task[Either[FooError, T]]): Task[T] = {
    t.flatMap {
      case Left(e) => Task.raiseError(e)
      case Right(value) => Task.now(value)
    }
  }

  case class Aggregate(errors: Seq[FooError]) extends FooError

  /*
  We can read below to understand the complete set of errors within the domain of Foo.
   */

  case class NoSuchBar(barId: Long) extends FooError
  case class ValueBelowMinimum(bar: Bar, value: Int, minimum: Int) extends FooError
  case class ValueAboveMaximum(bar: Bar, value: Int, maximum: Int) extends FooError
  case class NameNotUnique(name: String) extends FooError
}
