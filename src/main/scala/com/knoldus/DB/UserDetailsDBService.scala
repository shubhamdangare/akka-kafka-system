package com.knoldus.DB

import com.knoldus.common.User
import scalikejdbc._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserDetailsDBService(dBConnection: DBConnection, userDetailDB: UserDetailsDBLayer) {

  dBConnection.createConnectiontoDB()

  def addManyUsers(users: List[User]): Future[List[Int]] = Future {
    if (users.isEmpty)
      println("no user to add")
    users.map(user =>
      if (addUser(user) == -1)
        1
      else
        -1
    )

  }

  def addUser(user: User): Int = {

    if (userDetailDB.getCount(user.userId) == 0) {
      userDetailDB.add(user)
    }
    else {
      userDetailDB.updated(user.userId, user.clickedDate, user.clickedField)
      -1
    }

  }
}
