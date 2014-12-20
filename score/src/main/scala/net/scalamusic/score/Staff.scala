package net.scalamusic.score

case class Staff(clef: Clef, timeSignature: Duration, voices: List[Voice])