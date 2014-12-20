package net.scalamusic.score

object Midi {

  case class TicksPerQuarter(value: Int)

  implicit val DefaultTicksPerQuarter: TicksPerQuarter = TicksPerQuarter(1024)

}
