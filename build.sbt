import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._

packageArchetype.java_application

name := "composer"

scalaVersion := "2.11.4"

version := "0.0.2"

libraryDependencies += "de.sciss" %% "scalamidi" % "0.2.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2"

//mainClass := Some("com.openvision.music.composer.Main")

Keys.mainClass in (Compile) := Some("com.openvision.music.composer.Main")