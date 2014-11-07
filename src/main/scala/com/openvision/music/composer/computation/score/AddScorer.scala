package com.openvision.music.composer.computation.score

import com.openvision.music.score.Note

case class AddScorer(s1: Scorer, s2: Scorer) extends Scorer {

  override def apply(a: List[Note], b: List[Note]): Double = s1(a, b) + s2(a, b)

}
