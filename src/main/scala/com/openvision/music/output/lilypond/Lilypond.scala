package com.openvision.music.output.lilypond

import java.io._

import com.openvision.music.score._
import com.openvision.music.output.{ScoreRenderer, Format}

import scala.sys.process._
import com.openvision.music.score.{VoiceElement, Score}

object Lilypond extends ScoreRenderer {

  private def renderNoteName(name: NoteName)(implicit out: Writer) = {
    out.write(name.toString)
  }

  private def renderDuration(duration: Duration)(implicit out: Writer) = {
    out.write(duration.denominator.toString)
    if (duration.nominator != 1)
      out.write("*" + duration.nominator.toString)
  }

  private def renderOctave(octave: Int)(implicit out: Writer) = {
    if (octave > 0) {
      out.write("'" * octave)
    } else if (octave < 0) {
      out.write("," * -octave)
    }
  }

  private def renderClef(clef: Clef)(implicit out: Writer) = {
    out.write("\\clef \"")
    clef match {
      case Treble => out.write("treble")
      case Alto => out.write("alto")
      case Tenor => out.write("tenor")
      case Bass => out.write("bass")
    }
    out.write("\"\n")
  }

  private def renderTimeSignature(timeSignature: Duration)(implicit out: Writer) = {
    out.write(s"\\time ${timeSignature.nominator}/${timeSignature.denominator}\n")
  }

  private def renderMode(mode: Mode)(implicit out: Writer) = {
    mode match {
      case Major => out.write("\\major")
      case Minor => out.write("\\minor")
    }
  }

  private def renderKey(key: Key)(implicit out: Writer) = {
    out.write("\\key ")
    renderNoteName(key.name)
    out.write(" ")
    renderMode(key.mode)
    out.write("\n")
  }

  private def render(elem: VoiceElement)(implicit out: Writer): Unit = elem match {
    case Note(Pitch(name, octave), duration) =>
      renderNoteName(name)
      renderOctave(octave)
      renderDuration(duration)
    case Pause(duration) =>
      out.write("r")
      renderDuration(duration)
    case PlaceHolder(duration) =>
      out.write("\\skip ")
      renderDuration(duration)
  }

  private def renderVoice(voice: Voice)(implicit out: Writer): Unit = {
    out.write("    {")
    voice.elements.foreach { e =>
      out.write(" ")
      render(e)
    }
    out.write(" }")
  }

  def renderStaff(score: Score, staff: Staff, withChords: Boolean)(implicit out: Writer): Unit = {
    out.write("<<\n")
    renderKey(score.key)
    renderTimeSignature(staff.timeSignature)
    renderClef(staff.clef)
    if (withChords)
      renderChords(score.chords)
    staff.voices.dropRight(1).foreach { v =>
      renderVoice(v)
      out.write(" \\\\ \n")
    }
    renderVoice(staff.voices.last)
    out.write("\n>>\n")
  }

  def renderChord(chord: Chord)(implicit out: Writer): Unit = {
    renderNoteName(chord.symbol.root)
    renderDuration(chord.duration)
    if (chord.symbol.inversion > 0) {
      out.write("/")
      renderNoteName(chord.symbol.bass)
    }
    out.write(chord.symbol.suffix)
  }

  def renderChords(chords: List[Chord])(implicit out: Writer): Unit = {
    out.write("\\chords {")
    chords.foreach { c =>
      out.write(" ")
      renderChord(c)
    }
    out.write("}\n")
  }

  def renderScore(score: Score, out: Writer): Unit = {
    score.staves.dropRight(1).foreach { s =>
      renderStaff(score, s, false)(out)
    }
    renderStaff(score, score.staves.last, true)(out)
    out.close()
  }

  def renderScore(score: Score, out: OutputStream): Unit = renderScore(score, new OutputStreamWriter(out))

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
