package test_actors

import akka.io.Tcp.Received
import akka.util.ByteString
import play.api.libs.json.{JsValue, Json, OFormat}

object Message{

  /*
   * For each message is mandatory to create a Writes[...] and Reads[...]
   * implicits that will help Json class to do the automatic conversion
   * from class to JSValue.
   * We need it to be private to only the companion object because otherwise
   * it will raise an exception due to the confusion between the class specific
   * element and the trait one.
   */
  private[this] implicit val helloFormat: OFormat[Hello] = Json.format[Hello]
  private[this] implicit val goodbyeFormat: OFormat[Goodbye] = Json.format[Goodbye]
  private[this] implicit val assholeFormat: OFormat[Asshole] = Json.format[Asshole]

  /*
   * Implicit for the conversion of the trait
   */
  implicit val messagesFormat: OFormat[Message] = Json.format[Message]

  /*
   * utilities for conversions of
   * String -> ByteString
   * ByteString -> String
   * Message -> String
   */
  implicit def fromStringToByteString(msg: String): ByteString = ByteString(msg)
  implicit def fromByteStringToString(msg: ByteString): String = msg.utf8String
  implicit def fromMyMessageToString(msg: Message): ByteString = fromStringToByteString(Json.toJson(msg).toString())


  implicit def fromReceivedDataToMessage(msg: Received): Message = extractMessage(msg.data)


  /*
   * Extracting the message from a string that HAS TO BE a json object
   * or it will raise an exception because it can't do the conversion.
   */
  def extractMessage(originalMessage: String): Message = {
    val elemParsed = Json.parse(originalMessage)
    val elem = Json.fromJson[Message](elemParsed)
    if(elem.isSuccess) elem.get else null
  }

}

sealed trait Message
case class Hello(number: Int) extends Message
case class Asshole(message: String) extends Message
case class Goodbye(number: Int) extends Message
