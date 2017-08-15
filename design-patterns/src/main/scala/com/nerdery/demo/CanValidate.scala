package com.nerdery.demo

import monix.eval.Task

trait CanValidate[T, Error, Action] {
  def validate(t: T): Task[Either[Error, CanValidate.Valid]]
}

object CanValidate {
  sealed trait Valid
  case object Valid extends Valid
}