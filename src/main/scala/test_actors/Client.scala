package test_actors

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString

object Client {
  def apply(remote: InetSocketAddress) = Props(classOf[Client], remote)

  val message: String = "ciao"
}

class Client(remote: InetSocketAddress) extends Actor {

  import akka.io.Tcp._
  import Message._
  import context.system

  IO(Tcp) ! Connect(remote)

  def receive: Receive = {
    case CommandFailed(_: Connect) =>
      println("connect failed")
      context.stop(self)

    case c @ Connected(_, _) =>
      println("Client connected! -> " + c)
      val connection = sender()
      connection ! Register(self)
      connection ==> Asshole("bitch")
      context.become {
        case CommandFailed(w: Write) =>
          // O/S buffer was full
          println("write failed")
        case Received(data) =>
          val msgReceived = extractMessage(data)
          println("client received -> " + msgReceived)
        case "close" =>
          connection ! Close
        case _: ConnectionClosed =>
          println("connection closed")
          context.stop(self)
      }
  }
}
