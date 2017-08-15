package com.nerdery.demo.domain

class EntityReference[+T](val entityId: Long, val entity: Option[T])

object EntityReference {
  def apply[T](entityId: Long, entity: Option[T]): EntityReference[T] = {
    new EntityReference[T](entityId, entity)
  }
}
