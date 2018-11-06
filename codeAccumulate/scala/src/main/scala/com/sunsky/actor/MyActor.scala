package com.sunsky.actor

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging

/**
  * Created by sunsky on 2017/6/23.
  */
class MyActor extends Actor{
  val log = Logging(context.system, this)
  def receive = {
    case "test" => log.info("received test")
    case _      => log.info("received unknown message")
  }
}


object sb {
  def main(args: Array[String]): Unit = {

   val system = ActorSystem("Main")

    val myActor = system.actorOf(Props[MyActor])

    myActor ! "test"
  }
}