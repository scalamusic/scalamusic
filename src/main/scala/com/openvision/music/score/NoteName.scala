package com.openvision.music.score

case class NoteName(val symbol: String, val basePitch: Integer, val accidentals: Int) {

  def pitch = basePitch + accidentals

  def is = NoteName(symbol, basePitch, accidentals + 1)

  def es = NoteName(symbol, basePitch, accidentals - 1)

  def + = is

  def b = es

  override def toString = {
    val accStr = if (accidentals > 0)
        "is" * accidentals
      else if (accidentals < 0)
        "es" * -accidentals
      else
        ""
    symbol + accStr
  }

}

object C extends NoteName("c", 0, 0)
object D extends NoteName("d", 2, 0)
object E extends NoteName("e", 4, 0)
object F extends NoteName("f", 5, 0)
object G extends NoteName("g", 7, 0)
object A extends NoteName("a", 9, 0)
object B extends NoteName("b", 11, 0)
