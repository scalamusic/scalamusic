import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._
import sbt.Keys

packageArchetype.java_application

name := "scalamusic"

scalaVersion := "2.11.4"

version := "0.0.2"

lazy val composer = project.settings(
  scalaVersion := "2.11.4",
  Keys.mainClass in (Compile) := Some("net.scalamusic.composer.Main")
).dependsOn(score)

lazy val score = project.settings(
  scalaVersion := "2.11.4",
  libraryDependencies += "de.sciss" %% "scalamidi" % "0.2.0",
  libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2"
)