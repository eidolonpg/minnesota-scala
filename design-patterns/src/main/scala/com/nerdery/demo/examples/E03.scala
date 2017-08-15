package com.nerdery.demo.examples

import com.nerdery.demo.domain.Bar

object E03 {
  sealed trait FooError extends Throwable

  object FooError {
    case class Aggregate(errors: Seq[FooError]) extends FooError
    case class NoSuchBar(barId: Long) extends FooError
    case class ValueBelowMinimum(bar: Bar, value: Int, minimum: Int) extends FooError
    case class ValueAboveMaximum(bar: Bar, value: Int, maximum: Int) extends FooError
    case class NameNotUnique(name: String) extends FooError
  }

  object FooErrorTransformer {
    def asMessage(error: FooError): Seq[String] = {
      error match {
        case FooError.Aggregate(errors) => errors.flatMap(asMessage)
        case FooError.NoSuchBar(barId) => s"The specified bar [$barId] does not exist." :: Nil
        case FooError.ValueBelowMinimum(bar, value, minimum) => s"Specified value [$value] for bar [${bar.id}|${bar.name}] which is below the minimum value [$minimum]" :: Nil
        case FooError.ValueAboveMaximum(bar, value, maximum) => s"Specified value [$value] for bar [${bar.id}|${bar.name}] which is above the maximum value [$maximum]" :: Nil
        case FooError.NameNotUnique(name) => s"The specified Foo name [$name] is already taken." :: Nil
      }
    }
  }
}
