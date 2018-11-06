package com.sunsky.readProperties

import java.io.{BufferedReader, File, FileInputStream, InputStreamReader}

import scala.collection.mutable.ListBuffer

object ReadFile {

  def getFileContent(path:String):List[String] = {
    val listBuffer = ListBuffer[String]()
    var bufferReader:BufferedReader = null
    try {
      val file = new File(path)
      val input = new FileInputStream(file)
      val inputStreamReader = new InputStreamReader(input)
      bufferReader = new BufferedReader(inputStreamReader)
      var data:String = null
      while({data = bufferReader.readLine();data != null}){
        println("=================fuck ========= "+data)
        listBuffer.+=(data)
      }
    }catch {
      case e:Exception => println("read file error "+e.printStackTrace())
    }finally {
      bufferReader.close()
    }
    listBuffer.toList
  }
}
