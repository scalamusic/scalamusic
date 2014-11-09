package com.openvision.music.score

case class Key(name: NoteName, mode: Mode) {

  lazy val scale: List[NoteName] = mode.scale(name)

  lazy val leadingNotes: Map[NoteName, Double] = scale.zipWithIndex.map { case (n, i) =>
    (n, mode.leadingNotes(i))
  }.toMap

}
