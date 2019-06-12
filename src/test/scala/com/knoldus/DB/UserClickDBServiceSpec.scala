package com.knoldus.DB

import com.knoldus.common.{User, UserClick}
import org.mockito.Mockito._
import org.mockito.stubbing.OngoingStubbing
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}

class UserClickDBServiceSpec extends WordSpec with ScalaFutures with Matchers with MockitoSugar{

  protected val dbConnection: DBConnection = mock[DBConnection]
  protected val dbLayer: UserClickDBLayer = mock[UserClickDBLayer]
  val userService = new UserClickDBService(dbConnection,dbLayer)
  val user:List[User] = List(User("2",true,"sss","ddd"))
  val userClick = UserClick(user.head.userId, 1)

  "UserService#addUser" should {
    "add user successfully" in {

      when(user.foreach(data=> dbLayer.getCount(data.userId))).asInstanceOf[OngoingStubbing[Long]].thenReturn(1)
      when(
        user.foreach(data=> dbLayer.list(data.userId))
      ).asInstanceOf[OngoingStubbing[Option[UserClick]]].thenReturn(Some(userClick))

      when(
        user.foreach(data=> dbLayer.updated(data.userId,userClick.numberOfCLick))
      ).asInstanceOf[OngoingStubbing[Long]].thenReturn(1)
      whenReady(userService.addManyUsers(user))( _ shouldBe List(1))
    }
  }
}
