package com.openvision.music.input

import com.openvision.music.score._
import ChordSymbol._

import scala.util.parsing.combinator.RegexParsers

object Parser extends RegexParsers {

  def symbol: Parser[(Int, Boolean)] = "[a-gA-G]".r ^^ { s =>
    val symbolIndex = NoteName.symbols.indexOf(s.toLowerCase)
    val isMinor = s.charAt(0).isLower
    (symbolIndex, isMinor)
  }

  def accidentals: Parser[Int] = "((:?b*)|(:?\\#*))".r ^^ { s =>
    s match {
      case _ if (s.isEmpty) => 0
      case _ if (s.charAt(0) == '#') => s.length
      case _ if (s.charAt(0) == 'b') => -s.length
    }
  }

  def noteName: Parser[NoteName] = (symbol ~ accidentals) ^^ { case ((sy, minor) ~ a) =>
    NoteName(sy, a)
  }

  def chordSymbol: Parser[ChordSymbol] = (symbol ~ accidentals) ^^ { case ((sy, minor) ~ acc) =>
    val name = NoteName(sy, acc)
    if (minor)
      name.minor
    else
      name.major
  }

}
