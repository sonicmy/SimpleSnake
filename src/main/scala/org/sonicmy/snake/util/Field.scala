package org.sonicmy.snake.util

import scala.util.Random

class Field(val width: Int, val height: Int) {
	private val field = (0 to width*height).toArray.zip(Array.fill(width*height)(UnitType.Empty))
  private val rand = new Random

	def getField:Array[(Int, UnitType.Value)] = {
		field
	}

  def is(x: Int,t: UnitType.Value):Boolean = t match {
    case UnitType.Bonus => field(x)._2 == UnitType.Bonus
    case UnitType.Snake => field(x)._2 == UnitType.Snake
  }

  def setSnake(snake:Array[Int]) {
    for(f <- field if (f._2 == UnitType.Snake)) field(f._1) = f._1 -> UnitType.Empty
    for(s <- snake) field(s) = s -> UnitType.Snake
  }

  def addBonus {
    val empty = field.filter(_._2 == UnitType.Empty)
    if(!empty.isEmpty){
      val r = rand.nextInt(empty.length)
      field(empty(r)._1) = empty(r)._1 -> UnitType.Bonus
    }
  }
}