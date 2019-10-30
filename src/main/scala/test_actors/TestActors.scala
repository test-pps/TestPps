package test_actors

import java.net.InetSocketAddress

import akka.actor.ActorSystem

object TestActors extends App {
  val system = ActorSystem("mySystem")
  val system2 = ActorSystem("mySystem2")

  val client = system.actorOf(Client(new InetSocketAddress("localhost", 6789)))
  val server = system2.actorOf(Server())
}
