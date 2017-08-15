package com.nerdery.demo

import monix.eval.Task

object Bars {
  def populateReferences(bars: Seq[EntityReference[Bar]]): Task[Seq[EntityReference[Bar]]] = ???
}
