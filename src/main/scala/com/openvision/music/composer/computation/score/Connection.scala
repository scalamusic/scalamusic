package com.openvision.music.composer.computation.score

case class Connection(a: ConnectionChord, b: ConnectionChord) {

  private def group[A](list: List[A]) = list.zipWithIndex.groupBy { case (v, i) =>
    v
  }. map {
    case (v, list) =>
      (v, list.map(_._2))
  }

  lazy val diff = (a.notes zip b.notes) map { case (a, b) =>
    b.pitch.toMidi - a.pitch.toMidi
  }

  lazy val diff2Voices = group(diff)

  lazy val parallels = diff2Voices.filter(_._2.size > 1).keys

  def hiddenParallels(value: Int) = {

    def exists(voices: List[Int]) = (voices zip voices).exists { case (x, y) =>
      Math.abs(b.notes(x).pitch.toMidi - b.notes(y).pitch.toMidi) == value
    }

    val down = dir2Voices(-1.0f)
    val up = dir2Voices(1.0f)

    exists(down) || exists(up)
  }

  lazy val dir = diff.map(Math.signum(_))

  lazy val dir2Voices = group(dir)

}
