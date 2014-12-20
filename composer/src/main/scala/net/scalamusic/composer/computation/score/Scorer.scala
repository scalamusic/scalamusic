package net.scalamusic.composer.computation.score

trait Scorer {

  val Impossible = 10000

  def apply(c: Connection): Double

  def +(s: Scorer) = AddScorer(this, s)

}
