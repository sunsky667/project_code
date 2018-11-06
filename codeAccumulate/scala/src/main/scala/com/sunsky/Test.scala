package com.sunsky

object Test {

  def main(args: Array[String]): Unit = {
//    var tup:(String,String,String)= null
//    tup + "a"+"b"+"c"
//    println(tup)
//    tup = ("a","b","c")
//    println(tup)
//
//    println("1525780618288".toLong)
//
//    def a():Unit = {
//      println("=========")
//    }
//
//    val s = a();
//    println(s)
//
//    val w = () => Unit
//    println(w)


    var a = 0;
    var b = 0;
    // for 循环
    for(a <- 1 to 3 ; b <- 1 to 3){
      println( "Value of a: " + a );
      println( "Value of b: " + b );
    }

    val map = Map(1->"a",2->"b")
    println(map.get(1).get)
  }
}
