package com.sunsky

object SeqDemo {
  def main(args: Array[String]): Unit = {
    sortASC("e","f","b","c","a","d")
    sortByLen("a","bcd","bc","abcd","abcdef")
    opt("a","bcd","bc","abcd","abcdef")
  }

  /*****************按ASCII排序 start***********************/
  def sortASC(first:String,other : String*) = {
    val seq = (Seq(first) ++ other).sortBy(sortASCII(_))
    println(seq)
  }

  def sortASCII(record:String):String = {
    record //按ASCII排序
  }
  /*****************按ASCII排序 end***********************/


  /*****************按长度排序 start***********************/
  def sortByLen(first:String,other : String*) = {
    val seq = (Seq(first) ++ other).sortBy(sortByLength(_)).reverse //反序
    println(seq)
  }

  def sortByLength(record:String):Int = {
    record.length
  }
  /******************按长度排序 end**********************/


  def opt(first:String,other : String*)={
    val seq = (Seq(first) ++ other).filter(_.contains("a")).maxBy(a => a.length)
    println(seq)
  }
}
