import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.channel.{ Channel, ChannelOption, EventLoopGroup }
import ru.omyagkov.kv.Syntax.CommandOps
import ru.omyagkov.kv.client.ClientInitializer
import ru.omyagkov.kv.{ Command, Logging }

import java.io.{ BufferedReader, InputStreamReader }

object Client extends Logging {

  def main(args: Array[String]): Unit = {
    run(args)

  }

  def run(args: Array[String]): Unit = {
    val servers: List[Servers] = args(0)
      .split(",")
      .map(s => {
        Servers(s.split(":")(0), s.split(":")(1).toInt)
      })
      .toList
    val group: EventLoopGroup = new NioEventLoopGroup()
    val bootstrap             = configureBootstrap(group)
    val input                 = new BufferedReader(new InputStreamReader(System.in))
    val channels              = createChannels(servers, bootstrap)

    while (true) {
      val msg = input.readLine()
      if (msg.nonEmpty) {
        Command.FULL_COMMAND.findFirstIn(msg.normalize) match {
          case Some(msg) => process(msg)
          case _         => log.error(s"Invalid input command: $msg\r\n")
        }
      }

    }

    def process(msg: String) = {
      msg match {
        case _ if Command.PUT.validate(msg) =>
          Command.PUT.map(msg).map {
            case (key, _) =>
              val node = getNode(key)
              channels.get(node).fold(println("ERROR\r\n"))(_.writeAndFlush(msg + "\r\n"))
          }
        case _ if Command.GET.validate(msg) =>
          Command.GET.map(msg).map { key =>
            val node = getNode(key)
            channels.get(node).fold(println("ERROR\r\n"))(_.writeAndFlush(msg + "\r\n"))
          }
        case _ if Command.SHUTDOWN.validate(msg) =>
          channels.foreach(_._2.writeAndFlush(msg + "\r\n"))
          group.shutdownGracefully
        case _ if Command.QUIT.validate(msg) =>
          println("BYE\r\n")
          group.shutdownGracefully
          sys.exit(1)
        case _ => println("ERROR\r\n")
      }
    }

    def getNode(key: Int): Int = key.hashCode % channels.size

  }

  def configureBootstrap(group: EventLoopGroup): Bootstrap = {
    val keepAlive: java.lang.Boolean = true
    new Bootstrap()
      .group(group)
      .channel(classOf[NioSocketChannel])
      .option(ChannelOption.SO_KEEPALIVE, keepAlive)
      .handler(new ClientInitializer)
  }

  def createChannels(servers: List[Servers], bootstrap: Bootstrap): Map[Int, Channel] = {
    servers
      .map(createChannel(bootstrap))
      .zipWithIndex
      .map {
        case (channel, index) => index -> channel
      }
      .toMap
  }

  def createChannel(bootstrap: Bootstrap)(server: Servers): Channel = {
    bootstrap.connect(server.host, server.port).sync.channel
  }

}
case class Servers(host: String, port: Int)
