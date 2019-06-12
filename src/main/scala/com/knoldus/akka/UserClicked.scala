package com.knoldus.akka

import akka.actor.{Actor, ActorLogging, Props}
import com.knoldus.DB.{DBConnection, UserClickDBService}
import com.knoldus.common.User

class UserClicked(userData: List[User], dbConnection: DBConnection,userClick: UserClickDBService)
  extends Actor with ActorLogging {

  import UserClicked._

  def receive: PartialFunction[Any, Unit] = {
    case UserData(users) =>
      userClick.addManyUsers(users)
  }

}

object UserClicked {

  def props(userData: List[User], dbConnection: DBConnection ,userClick: UserClickDBService): Props =
    Props(new UserClicked(userData, dbConnection,userClick))

  final case class UserData(users: List[User])
}
