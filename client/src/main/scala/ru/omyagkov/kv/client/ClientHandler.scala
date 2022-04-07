package ru.omyagkov.kv.client

import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
import ru.omyagkov.kv.Logging

class ClientHandler extends SimpleChannelInboundHandler[String] with Logging{
  override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
    //log.info(s"$msg \r\n")
    println(s"$msg \r\n")
  }

}
