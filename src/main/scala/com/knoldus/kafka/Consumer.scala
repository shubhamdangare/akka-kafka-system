package com.knoldus.kafka

import java.util
import java.util.Properties
import scala.collection.JavaConverters._
import com.knoldus.common.User
import com.typesafe.config.Config
import org.apache.kafka.clients.consumer.KafkaConsumer

class Consumer(config:Config){

  def consumeFromKafka(topic: String) = {
    val props = new Properties()
    props.put("bootstrap.servers", config.getString("BOOTSTRAP_SERVER"))
    props.put("key.deserializer", config.getString("KEY_DESERIALIZER"))
    props.put("value.deserializer", config.getString("VALUE_DESERIALIZER"))
    props.put("auto.offset.reset", config.getString("OFFSET"))
    props.put("group.id", config.getString("GROUP_ID"))
    val consumer: KafkaConsumer[String, User] = new KafkaConsumer[String, User](props)
    consumer.subscribe(util.Arrays.asList(topic))
    println("inside subscriber")
    val record = consumer.poll(100).asScala.toList.map(_.value())
    record

  }
}