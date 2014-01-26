package org.sonicmy.snake

import processing.core._
import java.awt.Dimension
import org.sonicmy.snake.util._
import org.sonicmy.snake.solver.Solver

object Main extends PApplet {
  private var applet:Main = _

  private val (_WIDTH,_HEIGHT,_UNITSIZE,_MARGIN,_SCALE) = (20,20,10,1,3)

  def main(args: Array[String]) = {
    applet = new Main(_WIDTH,_HEIGHT,_UNITSIZE,_MARGIN,_SCALE)
    val frame = new javax.swing.JFrame("Main")
    val size = new Dimension(_SCALE * (_WIDTH * (_UNITSIZE+ _MARGIN) + _MARGIN) + 10, _SCALE * (_HEIGHT * (_UNITSIZE+ _MARGIN) + _MARGIN) + 30)
 
    frame.setPreferredSize(size)
    frame.getContentPane().add(applet)
    frame.pack
    applet.init
    frame.setVisible(true)
    frame.setResizable(false)
  }
}

class Main (val _WIDTH:Int, val _HEIGHT:Int, val _UNITSIZE:Int, val _MARGIN:Int, val _SCALE:Int) extends PApplet {

  var game = new Game(_WIDTH,_HEIGHT)
  var solver = new Solver(game,_WIDTH)

  def drawUnit(x:Int, y:Int, utype:UnitType.Value) {
    utype match {
      case UnitType.Empty => fill(100,100,100) 
      case UnitType.Bonus => fill(255,255,255)
      case UnitType.Snake => fill(0,250,0)
      case _ =>
    }
    
    rect(_SCALE*(x*(_UNITSIZE + _MARGIN) + _MARGIN)  ,_SCALE*(y*(_UNITSIZE + _MARGIN) + _MARGIN) ,_UNITSIZE*_SCALE,_UNITSIZE*_SCALE)
  }

  def drawUnit(pos: (Int,UnitType.Value)) {
    drawUnit(pos._1%_WIDTH,pos._1/_WIDTH, pos._2)
  }

  override def setup() = {  
    background(0,0,0,0)
    frameRate(15)
  }

  override def draw() = {
    solver.think
    game.update.foreach(drawUnit)
  }

  override def keyPressed() = {
    if(keyCode == 82) game = new Game(_WIDTH,_HEIGHT)
    game.setSnakeDirection(keyCode)
  }
}