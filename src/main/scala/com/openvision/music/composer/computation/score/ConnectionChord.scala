package com.openvision.music.composer.computation.score

import com.openvision.music.score.{ChordSymbol, Note, Key}

case class ConnectionChord(notes: List[Note], chord: ChordSymbol)(implicit currentKey: Key) {

  private lazy val noteNames = notes.map(_.pitch.name)

  def chordIndex(i: Int) = {
    chord.notes.indexOf(notes(i).pitch.name)
  }

  lazy val leadingNotes = chord.notes.map { n =>
    currentKey.leadingNotes(n)
  }

  lazy val count = chord.notes.map { n =>
    noteNames.count(_ == n)
  }

}
