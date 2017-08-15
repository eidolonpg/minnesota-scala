package com.nerdery.demo

import monix.eval.Task

sealed trait FooError extends Throwable

object FooError {
  def liftFromTask[T](t: Task[Either[FooError, T]]): Task[T] = {
    t.flatMap {
      case Left(e) => Task.raiseError(e)
      case Right(value) => Task.now(value)
    }
  }

  case class Aggregate(errors: Seq[FooError]) extends FooError
  case class NoSuchBar(barId: Long) extends FooError
  case class ValueBelowMinimum(bar: Bar, value: Int, minimum: Int) extends FooError
  case class ValueAboveMaximum(bar: Bar, value: Int, maximum: Int) extends FooError
  case class NameNotUnique(name: String) extends FooError
}
