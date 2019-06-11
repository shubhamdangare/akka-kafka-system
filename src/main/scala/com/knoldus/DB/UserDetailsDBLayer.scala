package com.knoldus.DB

import com.knoldus.common.{User, UserDetails}
import scalikejdbc._
import scalikejdbc.interpolation.SQLSyntax.count

class UserDetailsDBLayer {

  implicit val session = AutoSession

  def list(ids: String): Option[UserDetails] = {
    val userTable = UserDetails.syntax("m")
    withSQL {
      select.from(UserDetails as userTable).where.eq(userTable.userId, ids)
    }.map(UserDetails(userTable.resultName)).single().apply()
  }

  def listAll: Option[List[UserDetails]] = {
    val userTable = UserDetails.syntax("m")
    Option(withSQL {
      select.from(UserDetails as userTable)
    }.map(UserDetails(userTable.resultName)).list().apply())
  }

  def addMany(users: List[User]): List[Int] = {
    DB.localTx { implicit s =>
      users.map(data => add(data))
    }
  }

  def add(user: User): Int = {
    if(getCount(user.userId) == 0)
      withSQL {
        insert.into(UserDetails).values(user.userId, user.clickedField,user.clickedDate)
      }.update().apply()
    else
      -1
  }

  def getCount(userId: String): Long = {
    val userTable = UserDetails.syntax("m")
    withSQL(
      select(count(userTable.userId)).from(UserDetails as userTable).where.eq(userTable.userId, userId)
    ).map(rs => rs.long(1)).single.apply().get
  }

  def updated(ids: String, clickedDate: String,clickedField: String): Long = {
    withSQL {
      update(UserDetails).set(
        UserDetails.column.clickedDate -> clickedDate,
        UserDetails.column.clickedField -> clickedField
      ).where.eq(UserDetails.column.userId, ids)
    }.update.apply()
  }

}
