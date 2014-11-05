package com.openvision.music.score

case class Chord(symbol: ChordSymbol, override val duration: Duration) extends DurationElement(duration)
