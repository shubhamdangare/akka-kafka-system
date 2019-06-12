package com.knoldus.akka


import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.knoldus.DB.{DBConnection, UserClickDBService}
import com.knoldus.common.User
import org.mockito.Mockito.when
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ActorSystemSpec extends TestKit(ActorSystem("MySpec"))
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll
  with ScalaFutures
  with MockitoSugar {


  override def afterAll(): Unit = {
    super.afterAll()
    TestKit.shutdownActorSystem(system, verifySystemShutdown = true)
  }

  def future[A](a: A): Future[A] = Future.successful(a)

  protected val dbConnection: DBConnection = mock[DBConnection]
  protected val dbLayer: UserClickDBService = mock[UserClickDBService]

  "An Echo actor" must {

    "send back messages unchanged" in {

      val user: List[User] = List(User("2", true, "sss", "ddd"))


      val tester1 = TestProbe()
      when(
        dbLayer.addManyUsers(user)
      ).thenReturn(future(List(1)))

      println("----------------------------------------")

      whenReady {
        val data = dbLayer.addManyUsers(user)
      println("`````````````````")
        tester1.expectMsg(dbLayer.addManyUsers(user))
        println("@@@@@@@@@@@")
        tester1.reply(List(1))
        println(data)
        data
      }(abc => {
        println(abc); abc shouldBe List(1)
      })


      /** when(
        *dbLayer.addManyUsers(user)
        * ).thenReturn(future(List(1)))
        * *
        *
        * val prop = UserClicked.props(user, dbConnection, dbLayer)
        * *
        * val echo = system.actorOf(prop)
        * val probe = TestProbe()
        *probe.send(echo,dbLayer.addManyUsers(user) )
        * try expectMsg("hello")
        * *
        * echo ! dbLayer.addManyUsers(user)
        * within(800.milliseconds) {
        * tester1.expectMsg("abc")
        * }
        */
    }

  }

}
