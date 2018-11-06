package com.sunsky.option

object OptionCode {
  def main(args: Array[String]): Unit = {
    val seq = Seq("a","b","c","d","e","f","g")
    seq.foreach(record => println("====seq element====> "+record))


    val option = Option("a","b","c","d")
    val value = option.getOrElse("0")
    println("option value ===> "+value)

    option.foreach(
      record => {
        println(record)
      }
    )
  }
}
