package com.openvision.music.composer.computation.score

import com.openvision.music.score.Note

trait ChordScorer extends Scorer {

  def apply(ch: List[Note]): Double

  override def apply(a: List[Note], b: List[Note]): Double = apply(a) + apply(b)

}
