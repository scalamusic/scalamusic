package com.openvision.music.score

sealed class NoteName(pitch: Integer)

case class C() extends NoteName(0)
case class D() extends NoteName(2)
case class E() extends NoteName(4)
case class F() extends NoteName(5)
case class G() extends NoteName(7)
case class A() extends NoteName(9)
case class B() extends NoteName(11)
