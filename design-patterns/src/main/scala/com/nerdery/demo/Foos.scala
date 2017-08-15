package com.nerdery.demo

import monix.eval.Task

object Foos {
  import Foo._

  def create(foo: Foo): Task[(Foo, EntityVersion)] = {
    for {
      bars <- Setup.retrieveBars(foo)
      _ <- FooError.liftFromTask(Validation.validate((foo, bars)))
      newVersion <- Persistence.save(foo)
    } yield handleFooCreatedEvent(foo, newVersion)
  }

  object Setup {
    def retrieveBars(foo: Foo): Task[Seq[EntityReference[Bar]]] = ???
  }

  /*object Validation {
    def validate(foo: Foo, bars: Seq[EntityReference[Bar]]): Task[Either[FooError, Valid]] = {
      val errors = for {
        valueErrors <- validateValues(foo, bars)
        uniquenessErrors <- validateUniqueness(foo)
      } yield valueErrors ++ uniquenessErrors

      errors.flatMap {
        case Nil => Task.now(Right(Valid))
        case es => Task.now(Left(FooError.Aggregate(es)))
      }
    }

    def validateUniqueness(foo: Foo): Task[Seq[FooError]] = ???

    def validateValues(foo: Foo, bars: Seq[EntityReference[Bar]]): Task[Seq[FooError]] = ???

    sealed trait Valid
    case object Valid extends Valid
  }*/

  object Persistence {
    def save(foo: Foo): Task[EntityVersion] = ???
  }

  private def handleFooCreatedEvent(foo: Foo, newVersion: EntityVersion): (Foo, EntityVersion) = {
    (foo, newVersion)
  }

  sealed trait FooActions
  object FooActions {
    sealed trait Create extends FooActions
    case object Create extends Create

    sealed trait Update extends FooActions
    case object Update extends Update
  }
}
