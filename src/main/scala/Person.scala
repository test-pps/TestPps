
object Gender extends Enumeration {
  val Man, Woman = Value
}

sealed trait Person {
  def name: String
  def surname: String
  def address: String
  def phoneNumber: Int
  def gender: Gender.Value
  def phoneNumber_=(phoneNumber: Int): Unit
  def address_=(addr: String): Unit
}

case class Student(name:String, surname: String, gender: Gender.Value, private var _phoneNumber: Int, private var _address: String) extends Person {

  override def address: String = this._address

  override def address_=(addr: String): Unit = this._address = addr
  
  override def phoneNumber: Int = this._phoneNumber

  override def phoneNumber_=(phone: Int): Unit = this._phoneNumber = phone
}


object TestApp extends App {

  val student = Student("Francesco", "Grandinetti", Gender.Man, 666, "via x")

  student.address = "Via zaccherini 25"

  println(student)

}
