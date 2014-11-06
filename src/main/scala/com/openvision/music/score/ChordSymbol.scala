package com.openvision.music.score

case class ChordSymbol(suffix: String, inversion: Int, notes: List[NoteName]) {

  def root = notes(0)

  def bass = notes(inversion)

  def _7 = ChordSymbol(suffix + "7", inversion, notes :+ (root + Interval.minor(7)))
  def _maj7 = ChordSymbol(suffix + "maj7", inversion, notes :+ (root + Interval.major(7)))

  def invert(num: Int) = ChordSymbol(suffix, (inversion + 1) % notes.size, notes)

  override def toString = notes.mkString("(", " ", ")") + " / " + root

}

object ChordSymbol {

  implicit class Constructor(name: NoteName) {

    def major = ChordSymbol("", 0, List(name, name + Interval.major(3), name + Interval.perfect(5)))
    def minor = ChordSymbol("", 0, List(name, name + Interval.minor(3), name + Interval.perfect(5)))
    def diminished = ChordSymbol("", 0, List(name, name + Interval.minor(3), name + Interval.diminished(5)))
    def augmented = ChordSymbol("", 0, List(name, name + Interval.major(3), name + Interval.augmented(5)))

  }

}
