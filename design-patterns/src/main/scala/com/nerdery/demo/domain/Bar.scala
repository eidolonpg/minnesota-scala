package com.nerdery.demo.domain

case class Bar(id: Long,
               name: String,
               minimum: Int,
               maximum: Int)

object Bar {
  case class Value(bar: EntityReference[Bar], value: Int)
}
