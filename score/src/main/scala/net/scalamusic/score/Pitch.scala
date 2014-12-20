package net.scalamusic.score

case class Pitch(name: NoteName, octave: Int) extends Ordered[Pitch] {

  def toMidi: Int = 48 + name.pitch + 12*octave

  override def compare(that: Pitch): Int = this.toMidi - that.toMidi

  def +(i: Interval) = Pitch.fromMidi((name.index + i.base) % 7, toMidi + i.halfTones)

  def -(i: Interval) = Pitch.fromMidi((name.index - i.base) % 7, toMidi - i.halfTones)

  def -(p: Pitch) = {
    val base = Math.abs(name.index - p.name.index)
    val diff = Math.abs(toMidi - p.toMidi)
    Interval(base, diff - Interval.halfTones(base))
  }

  override def toString = {
    val octStr = if (octave > 0) {
      "'"*octave
    } else if (octave < 0) {
      ","*(-octave)
    } else {
      ""
    }
    name.toString + octStr
  }
}

object Pitch {

  def fromMidi(noteIndex: Int, midiPitch: Int) = {
    val basePitch = NoteName.basePitch(noteIndex)
    val x = midiPitch - basePitch - 48
    val octave: Int = x / 12
    val acc = x % 12
    if (acc > 6) {
      Pitch(NoteName(noteIndex, acc - 12), octave + 1)
    } else {
      Pitch(NoteName(noteIndex, acc), octave)
    }
  }

}
