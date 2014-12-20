package net.scalamusic.composer.computation.score

trait ChordScorer extends Scorer {

  def apply(ch: ConnectionChord): Double

  override def apply(c: Connection): Double = apply(c.a) + apply(c.b)

}
