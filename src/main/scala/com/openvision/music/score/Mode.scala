package com.openvision.music.score

import Interval._

sealed trait Mode {

  def scale(name: NoteName): List[NoteName]

}


sealed trait HalfToneMode extends Mode {

  protected def steps: List[Interval]

  override def scale(name: NoteName): List[NoteName] = {
    steps.foldLeft(List[NoteName](name)) { (list, interval) =>
      list match {
        case (x :: xs) => (x + interval) :: x :: xs
      }
    }.reverse
  }

}

object Major extends HalfToneMode {

  override val steps = List(
    major(2),
    major(2),
    minor(2),
    major(2),
    major(2),
    major(2),
    minor(2)
  )

}

object Minor extends HalfToneMode {

  override val steps = List(
    major(2),
    minor(2),
    major(2),
    major(2),
    minor(2),
    major(2),
    major(2)
  )

}
