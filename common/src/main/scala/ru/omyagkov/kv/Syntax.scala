package ru.omyagkov.kv

object Syntax {

  implicit class CommandOps(private val data: String) extends AnyVal {
    def normalize: String = data.trim.replaceAll("( )+", " ").replaceAll("\r","")
  }

}
