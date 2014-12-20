package net.scalamusic.composer.computation

import net.scalamusic.score._

case class Range(min: Pitch, max: Pitch)

object Range {

  val soprano = Range(C^1, A^2)
  val alto = Range(F^0, D^2)
  val tenor = Range(C^0, A^1)
  val bass = Range(F^(-1), D^1)

  val voiceRanges = List(soprano, alto, tenor, bass)

}
