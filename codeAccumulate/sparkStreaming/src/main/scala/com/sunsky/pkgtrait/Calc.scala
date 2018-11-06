package com.sunsky.pkgtrait
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream


class Calc extends Calculate{

  override def getStreamingContext(): StreamingContext = super.getStreamingContext()

  override def input(ssc: StreamingContext): DStream[String] = super.input(ssc)

  override def start(ssc: StreamingContext): Unit = super.start(ssc)
}

object Calc{

  def apply : Calc
  = new Calc()

}
