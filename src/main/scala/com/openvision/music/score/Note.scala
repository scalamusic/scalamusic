package com.openvision.music.score

import com.openvision.music.score.Midi.TicksPerQuarter
import de.sciss.midi.{NoteOff, NoteOn, Event}

case class Note(name: NoteName, octave: Int, accidental: Int, override val duration: Duration) extends VoiceElement(duration) {

  protected def midiPitch: Int = 48 + (name.pitch + 12*octave) + accidental

  def toMidi(channel: Int, tick: Long)(implicit rate: TicksPerQuarter): List[Event] = {
    Event(tick, NoteOn(0, midiPitch, 80)) :: Event(tick + midiTickLength, NoteOff(0, midiPitch, 0)) :: Nil
  }

}
