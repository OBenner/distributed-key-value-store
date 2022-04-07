import sbt.Keys._
import sbt._

object Settings {

  object ScalaVersion {
    val Major = "2.13"
    val It    = s"$Major.6"
  }

  lazy val commonSettings = Seq(
    scalaVersion := ScalaVersion.It
  )

  // Add this for suppress warnings in datamodel module, maybe can change this in future
  lazy val suppressWarnSettings = Seq(
    scalacOptions --= Seq(
      "-Ywarn-dead-code",
      "-Ywarn-unused",
      "-Ywarn-unused:implicits",
      "-Ywarn-unused:imports",
      "-Ywarn-unused:locals",
      "-Ywarn-unused:params",
      "-Ywarn-unused:patvars",
      "-Ywarn-unused:privates",
      "-Xlint"
    )
  )

}