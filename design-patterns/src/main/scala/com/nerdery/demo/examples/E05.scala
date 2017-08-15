package com.nerdery.demo.examples

import cats.Eq
import cats.implicits._
import com.nerdery.demo.domain.Foo

object E05 {
  // Note that Foo does NOT extend Eq, we create an implicit that knows how to handle Foo.
  implicit object FooIsEq extends Eq[Foo] {
    override def eqv(x: Foo, y: Foo): Boolean = x.id == y.id
  }

  // Returns "False"
  def exampleUsage(): Boolean = {
    val f1 = Foo(1, "Foo1", Nil)
    val f2 = Foo(2, "Foo2", Nil)

    f1 === f2
  }

  /*
  trait Eq[A] {
    def eqv(x: A, y: A): Boolean
  }
  */
}
