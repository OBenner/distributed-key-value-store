import sbt._

object Version {

  val Slf4jApi       = "1.7.32"
  val Log4jSlf4jImpl = "2.14.1"
  val Logback        = "1.2.11"

  val ScalaTest = "3.2.11"
  val Parser    = "2.1.1"
  val Netty     = "4.1.75.Final"

  val Config = "1.2.1"

}

object Dependencies {

  object Netty {
    val Netty    = "io.netty" % "netty-all" % Version.Netty
    lazy val All = Seq(Netty)
  }

  object Config {
    val Conf = "com.typesafe" % "config" % Version.Config

    lazy val All = Seq(Conf)
  }

  object Testing {
    val ScalaTest = "org.scalatest" %% "scalatest" % Version.ScalaTest % Test
    lazy val All  = Seq(ScalaTest)
  }

  object Logging {
    val Logback  = "ch.qos.logback" % "logback-classic" % Version.Logback
    lazy val All = Seq(Logback)
  }

  object Utils {
    val Parser   = "org.scala-lang.modules" %% "scala-parser-combinators" % Version.Parser
    lazy val All = Seq(Parser)
  }
}



//import sbt._
//
//object Version {
//
//  val Slf4jApi = "1.7.32"
//  val Log4jSlf4jImpl = "2.14.1"
//  val Logback = "1.2.6"
//  val Log4j2Version    = "2.11.1"
//  val ScalaTest = "3.2.9"
//  val Parser = "2.1.0"
//  val Netty = "4.1.75.Final"
//
//  val Config = "1.2.1"
//
//}
//
//object Dependencies {
//  object Netty {
//    val Netty = "io.netty" % "netty-all" % Version.Netty
//    lazy val All = Seq(Netty)
//  }
//  object Config {
//    val Conf =  "com.typesafe" % "config" % Version.Config
//
//    lazy val All = Seq(Conf)
//  }
//
//  object Logging {
//    lazy val log4jApi            = "org.apache.logging.log4j" % "log4j-api" % Version.Log4j2Version
//    lazy val log4jSlf4j          = "org.apache.logging.log4j" % "log4j-slf4j-impl" % Version.Log4j2Version
//    lazy val log4jCore           = "org.apache.logging.log4j" % "log4j-core" % Version.Log4j2Version
//    lazy val Slf4j               = "com.typesafe.akka" %% "akka-slf4j" % "2.5.29"
//    lazy val All = Seq(log4jApi, log4jSlf4j, log4jCore, Slf4j)
//  }
//  object Testing {
//    val ScalaTest = "org.scalatest" %% "scalatest" % Version.ScalaTest % Test
//    lazy val All = Seq(ScalaTest)
//  }
//
//
//
//  object Utils {
//    val Parser = "org.scala-lang.modules" %% "scala-parser-combinators" % Version.Parser
//    lazy val All = Seq(Parser)
//  }
//}