package com.knoldus.akka

import akka.actor.{Actor, ActorLogging, Props}
import com.knoldus.DB.{DBConnection, UserDetailsDBLayer, UserDetailsDBService}
import com.knoldus.common.User


class UserDetails(userData:List[User],dbConnection: DBConnection) extends Actor with ActorLogging{

  import UserDetails._

  val userDetailDB = new UserDetailsDBLayer
  val userDetails = new UserDetailsDBService(dbConnection,userDetailDB)

  def receive: PartialFunction[Any, Unit] = {
    case UserDetailData(users) =>
      userDetails.addManyUsers(users)
  }

}

object UserDetails{

  def props(userData: List[User],dbConnection: DBConnection): Props =
    Props(new UserDetails(userData,dbConnection))

  final case class UserDetailData(users: List[User])
}
