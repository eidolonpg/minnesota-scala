package com.nerdery.demo

import monix.eval.Task

object Validation {
  def validate[T, Error, Action](t: T)(implicit ev: CanValidate[T, Error, Action]): Task[Either[Error, CanValidate.Valid]] = {
    ev.validate(t)
  }
}
