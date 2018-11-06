package com.sunsky

class Marker private(val color: String){
  println("Creating " + this)

  override def toString(): String = "marker color " + color
}

object Marker {
  private val markers = Map("red" -> new Marker("red"),
    "blue" -> new Marker("blue"),
    "green" -> new Marker("green"))

  def primaryColors = "red, green, blue"

  def apply(color: String) = if (markers.contains(color)) markers(color) else null
}



