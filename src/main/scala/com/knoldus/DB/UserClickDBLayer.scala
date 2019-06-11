package com.knoldus.DB

import com.knoldus.common.{User, UserClick}
import scalikejdbc._
import scalikejdbc.interpolation.SQLSyntax.count


class UserClickDBLayer {

  implicit val session = AutoSession

  def list(ids: String): Option[UserClick] = {
    val userTable = UserClick.syntax("m")
    withSQL {
      select.from(UserClick as userTable).where.eq(userTable.userId, ids)
    }.map(UserClick(userTable.resultName)).single().apply()
  }

  def listAll: Option[List[UserClick]] = {
    val userTable = UserClick.syntax("m")
    Option(withSQL {
      select.from(UserClick as userTable)
    }.map(UserClick(userTable.resultName)).list().apply())
  }

  def addMany(users: List[User]): List[Int] = {
    DB.localTx { implicit s =>
      users.map(data => add(data))
    }
  }

  def add(user: User): Int = {
    if(getCount(user.userId) == 0)
    withSQL {
      insert.into(UserClick).values(user.userId, user.isClicked)
    }.update().apply()
    else
      -1
  }

  def getCount(userId: String): Long = {
    val userTable = UserClick.syntax("m")
    withSQL(
      select(count(userTable.userId)).from(UserClick as userTable).where.eq(userTable.userId, userId)
    ).map(rs => rs.long(1)).single.apply().get
  }

  def updated(ids: String, click: Int): Long = {
    withSQL {
      update(UserClick).set(
        UserClick.column.numberOfCLick -> click
      ).where.eq(UserClick.column.userId, ids)
    }.update.apply()
  }
}
