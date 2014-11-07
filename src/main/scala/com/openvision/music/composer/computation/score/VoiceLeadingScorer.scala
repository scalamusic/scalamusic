package com.openvision.music.composer.computation.score

import com.openvision.music.score.{Interval, Note}
import com.openvision.music.score.Interval._

object VoiceLeadingScorer extends Scorer {

//  def diff(a: List[Note]) = {
//    (a.zipWithIndex zip a.zipWithIndex).flatMap { case ((u, ui), (v, vi)) =>
//      if (u != v) {
//        Some((ui, vi, u.pitch.toMidi - v.pitch.toMidi))
//      } else {
//        None
//      }
//    }
//  }

  private val perfectFifth = 7
  private val perfectOctave = 12
  private val perfectUnison = 0

  override def apply(a: List[Note], b: List[Note]): Double = {

    val diff = (a zip b).map { case (u, v) =>
      v.pitch.toMidi - u.pitch.toMidi
    }

    // parallels:
    val parallels = diff.zipWithIndex.groupBy(_._1).map { case (d, list) =>
      (d, list.map(_._2))
    }

    val dir = diff.map(Math.signum(_)).zipWithIndex.groupBy(_._1).map { case (d, list) =>
      (d, list.map(_._2))
    }

    val parallelPenalty = parallels.map { case (d, is) =>
      if (is.size > 1) {
        (is zip is).map { case (u, v) =>
          val d = a(u).pitch.toMidi - a(v).pitch.toMidi
          d match {
            case `perfectFifth` => 1000
            case `perfectOctave` => 2000
            case `perfectUnison` => 2000
          }
        }.sum
      } else {
        0
      }
    }.sum

    val sameDirPenalty = dir.values.map(_.size).max

    val allSameDirPenalty = dir.size match {
      case 1 => 100
      case _ => 0
    }

    parallelPenalty +
      sameDirPenalty +
      allSameDirPenalty

  }

}
