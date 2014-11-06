package com.openvision.music.composer

import com.openvision.music.composer.computation.{Configuration, ChordIterator}
import com.openvision.music.input.Parser._
import com.openvision.music.score._

import scala.collection.mutable.ArrayBuffer

object ChordEnumerator extends App {

  def showChord(chord: ChordSymbol) = {

    Console.println("Processing: " + chord)

    val it = new ChordIterator(chord, Configuration(Note(chord.root^2, Duration(1, 2))))

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

  val chord = parse(phrase(chordSymbol), args(0))

  chord match {
    case Success(chord, _) => showChord(chord)
    case x: Failure => throw new RuntimeException(x.toString())
    case x: Error => throw new RuntimeException(x.toString())
  }

}
