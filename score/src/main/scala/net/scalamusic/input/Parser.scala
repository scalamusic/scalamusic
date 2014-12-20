package net.scalamusic.input

import net.scalamusic.score._
import ChordSymbol._

import scala.util.parsing.combinator.RegexParsers

object Parser extends RegexParsers {

  def symbol: Parser[(Int, Boolean)] = "[a-gA-G]".r ^^ { s =>
    val symbolIndex = NoteName.symbols.indexOf(s.toLowerCase)
    val isMinor = s.charAt(0).isLower
    (symbolIndex, isMinor)
  }

  def updownParser(up: Char, down: Char): Parser[Int] = s"[$up$down]*".r ^^ { s =>
    s match {
      case _ if (s.isEmpty) => 0
      case _ if (s.charAt(0) == up) => s.length
      case _ if (s.charAt(0) == down) => -s.length
    }
  }

  def accidentals: Parser[Int] = updownParser('b', '#')

  def noteName: Parser[NoteName] = (symbol ~ accidentals) ^^ { case ((sy, minor) ~ a) =>
    NoteName(sy, a)
  }

  def octave: Parser[Int] = updownParser('\'', ',')

  def pitch: Parser[Pitch] = (noteName ~ octave) ^^ { case (name ~ octave) =>
    Pitch(name, octave)
  }

  def duration: Parser[Duration] = """(\d+)(\*\d+)?""".r ^^ { s =>
    s.split("\\*") match {
      case Array(denominator, nominator) => Duration(nominator.toInt, denominator.toInt)
      case Array(denominator) => Duration(1, denominator.toInt)
    }
  }

  def note: Parser[Note] = (pitch ~ duration) ^^ { case (pitch ~ duration) =>
    Note(pitch, duration)
  }

  def chordSymbol: Parser[ChordSymbol] = (symbol ~ accidentals) ^^ { case ((sy, minor) ~ acc) =>
    val name = NoteName(sy, acc)
    if (minor)
      name.minor
    else
      name.major
  }

  def chordSequence: Parser[Seq[ChordSymbol]] = chordSymbol*

}
