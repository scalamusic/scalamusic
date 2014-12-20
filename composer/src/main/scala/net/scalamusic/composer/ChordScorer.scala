package net.scalamusic.composer

import net.scalamusic.composer.computation.score._
import net.scalamusic.composer.computation.{Configuration, ChordIterator}
import net.scalamusic.input.Parser._
import net.scalamusic.score._
import ChordSymbol._

import scala.collection.mutable.ArrayBuffer

object ChordScorer extends App {

  implicit val currentKey = Key(C, Major)

  println(Minor.scale(A))

  def addToVoice(chord: List[Note], voices: Seq[ArrayBuffer[Note]]) = {
    (chord zip voices) map { case (n, v) =>
      v += n
    }
  }

  def processChords(n1: Note, n2: Note, a: ChordSymbol, b: ChordSymbol) = {

    Console.println(s"$a ($n1) -> $b ($n2)")

    val configurationA = Configuration(n1)
    val configurationB = Configuration(n2)
    val scorer = PositionScorer + DoublingScorer + VoiceLeadingScorer

    val sorted = (for (c1 <- ChordIterator(a, configurationA); c2 <- ChordIterator(b, configurationB)) yield (c1, c2)).toList.sortWith { case ((a1, b1), (a2, b2)) =>
      scorer(Connection(ConnectionChord(a1, C.major), ConnectionChord(b1, C.major))) < scorer(Connection(ConnectionChord(a2, C.major), ConnectionChord(b2, C.major))) // TODO
    }

    sorted.foreach { case (a, b) =>
      //Console.print(scorer(a, b))
      Console.println(s": $a -> $b")
    }

    val voices = (1 to 4).map { n =>
      ArrayBuffer[Note]()
    }
    sorted.foreach { case (a, b) =>
      addToVoice(a, voices)
      addToVoice(b, voices)
    }

    val score = Score(Key(C, Major), List(
      Staff(Treble, Duration(4, 4), List(
        Voice(voices(0).toList),
        Voice(voices(1).toList)
      )),
      Staff(Bass, Duration(4, 4), List(
        Voice(voices(2).toList),
        Voice(voices(3).toList)
      ))), Nil)

    score.show()

  }

  val chords = parse(phrase(note ~ note ~ chordSymbol ~ chordSymbol), args.mkString(" "))

  (chords) match {
    case Success((n1 ~ n2 ~ a ~ b), _) => processChords(n1, n2, a, b)
    case x: Failure => throw new RuntimeException(x.toString())
    case x: Error => throw new RuntimeException(x.toString())
  }

}
