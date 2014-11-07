package com.openvision.music.composer.computation.score

import com.openvision.music.score.Note

object DoublingScorer extends ChordScorer {

  override def apply(ch: List[Note]): Double = {
    val preferFullChords = ch.size - ch.map(_.pitch.name).toSet.size
    preferFullChords
  }

}
