package com.openvision.music.composer

import java.io.OutputStreamWriter

import com.openvision.music.output.lilypond.Lilypond
import com.openvision.music.score._

import com.openvision.music.output.ScoreRenderer.lilypond

object Main extends App {

  val score = Score(Key(C, Major), List(Staff(Treble, Duration(4, 4), List(
    Voice(List(
      Note(C, 1, Duration(1, 2)),
      Note(D, 1, Duration(1, 2)),
      Note(C, 1, Duration(1, 1))
    )),
    Voice(List(
      Note(A, 0, Duration(1, 1)),
      Note(A, 0, Duration(1, 1))
    ))
  ))))

  //score.play()

  //Lilypond.write(score, "test")
  Lilypond.renderScore(score, new OutputStreamWriter(System.out))

  score.show()

}
