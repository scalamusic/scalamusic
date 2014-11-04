package com.openvision.music.score

case class Pause(override val duration: Duration) extends VoiceElement(duration)
