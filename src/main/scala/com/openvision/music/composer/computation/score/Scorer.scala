package com.openvision.music.composer.computation.score

import com.openvision.music.score._

trait Scorer {

  def apply(a: List[Note], b: List[Note]): Double

  def +(s: Scorer) = AddScorer(this, s)

}
