package net.scalamusic.score

case class NoteName(val index: Integer, val accidentals: Int) {

  def pitch = NoteName.basePitch(index) + accidentals

  def is = NoteName(index, accidentals + 1)

  def es = NoteName(index, accidentals - 1)

  def +(i: Interval) = (Pitch(this, 0) + i).name
  def -(i: Interval) = (Pitch(this, 0) - i).name

  def ^(octave: Int) = Pitch(this, octave)

  override def toString: String = {
    val accStr = if (accidentals > 0)
        "is" * accidentals
      else if (accidentals < 0)
        "es" * -accidentals
      else
        ""
    NoteName.symbols(index) + accStr
  }

}

object NoteName {

  val basePitch = List(0, 2, 4, 5, 7, 9, 11)

  val symbols = List("c", "d", "e", "f", "g", "a", "b")

}

object C extends NoteName(0, 0)
object D extends NoteName(1, 0)
object E extends NoteName(2, 0)
object F extends NoteName(3, 0)
object G extends NoteName(4, 0)
object A extends NoteName(5, 0)
object B extends NoteName(6, 0)
