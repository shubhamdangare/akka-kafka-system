package com.knoldus.kafka

import java.time.Instant
import java.util.Properties
import com.knoldus.common.User
import com.typesafe.config.Config
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

class Producer(config: Config){

  def writeToKafka(topic: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", config.getString("BOOTSTRAP_SERVER"))
    props.put("key.serializer", config.getString("KEY_SERIALIZER"))
    props.put("value.serializer", config.getString("VALUE_SERIALIZER"))
    val producer = new KafkaProducer[String, User](props)
    val userObject = User("4",true ,"login", Instant.now.toString)
    val record = new ProducerRecord[String, User](topic, "User2", userObject)
    producer.send(record)
    producer.close()
  }
}


