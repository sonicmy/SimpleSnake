package org.sonicmy.snake.solver

import org.sonicmy.snake.util._

class Solver(game: Game, width: Int) {
  private var hasPlan = false
  private var isDone = false

  def think {
    val field:Array[(Int,UnitType.Value)] = game.getField
    if(field.filter(_._2 == UnitType.Bonus).isEmpty){
      println("NoBonus")
    }
    else {
      
    }
    if(!isDone) {
      field.map(x => x._2 match {
          case UnitType.Empty => "#"
          case UnitType.Snake => "S"
          case UnitType.Bonus => "B"
        }).grouped(width).foreach(x => println(x.mkString))
      isDone = true
    }
  }

}