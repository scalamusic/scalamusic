package com.openvision.music.score

case class Note(name: NoteName, octave: Int, accidental: Int, override val duration: Duration) extends VoiceElement(duration)
