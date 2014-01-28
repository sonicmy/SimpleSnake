package org.sonicmy.snake.util

class Game(width: Int, height: Int) {
  private val field = new Field(width, height)
  private var oldField = Array.fill(width*height)(1,UnitType.Default)
  var isRunning = true
  private val snake = new Snake(width,height,this)
  private var iter = 0


  def isBonusPresent = field.getField.clone.map(_._2).exists(_ == UnitType.Bonus)

  def getField:(Array[UnitType.Value] , Array[Int]) = {
    val _field = field.getField.clone.map(_._2)
    for(f <- _field.indices if(_field(f) == UnitType.Snake)) _field(f) = UnitType.Empty
    _field -> snake.getPosition.clone
  }
  
  def is(x:Int,t:UnitType.Value):Boolean = {
    val colide = field.is(x,t)
    t match {
      case UnitType.Bonus => colide
      case UnitType.Snake => if(colide){ isRunning = false; println(snake.getPosition.mkString(","));println(iter+"\ngame over"); iter = 0}
    }
    colide
  }

  def setSnakeDirection(dir: Dir.Value) = snake.trySetDirection(dir)

  def setSnakeDirection(keyCode:Int) =  keyCode match {
      case 37 => snake.trySetDirection(Dir.Left)
      case 38 => snake.trySetDirection(Dir.Up)
      case 39 => snake.trySetDirection(Dir.Right)
      case 40 => snake.trySetDirection(Dir.Down)
      case _  => 
    }

  //snake.trySetDirection(keyCode)

  def update:Array[(Int,UnitType.Value)] = {
    if(isRunning){
      field.setSnake(snake.getPosition)
      if(!isBonusPresent) field.addBonus
      snake.move
      iter += 1
      is(snake.getPosition.head,UnitType.Snake)


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