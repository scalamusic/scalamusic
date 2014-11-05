package com.openvision.music.composer

import java.io.OutputStreamWriter

import com.openvision.music.output.lilypond.Lilypond
import com.openvision.music.score._

import com.openvision.music.output.ScoreRenderer.lilypond
import ChordSymbol._

object Main extends App {

  val score = Score(Key(C, Major), List(
    Staff(Treble, Duration(4, 4), List(
      Voice(List(
        Note(C, 1, Duration(1, 2)),
        Note(D, 1, Duration(1, 2)),
        Note(C, 1, Duration(1, 1))
      )),
      Voice(List(
        PlaceHolder(Duration(1, 2)),
        PlaceHolder(Duration(1, 2)),
        PlaceHolder(Duration(1, 1))
      ))
  )),
    Staff(Bass, Duration(4, 4), List(
      Voice(List(
        PlaceHolder(Duration(1, 2)),
        PlaceHolder(Duration(1, 2)),
        PlaceHolder(Duration(1, 1))
      )),
      Voice(List(
        PlaceHolder(Duration(1, 2)),
        PlaceHolder(Duration(1, 2)),
        PlaceHolder(Duration(1, 1))
      ))
  ))), List(
    Chord(C.major, Duration(1, 2)),
    Chord(G.major._7, Duration(1, 2)),
    Chord(C.major, Duration(1, 2))
  ))

  Lilypond.renderScore(score, System.out)

  score.show()
  score.play()
}
