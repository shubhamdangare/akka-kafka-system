package com.knoldus.akka
import akka.actor.{ActorRef, ActorSystem}
import com.knoldus.DB.DBConnection
import com.knoldus.akka.UserClicked.UserData
import com.knoldus.akka.UserDetails.UserDetailData
import com.knoldus.common.User
import com.knoldus.kafka.{Consumer, Producer}
import com.typesafe.config.{Config, ConfigFactory}

object ActorSystems extends App {

  val conf: Config = ConfigFactory.load()

  val config: Config = conf.getConfig("Kafka")

  val dbConnection = new DBConnection()

  val producer: Unit = new Producer(config).writeToKafka(config.getString("TOPIC"))

  val consumer: List[User] = new Consumer(config).consumeFromKafka(config.getString("TOPIC"))

  val system: ActorSystem = ActorSystem("Akka-kafka")

  val userClickedActor: ActorRef = system.actorOf(UserClicked.props(consumer,dbConnection), "userClickedActor")

  val userDetailActor: ActorRef = system.actorOf(UserDetails.props(consumer,dbConnection), "userDetailActor")

  userClickedActor ! UserData(consumer)

  userDetailActor ! UserDetailData(consumer)

}
