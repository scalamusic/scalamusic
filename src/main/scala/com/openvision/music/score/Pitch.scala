package com.openvision.music.score

case class Pitch(name: NoteName, octave: Int) extends Ordered[Pitch] {

  def toMidi: Int = 48 + name.pitch + 12*octave

  override def compare(that: Pitch): Int = this.toMidi - that.toMidi

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
