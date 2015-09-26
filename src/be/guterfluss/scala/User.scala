package be.guterfluss.scala

import be.guterfluss.immutability.{Resident, Address}


case class User(email: Email, password: Password)

case class Password(content: String)

case class Email(content: String)

case class Appartment(address: Address,
                      residents: List[Resident]) {
  def addResident(resident: Resident): Appartment = {
    copy(residents = resident :: residents)
  }
}

object User {

  type FirstName = String

  val four = 4
  // immutable
  var n = 2 // mutable


  def filterLines(lines: List[String]) = lines.filter(_.startsWith("BUG"))
}
