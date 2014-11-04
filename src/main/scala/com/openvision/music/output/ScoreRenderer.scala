package com.openvision.music.output

import java.io.File

import com.openvision.music.output.lilypond.Lilypond
import com.openvision.music.score.Score

trait ScoreRenderer {

  def write(score: Score, target: File, format: Format): Unit

  def write(score: Score, targetFileName: String, format: Format = Format.Pdf): Unit = write(score, new File(targetFileName), format)

  def show(score: Score, format: Format = Format.Pdf): Unit

}

object ScoreRenderer {

  implicit val lilypond: ScoreRenderer = Lilypond

}
