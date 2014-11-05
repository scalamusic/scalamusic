package com.openvision.music.composer.computation

import com.openvision.music.score.Note

case class Configuration(note: Option[Note], range: Range)

object Configuration {

  def apply(sopranoNote: Note): List[Configuration] = List(
    Configuration(Some(sopranoNote), Range.soprano),
    Configuration(None, Range.alto),
    Configuration(None, Range.tenor),
    Configuration(None, Range.bass)
  )

}