name := "distributed-key-value-store"

version := "0.1"

scalaVersion := "2.13.8"

idePackagePrefix := Some("ru.omyagkov")

import Dependencies._

ThisBuild / organization := "ru.omyagkov"

lazy val root = (project in file(".")).aggregate(Client, Server, Common)

lazy val Client = appModule("client")
  .settings(
    libraryDependencies ++=
      Testing.All ++
        Utils.All
        ++ Netty.All
        ++ Config.All
  )
  .dependsOn(Common)

lazy val Server = appModule("server")
  .settings(
    libraryDependencies ++=
      Testing.All ++
        Utils.All
        ++ Netty.All
  )
  .dependsOn(Common)

lazy val Common = appModule("common").settings(libraryDependencies ++= Logging.All)

def appModule(moduleID: String): Project = {
  Project(id = moduleID, base = file(moduleID))
    .enablePlugins(JavaAppPackaging)
    .settings(name := moduleID, Universal / packageName := moduleID, Settings.commonSettings)
}
