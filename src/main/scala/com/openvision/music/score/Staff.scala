package com.openvision.music.score

case class Staff(clef: Clef, timeSignature: Duration, voices: List[Voice])