package ru.omyagkov.kv.server

import io.netty.channel.socket.SocketChannel
import io.netty.channel.{ ChannelInitializer, ChannelPipeline }
import io.netty.handler.codec.string.{ StringDecoder, StringEncoder }
import io.netty.handler.codec.{ DelimiterBasedFrameDecoder, Delimiters }

class ServerInitializer(store: Store) extends ChannelInitializer[SocketChannel] {
  override def initChannel(ch: SocketChannel): Unit = {
    val pipeline: ChannelPipeline = ch.pipeline()
    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter(): _*))
    pipeline.addLast("decoder", new StringDecoder())
    pipeline.addLast("encoder", new StringEncoder())
    pipeline.addLast("handler", new ServerHandler(store))
  }
}
