package ru.omyagkov.kv.server

import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.{ ChannelHandlerContext, SimpleChannelInboundHandler }
import ru.omyagkov.kv.Syntax.CommandOps
import ru.omyagkov.kv.{ Command, Logging }

@Sharable
class ServerHandler(store: Store) extends SimpleChannelInboundHandler[String] with Logging {

  @throws[Exception]
  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    ctx.flush()
  }

  @throws[Exception]
  override protected def channelRead0(ctx: ChannelHandlerContext, s: String): Unit = {

    Command.FULL_COMMAND.findFirstIn(s.normalize) match {
      case Some(msg) => process(msg, ctx, store)
      case _         => log.error(s"Invalid input command: $s\r\n")
    }
  }

  /**
   * @param msg
   * @param ctx
   * @param store
   */
  def process(msg: String, ctx: ChannelHandlerContext, store: Store): Unit = {
    println(msg)
    msg match {
      case _ if Command.PUT.validate(msg) =>
        Command.PUT.map(msg).map {
          case (key, value) =>
            store.putMap(key, value)
            ctx.writeAndFlush(
              "[" + value + " SAVED AT " + ctx.channel.remoteAddress + "] " + " \r\n"
            )
        }
      case _ if Command.GET.validate(msg) =>
        Command.GET.map(msg).map { key =>
          ctx.writeAndFlush(store.getValue(key) + " \r\n")
        }
      case _ if Command.SHUTDOWN.validate(msg) =>
        ctx.channel().eventLoop().shutdownGracefully()
        sys.exit(1)

      case _ => println("ERROR\r\n")
    }
  }

  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = {
    ctx.flush()
  }
}
