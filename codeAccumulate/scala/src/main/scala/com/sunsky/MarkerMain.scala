package com.sunsky

object MarkerMain {
  def main(args: Array[String]): Unit = {
    println("Primary colors are : " + Marker.primaryColors)
    println(Marker("blue"))
    println(Marker("red"))
    println("Primary colors are : " + Marker.primaryColors)
    println("Primary colors are : " + Marker.primaryColors)
  }
}
