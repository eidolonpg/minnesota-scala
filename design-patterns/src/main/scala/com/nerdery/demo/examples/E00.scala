package com.nerdery.demo.examples

object E00 {
  case class Foo(id: Long, name: String, values: Seq[Bar.Value])

  case class Bar(id: Long,
                 name: String,
                 minimum: Int,
                 maximum: Int)

  object Bar {
    case class Value(bar: EntityReference[Bar], value: Int)
  }

  class EntityReference[+T](val entityId: Long, val entity: Option[T])

  object EntityReference {
    def apply[T](entityId: Long, entity: Option[T]): EntityReference[T] = {
      new EntityReference[T](entityId, entity)
    }
  }
}
