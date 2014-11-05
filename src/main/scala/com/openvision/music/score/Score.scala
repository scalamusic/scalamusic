package com.openvision.music.score

import java.io.File

import com.openvision.music.output.ScoreRenderer
import com.openvision.music.score.Midi.TicksPerQuarter
import com.openvision.music.output.Format
import de.sciss.midi.{TickRate, Sequence, Sequencer}

case class Score(key: Key, staves: List[Staff]) {

  def voices = staves.flatMap(_.voices)

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

  def write(target: File, format: Format)(implicit renderer: ScoreRenderer) = renderer.write(this, target, format)

  def write(target: String, format: Format = Format.Pdf)(implicit renderer: ScoreRenderer) = renderer.write(this, target, format)

  def show(format: Format = Format.Pdf)(implicit renderer: ScoreRenderer) = renderer.show(this, format)

}
