package test_actors

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, Props}
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import play.api.libs.json._

object Server {
  def apply() = Props(classOf[Server])
}

class Server extends Actor {

  import akka.io.Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 6789))

  override def receive: Receive = {
    case b @ Bound(_) =>
      context.parent ! b

    case CommandFailed(_: Bind) => context.stop(self)

    case c @ Connected(_, _) =>
      println("New client arrived! -> " + c)
      val connection = sender()
      val handler = context.actorOf(SimplisticHandler(connection))
      connection ! Register(handler)
  }

}

class SimplisticHandler(connection: ActorRef) extends Actor {

  import Message._

  override def receive: Receive = {
    case CommandFailed(w: Write) =>
      // O/S buffer was full
      println("write failed")
    case Received(data) =>
      println(data.utf8String)
      val msgReceived = extractMessage(data)
      println("Server received -> " + msgReceived)
      msgReceived match {
        case Hello(_) => connection ! Write(Goodbye(5))
        case Goodbye(_) => connection ! Write(Asshole("You're really an asshole, do you?"))
        case Asshole(_) => connection ! Write(Asshole("Take it back!!!"))
        case _ =>
      }
    case "close" =>
    connection ! Close
    case _: ConnectionClosed =>
      println("connection closed")
      context.stop(self)
  }
}

object SimplisticHandler {
  def apply(connection: ActorRef) = Props(classOf[SimplisticHandler], connection)
}