package com.nerdery.demo.examples

import com.nerdery.demo.domain.{Bar, EntityReference, Foo}
import com.nerdery.demo.error.FooError
import monix.eval.Task

object E06 {
  trait CanValidate[T, Error] {
    def validate(t: T): Task[Either[Error, CanValidate.Valid]]
  }

  object CanValidate {
    sealed trait Valid
    case object Valid extends Valid
  }

  object Validation {
    def validate[T, Error](t: T)(implicit ev: CanValidate[T, Error]): Task[Either[Error, CanValidate.Valid]] = {
      ev.validate(t)
    }
  }

  implicit object FooCanValidate extends CanValidate[(Foo, Seq[EntityReference[Bar]]), FooError] {
    override def validate(t: (Foo, Seq[EntityReference[Bar]])): Task[Either[FooError, CanValidate.Valid]] = {
      val (foo, bars) = t
      val errors = for {
        valueErrors <- validateValues(foo, bars)
        uniquenessErrors <- validateUniqueness(foo)
      } yield valueErrors ++ uniquenessErrors

      errors.flatMap {
        case Nil => Task.now(Right(CanValidate.Valid))
        case es => Task.now(Left(FooError.Aggregate(es)))
      }
    }

    def validateUniqueness(foo: Foo): Task[Seq[FooError]] = ???

    def validateValues(foo: Foo, bars: Seq[EntityReference[Bar]]): Task[Seq[FooError]] = ???
  }
}
