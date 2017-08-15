package com.nerdery.demo.error

object FooErrorTransformer {
  /**
    * This function allows us to transform a FooError into our "display" representation.
    * We are guaranteed to handle every case of FooError by the compiler since we've treated
    * that trait as an enumeration. Errors can be handled at different layers of the application
    * in this fashion.
    *
    * @param error The error to transform.
    * @return List of strings which hold the error messages relevant to the error.
    */
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
