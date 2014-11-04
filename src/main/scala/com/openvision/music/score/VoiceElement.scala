package com.openvision.music.score

import com.openvision.music.score.Midi.TicksPerQuarter
import de.sciss.midi.Event

abstract class VoiceElement(duration: Duration) extends DurationElement(duration) {

  protected def midiTickLength(implicit rate: TicksPerQuarter) = (rate.value * duration.duration).toLong

  def toMidi(channel: Int, tick: Long)(implicit rate: TicksPerQuarter): List[Event]

}
