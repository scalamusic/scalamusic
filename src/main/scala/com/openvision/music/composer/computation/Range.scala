package com.openvision.music.composer.computation

import com.openvision.music.score._

case class Range(min: Pitch, max: Pitch)

object Range {

  val soprano = Range(C^1, G^2)
  val alto = Range(F^0, D^2)
  val tenor = Range(C^0, G^1)
  val bass = Range(E^(-1), E^1)

  val voiceRanges = List(soprano, alto, tenor, bass)

}
