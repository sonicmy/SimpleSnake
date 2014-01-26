package org.sonicmy.snake.util

class Snake(width:Int,height:Int,game:Game) {
  private var position = ((width*height - width)/2 to (width*height - width - 8)/2 by -1).toArray
  private var direction = Dir.Right

  def trySetDirection(keyCode: Int)= {
    keyCode match {
      case 37 => direction = if(direction != Dir.Right) Dir.Left else direction
      case 38 => direction = if(direction != Dir.Down) Dir.Up else direction
      case 39 => direction = if(direction != Dir.Left) Dir.Right else direction
      case 40 => direction = if(direction != Dir.Up) Dir.Down else direction
      case _  => 
    }
  }

  def getPosition:Array[Int] = {
    position
  }

  def move {
    direction match {
      case Dir.Up     => {
        val head = if(position.head / width == 0) width*height - (width - position.head % width) else position.head - width
        position = if(game.is(head,UnitType.Bonus)) head +: position else head +: position.init
        //game.is(head,UnitType.Snake)
      }
      case Dir.Down   => {
        val head = if(position.head / width == width - 1) position.head % width else position.head + width
        position = if(game.is(head,UnitType.Bonus)) head +: position else head +: position.init
        //game.is(head,UnitType.Snake)
      }
      case Dir.Left   => {
        val head = if(position.head % width == 0) (position.head-1+width) else (position.head - 1) 
        position = if(game.is(head,UnitType.Bonus)) head +: position else head +: position.init
        //game.is(head,UnitType.Snake)
      }
      case Dir.Right  => {
        val head = if((position.head + 1) % width == 0) (position.head+1-width) else (position.head + 1) 
        position = if(game.is(head,UnitType.Bonus)) head +: position else head +: position.init
        //game.is(head,UnitType.Snake)
      }
    }
  }

  object Dir extends Enumeration {
    type Dir = Value
    val Up = Value(0)
    val Down = Value(1)
    val Left = Value(2)
    val Right = Value(3)
  }
}