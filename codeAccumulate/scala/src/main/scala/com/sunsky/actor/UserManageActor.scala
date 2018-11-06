package com.sunsky.actor

import akka.actor.{Actor, ActorSystem, Props}

case class Login(userName:String,password:String)

case class Register(userName:String,password:String)

class UserManageActor extends Actor {

  override def receive: Receive = {
    case Login(userName:String,password:String) => println("login user name : "+userName+" password : "+password)
    case Register(userName:String,password:String) => println("register name : "+userName +" password : "+password)
  }

}

object actor{
  def main(args: Array[String]): Unit = {

    val system = ActorSystem("Main")

    val userManageActor = system.actorOf(Props[UserManageActor])

    userManageActor.tell(Register("fuck", "1234"),userManageActor)

    userManageActor ! Register("leo", "1234")
    userManageActor ! Login("leo", "1234")
  }
}

