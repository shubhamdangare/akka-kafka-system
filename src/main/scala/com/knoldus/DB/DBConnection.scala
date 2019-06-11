
package com.knoldus.DB

import scalikejdbc._

class DBConnection {

  def createConnectiontoDB():Unit = {
    Class.forName("com.mysql.jdbc.Driver")
    ConnectionPool.singleton("jdbc:mysql://localhost:3306/UserData", "root", "root")
  }
}
