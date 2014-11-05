package com.openvision.music.score

import com.openvision.music.score.Midi.TicksPerQuarter
import de.sciss.midi.{NoteOff, NoteOn, Event}

case class Note(pitch: Pitch, override val duration: Duration) extends VoiceElement(duration) {

  def toMidi(channel: Int, tick: Long)(implicit rate: TicksPerQuarter): List[Event] = {
    Event(tick, NoteOn(0, pitch.toMidi, 80)) :: Event(tick + midiTickLength, NoteOff(0, pitch.toMidi, 0)) :: Nil
  }

  override def toString = s"$pitch$duration"

}
