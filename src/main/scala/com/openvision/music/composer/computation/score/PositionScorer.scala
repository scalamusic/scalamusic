package com.openvision.music.composer.computation.score

import com.openvision.music.score.Note
import com.openvision.music.score.Interval
import com.openvision.music.score.Interval._

object PositionScorer extends ChordScorer {

  private def distancePenalty(distance: Int, i: Interval = major(6)) = {
    val avoidSameNote = (if (distance == 0)
      1.0
    else
      0.0)
    val avoidGaps = distance - i.halfTones match {
      case v if v < 0 => 0
      case v => v
    }
    avoidSameNote + avoidGaps
  }

  override def apply(ch: List[Note]): Double = {

    def distance(i1: Int, i2: Int) = ch(i1).pitch.toMidi - ch(i2).pitch.toMidi

    val dSA = distance(0, 1)
    val dAT = distance(1, 2)
    val dTB = distance(2, 3)

    val balancePenalty = if (dAT > 0.0) Math.abs((dSA / dAT) - 1.0) else Math.abs((dSA / 1.0) - 1.0)

    distancePenalty(dSA) + distancePenalty(dAT) + distancePenalty(dTB, perfect(12)) + balancePenalty
  }

}

