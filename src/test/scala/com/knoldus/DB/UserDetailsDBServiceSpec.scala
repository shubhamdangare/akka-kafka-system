package com.knoldus.DB

import com.knoldus.common.User
import org.mockito.Mockito._
import org.mockito.stubbing.OngoingStubbing
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}

class UserDetailsDBServiceSpec extends WordSpec with ScalaFutures with Matchers with MockitoSugar{

  protected val dbConnection: DBConnection = mock[DBConnection]
  protected val dbLayer: UserDetailsDBLayer = mock[UserDetailsDBLayer]
  val userService = new UserDetailsDBService(dbConnection,dbLayer)
  val user:List[User] = List(User("2",true,"sss","ddd"))

  "UserService#addUser" should {
    "add user successfully" in {

      when(user.foreach(data=> dbLayer.getCount(data.userId))).asInstanceOf[OngoingStubbing[Long]].thenReturn(1)
      when(
        user.foreach(data=> dbLayer.updated(data.userId,data.clickedField,data.clickedDate))
      ).asInstanceOf[OngoingStubbing[Long]].thenReturn(1)

      whenReady(userService.addManyUsers(user))( _ shouldBe List(1))

    }

  }
}
