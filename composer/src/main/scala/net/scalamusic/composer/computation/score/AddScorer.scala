package net.scalamusic.composer.computation.score

case class AddScorer(s1: Scorer, s2: Scorer) extends Scorer {

  override def apply(c: Connection): Double = s1(c) + s2(c)

}
