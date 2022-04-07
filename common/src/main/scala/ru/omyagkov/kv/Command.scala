package ru.omyagkov.kv

import ru.omyagkov.kv.Syntax._

import scala.util.matching.Regex

sealed trait Command[R] {
  protected val pattern: Regex
  protected def parse(r: Regex.Match): R

  def validate(msg: String): Boolean = pattern.findFirstIn(msg.normalize).isDefined
  def map(msg: String): Option[R]    = pattern.findFirstMatchIn(msg).map(parse)
}

object Command {

  type Key   = Int
  type Value = String

  case object PUT extends Command[(Key, Value)] {
    override protected val pattern: Regex = "^PUT (\\d+):(.*)$".r

    override protected def parse(r: Regex.Match): (Key, Value) = (r.group(1).toInt, r.group(2))
  }

  case object GET extends Command[Key] {
    override protected val pattern: Regex = "^GET (\\d+)$".r

    override protected def parse(r: Regex.Match): Key = r.group(1).toInt
  }

  case object QUIT extends Command[AnyRef] {
    override protected val pattern: Regex = "^QUIT$".r

    override protected def parse(r: Regex.Match): AnyRef = AnyRef
  }

  case object SHUTDOWN extends Command[AnyRef] {
    override protected val pattern: Regex = "^SHUTDOWN$".r

    override protected def parse(r: Regex.Match) =AnyRef
  }

  val FULL_COMMAND: Regex = "^(PUT \\d+:.*|GET \\d+|QUIT|SHUTDOWN)$".r

}
