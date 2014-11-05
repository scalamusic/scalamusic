package com.openvision.music.score

case class Duration(val nominator: Int, val denominator: Int) {

  def duration: Double = nominator.asInstanceOf[Double] / denominator.asInstanceOf[Double]

  override def toString = {
    val nomStr = if (nominator != 1) "*" + nominator else ""
    denominator + nomStr
  }

}
