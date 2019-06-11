package com.knoldus.common

import scalikejdbc._

case class UserClick(userId: String, numberOfCLick: Integer)

object UserClick extends SQLSyntaxSupport[UserClick] {
  override val tableName = "UserClick"
  override val useSnakeCaseColumnName = false

  override val nameConverters = Map(
    "^userId$" -> "userId",
    "^numberOfCLick$" -> "numberOfCLick"
  )

  def apply(e: SyntaxProvider[UserClick])(rs: WrappedResultSet): UserClick = apply(e.resultName)(rs)

  def apply(e: ResultName[UserClick])(rs: WrappedResultSet): UserClick =
    new UserClick(userId = rs.string(e.userId), numberOfCLick = rs.int(e.numberOfCLick))

}

