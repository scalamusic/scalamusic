package com.openvision.music.composer.computation

import java.util.NoSuchElementException

import com.openvision.music.score.{Pitch, ChordSymbol, Note}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class ChordIterator(symbol: ChordSymbol, configuration: List[Configuration]) extends Iterator[List[Note]] {

  private def wrap(i: Int) = {
    if (i < 0) {
      symbol.notes.size + i
    } else {
      i
    }
  }

  private def next(n: Note) = {
    val i = symbol.notes.indexOf(n.pitch.name)
    val nextName = symbol.notes(wrap(i - 1))
    val nextOctave = if (nextName.index > n.pitch.name.index) n.pitch.octave - 1 else n.pitch.octave
    Note(Pitch(nextName, nextOctave), n.duration)
  }

  private def range(i: Int) = configuration(i).range

  private def max(i: Int, n: Note): Note = {
    if (n.pitch > range(i).max)
      max(i, next(n))
    else
      n
  }

  val state: mutable.Buffer[Note] = configuration.map(_.note).foldLeft(ArrayBuffer[Note]()) { case (buffer, n) =>
    val i = buffer.size
    val note = n.getOrElse(max(i, buffer(i - 1)))
    buffer += note
  }

  private def next(i: Int): Unit = {
    val nextNote = next(state(i))
    if (nextNote.pitch < range(i).min) {
      // next for top voice:
      if (i > 0) {
        next(i - 1)
        state(i) = max(i, state(i - 1))
      } else {
        state.clear
        throw new NoSuchElementException
      }
    } else {
      state(i) = nextNote
    }
  }

  override def hasNext: Boolean = {
    state.size > 0
  }

  override def next(): List[Note] = {
    val result = state.toList
    try {
      next(state.size - 1)
      result
    } catch {
      case e: NoSuchElementException => result
    }
  }
}
