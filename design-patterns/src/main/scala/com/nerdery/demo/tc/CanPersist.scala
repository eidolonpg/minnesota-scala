package com.nerdery.demo.tc

import com.nerdery.demo.domain.EntityVersion
import monix.eval.Task

trait CanPersist[T] {
  def persist(t: T): Task[EntityVersion]
}

object Persistence {
  def persist[T](t: T)(implicit ev: CanPersist[T]): Task[EntityVersion] = {
    ev.persist(t)
  }
}