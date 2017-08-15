package com.nerdery.demo

import com.nerdery.demo.domain.{Bar, EntityReference, EntityVersion, Foo}
import com.nerdery.demo.error.FooError
import com.nerdery.demo.tc.{Events, Persistence, Validation}
import monix.eval.Task

object Foos {
  import Foo._

  /**
    * This is the top-level function that encapsulates our business logic for creating a Foo.
    *
    * Note: We return the original Foo here, but what if we want the Foo returned with its
    * references fully-populated? That's not a problem -- we have the bars to make that happen.
    *
    * Rough English interpretation of the function:
    * - This function builds a computation that will persist a valid Foo to the database, and accepts that Foo as input.
    * - First we retrieve all of the Bars needed to validate the Foo.
    * - Next we validate the Foo against its Bars, capturing any errors that occur to short-circuit
    * - We then persist the Foo to the database
    * - ... and Finally report the event of creating a new Foo before returning the new Foo version.
    *
    * @param foo The Foo to create.
    * @return Task representing the input Foo along with its new Version.
    */
  def createFoo(foo: Foo): Task[(Foo, EntityVersion)] = {
    for {
      bars <- Setup.retrieveBars(foo)
      _ <- FooError.liftFromTask(Validation.validate((foo, bars)))
      newVersion <- Persistence.persist(foo)
      result <- Events.event(foo, newVersion)
    } yield result
  }

  /**
    * This object feels a little bit out of place, can we do better? Perhaps this is simply a standard
    * data layer function for Foos. We would need to decide where those live, or if there is some other
    * organization. Remember that this project isn't creating "services" and "repositories", so it's a
    * different organizational problem to solve.
    */
  object Setup {
    def retrieveBars(foo: Foo): Task[Seq[EntityReference[Bar]]] = ???
  }
}
