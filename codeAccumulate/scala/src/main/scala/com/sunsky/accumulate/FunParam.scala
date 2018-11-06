package com.sunsky.accumulate

object FunParam {
  def main(args: Array[String]): Unit = {
    delayed(time())
    println("==============================")
    val d = b(_)
    println(a("666",d))
    println("==============================")

    println("scope"+scope("28")(b("222")))

    println("use scope"+useScope())
  }

  def time():Long = {
    println("get time in nano seconds")
    System.nanoTime()
  }

  def delayed(f : => Long):Long = {
    println("delayed method")
    println("delayed method param : "+f)
    f
  }

  // 高阶函数
  // 函数 f 和 值 v 作为参数，而函数 f 又调用了参数 v
  //see http://www.runoob.com/scala/higher-order-functions.html
  def a(s:String, p : String => Long) : Long = {
    println("in a method")
    println("p is : "+p)
    p(s)
  }

  def b(s : String) : Long = {
    println("b method ")
    s.toLong
  }

  /**
    * 函数柯里化(Currying)
    * 柯里化(Currying)指的是将原来接受两个参数的函数变成新的接受一个参数的函数的过程。
    * 新的函数返回一个以原有第二个参数为参数的函数。
    */
  def scope(age:String)(f : => Long) : Long= {
    f+age.toInt
  }

  def useScope() = scope("22"){
      b("33")
    }


}
