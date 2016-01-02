import akka.actor.{Terminated, ActorRef, Actor}
import messages._

/**
 * Created by onegrx on 02.01.16.
 */
class Server extends Actor {

  var clients = List[(String, ActorRef)]()

  def receive: Receive = {

    case Connect(username) =>
      val s = sender()
      broadcast(Info(username + "joined the chat"))
      clients = (username, s) :: clients
      context.watch(s)

    case Terminated(client) =>
      val username = getUsername(client)
      clients = clients.filter(sender() != _._2)
      broadcast(Info(username + "left the chat"))

    case Broadcast(msg: String) =>
      val username = getUsername(sender())
      broadcast(Notice(username, msg))

  }

  def getUsername(actor: ActorRef): String = {
    clients.filter(actor == _._2).head._1
  }

  def broadcast(msg: Message) {
    clients.foreach(x => x._2 ! msg)
  }

}
