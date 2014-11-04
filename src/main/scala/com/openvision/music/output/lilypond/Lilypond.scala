package com.openvision.music.output.lilypond

import java.io.{File, FileWriter}

import com.openvision.music.output.{ScoreRenderer, Format}

import scala.sys.process._
import com.openvision.music.score.Score

object Lilypond extends ScoreRenderer {

  def write(score: Score, target: File, format: Format): Unit = {
    val str =
      """
        |\version "2.18.2"
        |{
        |  c' e' g' e'
        |}
      """.stripMargin

    val tmpFile = File.createTempFile("com.openvision.music", ".ly")
    val writer = new FileWriter(tmpFile)
    writer.write(str)
    writer.close()

    val process = Seq("lilypond", s"--${format.extension}", "-o", target.getAbsolutePath, tmpFile.getAbsolutePath)

    process.!

    ()
  }

  def show(score: Score, format: Format = Format.Pdf): Unit = {
    val file = File.createTempFile("com.openvision.music", "")
    write(score, file, format)
    Seq("open", file + "." + format.extension).!

    ()
  }

}
