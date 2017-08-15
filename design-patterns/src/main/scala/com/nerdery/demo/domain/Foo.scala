package com.nerdery.demo.domain

import com.nerdery.demo.error.FooError
import com.nerdery.demo.tc.{CanHandleDomainEvent, CanPersist, CanValidate}
import monix.eval.Task

case class Foo(id: Long, name: String, values: Seq[Bar.Value])

/**
  * Contains the type class instances for the Foo domain.
  *
  * import Foo._
  *
  * Will bring these implicit objects into scope. This encapsulates the logic and I/O for
  * validation, persistence, and event handling.
  */
object Foo {
  /**
    * Type class instance for the CanValidate type class.
    */
  implicit object FooCanValidate extends CanValidate[(Foo, Seq[EntityReference[Bar]]), FooError] {
    /**
      * Validate a Foo, providing the list of Bars which contain the information
      * needed to validate the values. This function checks the values attached to the Foo
      * and the uniqueness of the name, aggregating any errors that result.
      *
      * @param t The data to validate.
      * @return Task representing either an error or an indication that the provided data is valid.
      */
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

    /**
      * Validate the uniqueness of the given Foo. Uniqueness is determined by an exact
      * match on the name field of the Foo. This function should leverage the database to
      * determine if any existing Foo shares the same name as the given Foo.
      *
      * @param foo The Foo to validate.
      * @return Task representing a list of uniqueness errors, or Nil if the Foo is unique.
      */
    def validateUniqueness(foo: Foo): Task[Seq[FooError]] = ???

    /**
      * Validate the values of the given Foo against the given Bar definitions. The Foo is
      * invalid if any of the following scenarios occur:
      *
      * - The Bar does not exist (the EntityReference is not fully populated)
      * - The value of the Foo is outside the min/max range specified by the Bar
      *
      * @param foo The Foo to validate.
      * @param bars The list of references to Bars.
      * @return Task representing a list of value errors, or Nil if the Foo is valid.
      */
    def validateValues(foo: Foo, bars: Seq[EntityReference[Bar]]): Task[Seq[FooError]] = ???
  }

  /**
    * Type class instance for the CanPersist type class.
    */
  implicit object FooCanPersist extends CanPersist[Foo] {
    /**
      * Save the given Foo to the database. This function needs to account for the
      * values joined by Foo id and Bar id.
      *
      * The output of this function is an EntityVersion -- the database model might
      * contain a version field and last updated column. Any time we retrieve or update
      * a Foo we can choose to retrieve this information. When we update a Foo this
      * should be updated to increment the version and set the updated date to "now".
      *
      * @param t The Foo to persist.
      * @return Task representing the new version of the Foo.
      */
    override def persist(t: Foo): Task[EntityVersion] = ???
  }

  /**
    * Type class instance for the CanHandleDomainEvent type class.
    */
  implicit object FooCanHandleDomainEvent extends CanHandleDomainEvent[Foo] {
    /**
      * Handle an event for the Foo domain. This is pretty simplistic and might not cover
      * all use cases, but gets the basic point across. We have some domain model with
      * a specific version that we want to report. This implementation simply wraps them up.
      *
      * @param t The Foo being reported.
      * @param version The version of the given Foo.
      * @return Task representing the combination of Foo and EntityVersion.
      */
    override def handleEvent(t: Foo, version: EntityVersion): Task[(Foo, EntityVersion)] = {
      Task.now((t, version))
    }
  }
}
