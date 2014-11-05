package com.openvision.music.score

case class ChordSymbol(suffix: String, notes: List[NoteName]) {

  def root = notes(0)

  def _7 = ChordSymbol(suffix + "7", notes :+ (root + Interval.minor(7)))
  def _maj7 = ChordSymbol(suffix + "maj7", notes :+ (root + Interval.major(7)))

}

object ChordSymbol {

  implicit class Constructor(name: NoteName) {

    def major = ChordSymbol("", List(name, name + Interval.major(3), name + Interval.perfect(5)))
    def minor = ChordSymbol("", List(name, name + Interval.minor(3), name + Interval.perfect(5)))

  }

}
