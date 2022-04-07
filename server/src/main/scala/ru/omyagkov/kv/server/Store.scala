package ru.omyagkov.kv.server

import java.util.concurrent.{ConcurrentHashMap, ConcurrentMap}

class Store  {
  private val map: ConcurrentMap[Integer, String] = new ConcurrentHashMap[Integer, String]

  /**
   * @param key
   * @return
   */
  def getValue(key: Integer): String = {
    println("request for key = " + key)
     val obj =  this.map.get(key)
     Option(obj).getOrElse("ERROR")
  }

   def putMap(key: Integer, value: String): Unit = {
    this.map.put(key, value)
    println("Saved " + key + " and " + value)
  }

}
