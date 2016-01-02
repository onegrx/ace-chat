package messages

/**
 * Created by onegrx on 02.01.16.
 */
abstract class Message

case class Connect(username: String) extends Message
case class Broadcast(msg: String) extends Message
case class Info(msg: String) extends Message
case class Notice(from: String, msg: String) extends Message
