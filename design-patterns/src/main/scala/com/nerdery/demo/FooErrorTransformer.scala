package com.nerdery.demo

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
