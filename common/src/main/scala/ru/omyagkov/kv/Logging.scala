package ru.omyagkov.kv

import org.slf4j.{ Logger, LoggerFactory }

trait Logging {
  protected val log: Logger = LoggerFactory.getLogger(this.getClass)
}
