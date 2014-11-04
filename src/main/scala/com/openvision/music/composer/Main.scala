package com.openvision.music.composer

import com.openvision.music.output.lilypond.Lilypond
import com.openvision.music.score._

import com.openvision.music.output.ScoreRenderer.lilypond

object Main extends App {

  val score = Score(List(
    Voice(List(
      Note(C, 1, 0, Duration(1, 2)),
      Note(D, 1, 0, Duration(1, 2)),
      Note(C, 1, 0, Duration(1, 1))
    ))
  ))

  //score.play()

  //Lilypond.write(score, "test")
  score.show()

}
