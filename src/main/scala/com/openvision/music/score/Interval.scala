package com.openvision.music.score

case class Interval(base: Int, modifier: Int)

object Interval {

  def perfect(base: Int) = Interval(base, 0)
  def major(base: Int) = Interval(base, 0)
  def minor(base: Int) = Interval(base, -1)
  def augmented(base: Int) = Interval(base, 1)
  def diminished(base: Int) = base match {
    case 1 | 4 | 5 | 8 => Interval(base, -1)
    case 2 | 3 | 6 | 7 => Interval(base, -2)
  }

}
