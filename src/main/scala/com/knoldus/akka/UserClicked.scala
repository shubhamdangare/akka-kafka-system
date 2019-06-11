package com.knoldus.akka

import akka.actor.{Actor, ActorLogging, Props}
import com.knoldus.DB.{DBConnection, UserClickDBLayer, UserClickDBService}
import com.knoldus.common.User
import scalikejdbc._

class UserClicked(userData: List[User], dbConnection: DBConnection)
  extends Actor with ActorLogging {

  import UserClicked._

  val userClickDb  = new UserClickDBLayer
  val userClick = new UserClickDBService(dbConnection,userClickDb)

  def receive: PartialFunction[Any, Unit] = {
    case UserData(users) =>
      userClick.addManyUsers(users)
  }

}

object UserClicked {

  def props(userData: List[User], dbConnection: DBConnection): Props =
    Props(new UserClicked(userData, dbConnection))

  final case class UserData(users: List[User])
}
