package com.openvision.music.score

sealed class NoteName(val pitch: Integer)

object C extends NoteName(0)
object D extends NoteName(2)
object E extends NoteName(4)
object F extends NoteName(5)
object G extends NoteName(7)
object A extends NoteName(9)
object B extends NoteName(11)
