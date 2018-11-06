package com.sunsky.pkgtrait

object App {

  def main(args: Array[String]): Unit = {
    val cal = Calc.apply
    val ssc = cal.getStreamingContext()
    val line = cal.input(ssc)

    cal.start(ssc)
  }

}
