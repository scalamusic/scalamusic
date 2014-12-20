package net.scalamusic.score

import net.scalamusic.score.Midi.TicksPerQuarter
import de.sciss.midi.{TickRate, Track, Event}

case class Voice(elements: List[VoiceElement]) {

  def toMidi(channel: Int)(implicit rate: TickRate, ticksPerQuarter: TicksPerQuarter) = Track(elements.foldLeft((0L, List[Event]())) { case ((tick, ev), v)  =>
    val newEvents = v.toMidi(channel, tick)
    (newEvents.last.tick, ev ::: newEvents)
  }._2.toIndexedSeq)

}
