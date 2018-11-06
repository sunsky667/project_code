package com.sunsky

object MatchDemo {

  def main(args: Array[String]): Unit = {

    val line = "a"
    val aa = "other"

    println(m(line))
    println(m(aa))

  }

  def m(line :String) : String = {
    line match {
      case "a" => "1"
      case "b" => "2"
      case _ => "other"
    }
  }

}
