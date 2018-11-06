package com.sunsky

class StudentStruct(name:String, age : Int) {

  val n:String = name
  val a:Int = age

  def this(name:String){
   this(name,20)
  }

  val sx = println("=============")

  def p(): Unit ={

  }

  println("##############")

}

object StudentStruct{
  def main(args: Array[String]): Unit = {
    val s = new StudentStruct("a",10)
    val s1 = new StudentStruct("b")
  }
}
