package org.sonicmy.snake.util

object Dir extends Enumeration {
  type Dir = Value
  val Up = Value(0)
  val Down = Value(1)
  val Left = Value(2)
  val Right = Value(3)
}