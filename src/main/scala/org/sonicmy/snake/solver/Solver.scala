package org.sonicmy.snake.solver

import org.sonicmy.snake.util._

class Solver(game: Game, width: Int, height: Int) {

  private var hasPlan = false
  private var plan:Array[Dir.Value] = Array()
  private var oldBonus = 0

  def init:(Array[Int],Array[Int]) = {
    val f = game.getField
    f._1.map(_ match {
      case UnitType.Empty => 1000000
      case UnitType.Bonus => -1       
    }) -> f._2
  }

  def think {
    val (field, snake) = init
    val bonus = field.indexOf(-1)

    def move(pos:Int, dir:Dir.Value):(Int,Dir.Value) = dir match {
      case Dir.Up => (if(pos / width == 0) width*height - (width - pos % width) else pos - width) -> Dir.Up
      case Dir.Down => (if(pos / width == width - 1) pos % width else pos + width) -> Dir.Down
      case Dir.Left => (if(pos % width == 0) (pos-1+width) else (pos - 1)) -> Dir.Left
      case Dir.Right => (if((pos + 1) % width == 0) (pos+1-width) else (pos + 1) ) -> Dir.Right
    }
    
    def isWin(pos:Int):Boolean = bonus == pos
    def isWorth(pos:Int, steps:Int, sn:Array[Int]):Boolean = sn.indexOf(pos) == -1 && (field(pos) == -1 || field(pos) > steps + 1)
    def mark(pos:Int, steps:Int) = field(pos) = steps

    if(hasPlan && game.isRunning) {
      game.setSnakeDirection(plan.head)
      plan = plan.tail
      if(plan.isEmpty) hasPlan = false
    }
    else if(bonus != -1 && game.isRunning && oldBonus != bonus) {
      oldBonus = bonus
      def step(snakes:Array[(Array[Int], Array[Dir.Value])], steps:Int):Array[Dir.Value] = {
        val pairs:Array[Array[(Array[Int], Array[Dir.Value])]] = for (s <- snakes) yield {
          val worthPairs = (for { d <- 0 to 3
            val m = move(s._1.head,Dir(d))
            if(isWorth(m._1, steps, s._1)) } yield {
              mark(m._1, steps + 1)
              m
            }).map{ x => (x._1 +: s._1.init) -> (x._2 +: s._2)}
          worthPairs.toArray
        }

        def flattenThis(p: Array[Array[(Array[Int], Array[Dir.Value])]], res: Array[(Array[Int], Array[Dir.Value])]):Array[(Array[Int], Array[Dir.Value])] = {
          if(p.isEmpty) res
          else flattenThis(p.tail, p.head ++ res)
        }

        val flat = flattenThis(pairs, Array())
        if(flat.isEmpty) Array(Dir.Up)
        else {
          val res = flat.indexWhere(x => isWin(x._1.head))
          if(res != -1) {
            //println("___________________________________________")
            //flat.foreach(x => println(x._2.mkString(",")))
            flat(res)._2.reverse
          }
          else step(flat, steps+1)
        }
      }
      val p = step(Array((snake, Array():Array[Dir.Value])), 0)
      /*println("#####################################################################")
      println("Snake: "+snake.mkString(","))  
      println("Bonus: "+bonus)
      println("Plan: " +p.mkString(","))*/
      //game.isRunning = false
      plan = p.tail
      game.setSnakeDirection(p.head)
      if(!plan.isEmpty){
        hasPlan = true
      }
    } else if (bonus != -1 && game.isRunning ) {
      val f = (for(i <- 0 to 3) yield move(snake.head, Dir(i))).filter(x => isWorth(x._1,1, snake))
      if(f.isEmpty) plan = Array(Dir.Up)
      else {
        game.setSnakeDirection(f.head._2)
        /*println("############################BOOOOO#################################")
        println("Snake: "+snake.mkString(","))  
        println("Bonus: "+bonus)
        println("Plan: " +plan.mkString(","))*/
      }
    }
  }
}