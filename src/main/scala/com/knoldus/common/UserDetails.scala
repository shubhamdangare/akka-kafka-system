package com.knoldus.common

import scalikejdbc._

case class UserDetails(userId: String, clickedField: String,clickedDate:String)

object UserDetails extends SQLSyntaxSupport[UserDetails] {
  override val tableName = "UserDetails"
  override val useSnakeCaseColumnName = false

  override val nameConverters = Map(
    "^userId$" -> "userId",
    "^clickedField$" -> "clickedField",
    "^clickedDate$" -> "clickedDate"
  )

  def apply(e: SyntaxProvider[UserDetails])(rs: WrappedResultSet): UserDetails = apply(e.resultName)(rs)

  def apply(e: ResultName[UserDetails])(rs: WrappedResultSet): UserDetails =
    new UserDetails(userId = rs.string(e.userId), clickedField = rs.string(e.clickedField),
      clickedDate = rs.string(e.clickedField)
    )

}


