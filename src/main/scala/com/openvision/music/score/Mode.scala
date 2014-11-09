package com.openvision.music.score

import Interval._

sealed trait Mode {

  def scale(name: NoteName): List[NoteName]

  /**
   * Each entry in the list corresponds to the respective scale note. The list entry denotes the strength of the leading note on
   * a scale from 0.0 to 1.0.
   * @return
   */
  val leadingNotes: List[Double]

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
    major(2)
  )

  override val leadingNotes = List(
    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0
  )

}

object Minor extends HalfToneMode {

  override val steps = List(
    major(2),
    minor(2),
    major(2),
    major(2),
    minor(2),
    augmented(2) // harmonic minor?
  )

  override val leadingNotes = List(
    0.0, 0.0, 0.0, 0.0, 0.0, 0.5, 1.0
  )

}
