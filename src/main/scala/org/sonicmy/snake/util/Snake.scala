package org.sonicmy.snake.util

class Snake(width:Int,height:Int,game:Game) {
  private var position = ((width*height - width)/2 to (width*height - width - 8)/2 by -1).toArray
  private var direction = Dir.Right

  def trySetDirection(dir: Dir.Value)= {
    dir match {
      case Dir.Left => direction = if(direction != Dir.Right) Dir.Left else direction
      case Dir.Up => direction = if(direction != Dir.Down) Dir.Up else direction
      case Dir.Right => direction = if(direction != Dir.Left) Dir.Right else direction
      case Dir.Down => direction = if(direction != Dir.Up) Dir.Down else direction
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
      }
      case Dir.Down   => {
        val head = if(position.head / width == width - 1) position.head % width else position.head + width
        position = if(game.is(head,UnitType.Bonus)) head +: position else head +: position.init
      }
      case Dir.Left   => {
        val head = if(position.head % width == 0) (position.head-1+width) else (position.head - 1) 
        position = if(game.is(head,UnitType.Bonus)) head +: position else head +: position.init
      }
      case Dir.Right  => {
        val head = if((position.head + 1) % width == 0) (position.head+1-width) else (position.head + 1) 
        position = if(game.is(head,UnitType.Bonus)) head +: position else head +: position.init
      }
    }
  }
}