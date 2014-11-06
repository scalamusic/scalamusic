package com.openvision.music.score

case class Interval(base: Int, modifier: Int) extends Ordered[Interval] {

  val halfTones = Interval.halfTones(base) + modifier

  override def compare(that: Interval): Int = this.halfTones - that.halfTones
}

object Interval {

  val halfTonesList = List(0, 2, 4, 5, 7, 9, 11)

  def halfTones(base: Int) = halfTonesList(base % 8) + 12*(base / 8)

  def perfect(base: Int) = Interval(base - 1, 0)
  def major(base: Int) = Interval(base - 1, 0)
  def minor(base: Int) = Interval(base - 1, -1)
  def augmented(base: Int) = Interval(base - 1, 1)
  def diminished(base: Int) = ((base - 1) % 8) match {
    case 0 | 3 | 4 | 7 => Interval(base - 1, -1)
    case 1 | 2 | 5 | 6 => Interval(base - 1, -2)
  }

}
