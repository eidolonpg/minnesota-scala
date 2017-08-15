package com.nerdery.demo

import com.nerdery.demo.Foos.FooActions
import monix.eval.Task

case class Foo(id: Long, name: String, values: Seq[Bar.Value])

object Foo {
  def barIds(foo: Foo): Seq[Long] = foo.values.map(_.bar.entityId)

  def barReferences(foo: Foo): Seq[EntityReference[Bar]] = foo.values.map(_.bar)

  implicit object FooEq extends cats.Eq[Foo] {
    override def eqv(x: Foo, y: Foo): Boolean = x.id == y.id
  }

  implicit object FooCanValidateCreate extends CanValidate[(Foo, Seq[EntityReference[Bar]]), FooError, FooActions.Create] {
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
