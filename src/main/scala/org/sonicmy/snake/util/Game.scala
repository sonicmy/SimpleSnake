package org.sonicmy.snake.util

class Game(width: Int, height: Int) {
	private val field = new Field(width, height)
  private var oldField = Array.fill(width*height)(1,UnitType.Default)
  private var isRunning = true
  private var isBonusPresent = false
  private val snake = new Snake(width,height,this)

  def getField:Array[(Int,UnitType.Value)] = {
    val _field = field.getField.clone
    for(f <- _field if(f._2 == UnitType.Snake)) _field(f._1) = f._1 -> UnitType.Empty
    val _snake = snake.getPosition.clone
    for (s <- _snake) _field(s) = s -> UnitType.Snake
    _field
  }
	
  def is(x:Int,t:UnitType.Value):Boolean = {
    val colide = field.is(x,t)
    t match {
      case UnitType.Bonus => if(colide && isBonusPresent) isBonusPresent = false
      case UnitType.Snake => if(colide){ isRunning = false; println("game over")}
    }
    colide
  }

  def setSnakeDirection(keyCode:Int) = snake.trySetDirection(keyCode)

	def update:Array[(Int,UnitType.Value)] = {
    if(isRunning){
      snake.move
      is(snake.getPosition.head,UnitType.Snake)
      field.setSnake(snake.getPosition)

      if(!isBonusPresent) {
        field.addBonus    
        isBonusPresent=true
      }

      {
        val newField = field.getField
        val res = newField.diff(oldField)
        oldField = newField.clone
        res
      }
    }
    else Array((0, UnitType.Default))
	}
}