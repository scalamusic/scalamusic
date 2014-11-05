package com.openvision.music.output.lilypond

import java.io.{Writer, File, FileWriter}

import com.openvision.music.score._
import com.openvision.music.output.{ScoreRenderer, Format}

import scala.sys.process._
import com.openvision.music.score.{VoiceElement, Score}

object Lilypond extends ScoreRenderer {

  private def renderNoteName(name: NoteName, out: Writer) = {
    out.write(name.toString)
  }

  private def renderDuration(duration: Duration, out: Writer) = {
    out.write(duration.denominator.toString)
    if (duration.nominator != 1)
      out.write("*" + duration.nominator.toString)
  }

  private def renderOctave(octave: Int, out: Writer) = {
    if (octave > 0) {
      out.write("'" * octave)
    } else if (octave < 0) {
      out.write("," * -octave)
    }
  }

  private def renderClef(clef: Clef, out: Writer) = {
    out.write("\\clef \"")
    clef match {
      case Treble => out.write("treble")
      case Alto => out.write("alto")
      case Tenor => out.write("tenor")
      case Bass => out.write("bass")
    }
    out.write("\"\n")
  }

  private def renderTimeSignature(timeSignature: Duration, out: Writer) = {
    out.write(s"\\time ${timeSignature.nominator}/${timeSignature.denominator}\n")
  }

  private def renderMode(mode: Mode, out: Writer) = {
    mode match {
      case Major => out.write("\\major")
      case Minor => out.write("\\minor")
    }
  }

  private def renderKey(key: Key, out: Writer) = {
    out.write("\\key ")
    renderNoteName(key.name, out)
    out.write(" ")
    renderMode(key.mode, out)
    out.write("\n")
  }

  private def render(elem: VoiceElement, out: Writer): Unit = elem match {
    case Note(name, octave, duration) =>
      renderNoteName(name, out)
      renderOctave(octave, out)
      renderDuration(duration, out)
    case Pause(duration) =>
      out.write("r")
      renderDuration(duration, out)
    case PlaceHolder(duration) =>
      out.write("\\skip ")
      renderDuration(duration, out)
  }

  private def renderVoice(voice: Voice, out: Writer): Unit = {
    out.write("    {")
    voice.elements.foreach { e =>
      out.write(" ")
      render(e, out)
    }
    out.write(" }")
  }

  def renderStaff(key: Key, staff: Staff, out: Writer): Unit = {
    out.write("<<\n")
    renderKey(key, out)
    renderTimeSignature(staff.timeSignature, out)
    renderClef(staff.clef, out)
    staff.voices.dropRight(1).foreach { v =>
      renderVoice(v, out)
      out.write(" \\\\ \n")
    }
    renderVoice(staff.voices.last, out)
    out.write("\n>>\n")
  }

  def renderScore(score: Score, out: Writer): Unit = {
    score.staves.foreach { s =>
      renderStaff(score.key, s, out)
    }
    out.close()
  }

  def write(score: Score, target: File, format: Format): Unit = {

    val tmpFile = File.createTempFile("com.openvision.music", ".ly")
    val writer = new FileWriter(tmpFile)
    renderScore(score, writer)

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
