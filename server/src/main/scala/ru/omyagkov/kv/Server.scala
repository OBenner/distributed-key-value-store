package ru.omyagkov.kv

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.{ ChannelOption, EventLoopGroup }
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import ru.omyagkov.kv.server.{ ServerInitializer, Store }

object Server extends App {
  val address     = args(0)
  val server: Srv = Srv(address.split(":")(0), address.split(":")(1).toInt)
  run()

  @throws[InterruptedException]
  def run(): Unit = {
    val bossGroup: EventLoopGroup = new NioEventLoopGroup
    val workGroup: EventLoopGroup = new NioEventLoopGroup
    val store                     = new Store()
    try {
      val keepAlive: java.lang.Boolean = true
      val sb                           = new ServerBootstrap
      sb.group(bossGroup, workGroup)
        .channel(classOf[NioServerSocketChannel])
        .childHandler(new ServerInitializer(store))
        .childOption(ChannelOption.SO_KEEPALIVE, keepAlive)
      System.out.println("Running")
      val f = sb.bind(server.host, server.port).sync
      f.channel.closeFuture.sync
    } finally {
      bossGroup.shutdownGracefully
      workGroup.shutdownGracefully
    }
  }

}
case class Srv(host: String, port: Int)
