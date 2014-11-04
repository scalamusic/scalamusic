package com.openvision.music.score

object Midi {

  case class TicksPerQuarter(value: Int)

  implicit val DefaultTicksPerQuarter: TicksPerQuarter = TicksPerQuarter(1024)

}
