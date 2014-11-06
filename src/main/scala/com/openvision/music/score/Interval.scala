package com.openvision.music.score

case class Interval(base: Int, modifier: Int) {

  val halfTones = Interval.halfTones(base) + modifier

}

object Interval {

  val halfTones = List(0, 2, 4, 5, 7, 9, 11)

  def perfect(base: Int) = Interval(base - 1, 0)
  def major(base: Int) = Interval(base - 1, 0)
  def minor(base: Int) = Interval(base - 1, -1)
  def augmented(base: Int) = Interval(base - 1, 1)
  def diminished(base: Int) = base match {
    case 1 | 4 | 5 | 8 => Interval(base - 1, -1)
    case 2 | 3 | 6 | 7 => Interval(base - 1, -2)
  }

}
