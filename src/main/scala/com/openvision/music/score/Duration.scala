package com.openvision.music.score

case class Duration(val nominator: Int, val denominator: Int) {

  def duration: Double = nominator / denominator

}
