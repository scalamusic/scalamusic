package com.openvision.music.score

case class Pitch(name: NoteName, octave: Int) {

  def toMidi: Int = 48 + name.pitch + 12*octave

}
