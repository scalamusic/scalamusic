package net.scalamusic.composer

import net.scalamusic.composer.computation.{Configuration, ChordIterator}
import net.scalamusic.input.Parser._
import net.scalamusic.score._

import scala.collection.mutable.ArrayBuffer

object ChordEnumerator extends App {

  def showChord(note: Note, chord: ChordSymbol) = {

    Console.println("Processing: " + chord)

    val it = new ChordIterator(chord, Configuration(note))

    val voices = (1 to 4).map { n =>
      ArrayBuffer[Note]()
    }
    it.foreach { ch =>
      (ch zip voices) map { case (n, v) =>
        v += n
      }
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

  val chord = parse(phrase(note ~ chordSymbol), args.mkString(" "))

  chord match {
    case Success(note ~ chord, _) => showChord(note, chord)
    case x: Failure => throw new RuntimeException(x.toString())
    case x: Error => throw new RuntimeException(x.toString())
  }

}
