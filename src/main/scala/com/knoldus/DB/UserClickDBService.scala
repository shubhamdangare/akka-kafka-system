package com.knoldus.DB

import com.knoldus.common.User

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserClickDBService(dBConnection: DBConnection, userClickDBLayer: UserClickDBLayer) {

  dBConnection.createConnectiontoDB()

  def addManyUsers(users: List[User]): Future[List[Int]] = Future {
    if (users.isEmpty) 0
    users.map(user =>
      if (addUser(user) == -1) 1
      else -1
    )

  }

  def addUser(user: User): Int = {

    if (userClickDBLayer.getCount(user.userId) == 0)
      userClickDBLayer.add(user)
    else {
      userClickDBLayer.updated(user.userId, userClickDBLayer.list(user.userId).get.numberOfCLick + 1)
      -1
    }

  }

}
