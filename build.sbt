name := "KafkaAkkaAssignment"

version := "0.1"

scalaVersion := "2.12.8"

lazy val akkaVersion = "2.5.11"

lazy val root = (project in file("."))
  .settings(
    name := "root",
    libraryDependencies ++= Seq("org.apache.kafka" %% "kafka" % "2.0.0",
      "com.fasterxml.jackson.core" % "jackson-core" % "2.9.8",
      "com.fasterxml.jackson.core" % "jackson-annotations" % "2.9.8",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.8",
      "com.typesafe.play" %% "play-json" % "2.7.1",
      "de.heikoseeberger" %% "akka-http-play-json"   % "1.20.0",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.8",
      "net.manub" %% "scalatest-embedded-kafka" % "0.14.0" % "test",
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
      "org.scalatest" %% "scalatest" % "3.0.5" % "test",
      "mysql" % "mysql-connector-java" % "5.1.12",
      "org.scalikejdbc" %% "scalikejdbc" % "3.3.2",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
      "ch.qos.logback" % "logback-classic" % "1.1.2",
      "org.slf4j" % "slf4j-api" % "1.7.10",
      "com.typesafe" % "config" % "1.3.3"
    )
  )