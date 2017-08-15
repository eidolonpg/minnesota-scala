package com.nerdery.demo.tc

import monix.eval.Task

/**
  * Type class representing data that can be validated.
  * @tparam T The type of data that can be validated.
  * @tparam Error The type of error produced when the data is validated.
  */
trait CanValidate[T, Error] {
  def validate(t: T): Task[Either[Error, CanValidate.Valid]]
}

object CanValidate {
  /**
    * Token type/instance used to provide semantic return values in the case of successful validation.
    */
  sealed trait Valid
  case object Valid extends Valid
}

/**
  * API for performing validation on any data that provides an implementation of the CanValidate type class.
  */
object Validation {
  /**
    * Validate some data.
    *
    * @param t The data to validate.
    * @param ev Evidence that the data can be validated (implicit instance of the CanValidate type class)
    * @tparam T The type of data being validated.
    * @tparam Error The type of error produced by the validation.
    * @return Task representing either an error or an indication that the data is valid.
    */
  def validate[T, Error](t: T)(implicit ev: CanValidate[T, Error]): Task[Either[Error, CanValidate.Valid]] = {
    ev.validate(t)
  }
}
