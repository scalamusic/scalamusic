package net.scalamusic.output

import java.io.File

import net.scalamusic.output.lilypond.Lilypond
import net.scalamusic.score.Score

trait ScoreRenderer {

  def write(score: Score, target: File, format: Format): Unit

  def write(score: Score, targetFileName: String, format: Format = Format.Pdf): Unit = write(score, new File(targetFileName), format)

  def show(score: Score, format: Format = Format.Pdf): Unit

}

object ScoreRenderer {

  implicit val lilypond: ScoreRenderer = Lilypond

}
