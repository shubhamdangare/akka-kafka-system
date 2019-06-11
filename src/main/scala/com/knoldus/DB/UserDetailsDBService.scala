package com.knoldus.DB

import com.knoldus.common.User
import scalikejdbc._

class UserDetailsDBService(dBConnection: DBConnection,userDetailDB :UserDetailsDBLayer){

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

    if (userDetailDB.getCount(user.userId) == 0){
      userDetailDB.add(user)}
    else {
      userDetailDB.updated(user.userId,user.clickedDate,user.clickedField)
      println("user already exists")
      -1
    }
  }
}
