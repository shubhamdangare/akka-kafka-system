package com.knoldus.DB

import com.knoldus.common.User
import scalikejdbc._

class UserClickDBService(dBConnection: DBConnection,userClickDBLayer: UserClickDBLayer){

  dBConnection.createConnectiontoDB()

  def addManyUsers(users: List[User]): List[Unit] = {
    if(users.isEmpty)
      println("no user to add")
    users.map(user =>
      if (addUser(user) == -1)
        println("user not added" + user)
      else
        println("user added succesfully")
    )

  }

  def addUser(user: User): Int = {

    if (userClickDBLayer.getCount(user.userId) == 0)
      userClickDBLayer.add(user)
    else {
      userClickDBLayer.updated(user.userId,userClickDBLayer.list(user.userId).get.numberOfCLick + 1)
      println("user already exists")
       -1
    }

  }

}
