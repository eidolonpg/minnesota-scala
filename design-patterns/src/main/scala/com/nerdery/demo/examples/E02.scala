package com.nerdery.demo.examples

import com.nerdery.demo.domain.{Bar, EntityReference, EntityVersion, Foo}
import monix.eval.Task

object E02 {
  def createFoo(foo: Foo): Task[(Foo, EntityVersion)] = {
    for {
      bars <- getBars(foo)
      _ <- captureErrors(validate(foo, bars))
      newVersion <- persist(foo)
      result <- handleEvent(foo, newVersion)
    } yield result
  }

  private def getBars(foo: Foo): Task[Seq[EntityReference[Bar]]] = ???

  private def validate(foo: Foo, bars: Seq[EntityReference[Bar]]): Task[Either[Throwable, Unit]] = ???

  private def persist(foo: Foo): Task[EntityVersion] = ???

  private def handleEvent(foo: Foo, version: EntityVersion): Task[(Foo, EntityVersion)] = ???

  private def captureErrors[A](t: Task[Either[Throwable, A]]): Task[A] = ???
}
