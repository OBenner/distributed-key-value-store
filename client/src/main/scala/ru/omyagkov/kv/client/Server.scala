//package ru.omyagkov.kv.client
//
//import com.typesafe.config.Config
//
//
//
//case class Server(host: String, port: Int)
//
//object Server {
//
//  def apply(config: Config): List[Server] = {
//    val servers = config.getString("servers")
//    servers.split(",").map(_.split(":")).map(hp => Server(hp(0), hp(1).toInt)).toList
//  }
//}
