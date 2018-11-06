package com.sunsky.hook

object App {
  def main(args: Array[String]): Unit = {

    println("==================================")

    val hook = new Thread {
      override def run(): Unit = {
        println("##################hook method#################")
      }
    }

    def dd()  = {
      println("sssssssssssssssss")
    }

    println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
//    Runtime.getRuntime.addShutdownHook(hook)
    scala.sys.addShutdownHook(hook.run())
    scala.sys.addShutdownHook(dd)
    Thread.sleep(5000)
    println("finished")
    Thread.sleep(5000)

  }
}
