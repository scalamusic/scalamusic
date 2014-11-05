package com.openvision.music.composer

import com.openvision.music.composer.computation.{Configuration, ChordIterator}
import com.openvision.music.output.lilypond.Lilypond
import com.openvision.music.score._
import ChordSymbol._

import scala.collection.mutable.ArrayBuffer

/**
 * Created by hannes on 05/11/14.
 */
object ChordEnumerator extends App {

  val it = new ChordIterator(C.major, Configuration(Note(C^1, Duration(1, 2))))

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

  Lilypond.renderScore(score, System.out)

  score.show()
  //score.play()

}
