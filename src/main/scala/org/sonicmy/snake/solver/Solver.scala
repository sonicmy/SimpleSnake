package org.sonicmy.snake.solver

import org.sonicmy.snake.util._

class Solver(game: Game, width: Int, height: Int) {
  private var hasPlan = false
  private var isDone = false

  def think {
    val field= game.getField.clone.map(_ match {
          case UnitType.Empty => 0
          case UnitType.Snake => 100000000
          case UnitType.Bonus => -1        
      })
    if(field.filter(_ == -1).isEmpty) {
      println("NoBonus")
    }
    else {
      def isTrap(x:Int):Boolean = {
        if(field(x) == UnitType.Snake)
        true else false
      }

      
    }


    if(!isDone) {
      field.map(_ match {
          case 0 => "#"
          case 100000000 => "S"
          case -1 => "B"
        }).grouped(width).foreach(x => println(x.mkString))
      isDone = true
    }
  }

}