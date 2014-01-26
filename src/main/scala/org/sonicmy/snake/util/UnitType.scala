package org.sonicmy.snake.util

object UnitType extends Enumeration {
  type UnitType = Value
  val Empty = Value(0)
  val Bonus = Value(1)
  val Snake = Value(2)
  val Default = Value(-1)
}