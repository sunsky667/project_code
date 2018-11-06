package com.sunsky

import scala.io.StdIn

object StdInDemo {
  def main(args: Array[String]): Unit = {
    var line = ""

    while ({line = StdIn.readLine();line != null}){
      println(line)
    }

  }
}
