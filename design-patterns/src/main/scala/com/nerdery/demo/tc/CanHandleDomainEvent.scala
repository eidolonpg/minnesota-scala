package com.nerdery.demo.tc

import com.nerdery.demo.domain.EntityVersion
import monix.eval.Task

trait CanHandleDomainEvent[T] {
  def handleEvent(t: T, version: EntityVersion): Task[(T, EntityVersion)]
}

object Events {
  def event[T](t: T, version: EntityVersion)
              (implicit ev: CanHandleDomainEvent[T]): Task[(T, EntityVersion)] = {
    ev.handleEvent(t, version)
  }
}