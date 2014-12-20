package net.scalamusic.output

case class Format(extension: String)

object Format {

  val Pdf = Format("pdf")
  val Png = Format("png")

}
