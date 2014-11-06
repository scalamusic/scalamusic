package com.openvision.music.composer.computation

import java.util.NoSuchElementException

import com.openvision.music.score.{Pitch, ChordSymbol, Note}

import scala.collection.mutable.ArrayBuffer

class ChordIterator(symbol: ChordSymbol, configuration: List[Configuration], invert: Boolean = false) extends Iterator[List[Note]] {

  private def wrap(i: Int) = {
    if (i < 0) {
      symbol.notes.size + i
    } else {
      i
    }
  }

  private val bassIndex = configuration.size - 1
  private def isBass(i: Int) = (i == bassIndex)

  private def nextName(voiceIndex: Int, noteIndex: Int) = {
    if (isBass(voiceIndex) && !invert) {
      symbol.bass
    } else {
      symbol.notes(wrap(noteIndex - 1))
    }
  }

  private def next(voiceIndex: Int, n: Note) = {
    val i = symbol.notes.indexOf(n.pitch.name)
    val name = nextName(voiceIndex, i)
    val nextOctave = if (name.index >= n.pitch.name.index) n.pitch.octave - 1 else n.pitch.octave
    val nextPitch = Pitch(name, nextOctave)
    Console.println(s"next($voiceIndex, ${n.pitch}) = $nextPitch")
    Note(nextPitch, n.duration)
  }

  private def range(i: Int) = configuration(i).range

  private def max(i: Int, n: Note): Note = {
    if ((n.pitch > range(i).max) || (isBass(i) && !invert && (n.pitch.name != symbol.bass))) {
      max(i, next(i, n))
    } else {
      n
    }
  }

  val mutable = configuration.map(!_.note.isDefined)

  val state = configuration.map(_.note).foldLeft(ArrayBuffer[Note]()) { case (buffer, n) =>
    val i = buffer.size
    val note = n.getOrElse(max(i, buffer(i - 1)))
    buffer += note
  }

  private def next(i: Int): Unit = {
    if (mutable(i)) {
      val nextNote = next(i, state(i))
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
    } else {
      if (i > 0) {
        next(i - 1)
      } else {
        state.clear
        throw new NoSuchElementException
      }
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
