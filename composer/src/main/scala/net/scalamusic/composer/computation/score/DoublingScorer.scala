package net.scalamusic.composer.computation.score

object DoublingScorer extends ChordScorer {

  val Missing3rdPenalty = 500
  val Doubled3rdPenalty = 200

  override def apply(ch: ConnectionChord): Double = {
    ch.count(1) match {
      case 0 => Missing3rdPenalty
      case 1 => 0 // Good
      case 2 => Doubled3rdPenalty
      case _ => Impossible // More than 2 :o
    }
  }

}
