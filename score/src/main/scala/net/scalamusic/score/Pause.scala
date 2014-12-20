package net.scalamusic.score

import net.scalamusic.score.Midi.TicksPerQuarter
import de.sciss.midi.{NoteOff, Event}

case class Pause(override val duration: Duration) extends VoiceElement(duration) {

  override def toMidi(channel: Int, tick: Long)(implicit rate: TicksPerQuarter): List[Event] = Event(tick + midiTickLength, NoteOff(channel, 0, 0)) :: Nil

}
