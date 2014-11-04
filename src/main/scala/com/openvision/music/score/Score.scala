package com.openvision.music.score

import com.openvision.music.score.Midi.TicksPerQuarter
import de.sciss.midi.{TickRate, Sequence, Sequencer}

case class Score(voices: List[Voice]) {

  def toMidi(tempo: Int) = {
    implicit val tpq: TicksPerQuarter = Midi.DefaultTicksPerQuarter
    implicit val rate: TickRate = TickRate.tempo(tempo, tpq.value)
    val tracks = voices.zipWithIndex.map { case (v, i) =>
      v.toMidi(i)
    }
    Sequence(tracks.toIndexedSeq)
  }

  def play(tempo: Int = 120) = {
      val sequencer = Sequencer.open()
      val sq = toMidi(tempo)
      sequencer.play(sq)
      Thread.sleep((sq.duration * 1000).toLong)
      sequencer.close()
  }

}
