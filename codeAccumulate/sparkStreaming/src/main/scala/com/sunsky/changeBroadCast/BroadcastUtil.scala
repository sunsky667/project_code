package com.sunsky.changeBroadCast

import org.apache.spark.SparkContext
import org.apache.spark.broadcast.Broadcast

import scala.reflect.ClassTag


/**
  * Created by sunsky on 2017/7/12.
  * use:
  * 1.new a null object , type is Broadcast[T]
  * 2.use apply to create BroadcastUtil type object : BroadcastUtil.apply(instance)
  * 3.use method getInstance() to get Broadcast[T]
  * 4.use update method update to get updated broadcast
  */

object BroadcastUtil {

  def apply[T:ClassTag](sc:SparkContext,value:T): BroadcastUtil[T] = new BroadcastUtil(sc,value)

}

class BroadcastUtil[T:ClassTag](sc:SparkContext,value:T){

  private var instance = sc.broadcast(value)

  def getInstance(sc:SparkContext,value:T):Broadcast[T] = {

    if(instance == null){
      synchronized(
        if(instance == null){
          instance = sc.broadcast(value)
        }
      )
    }
    instance
  }

  def update(sc: SparkContext,newValue:T,blocking: Boolean = false) = {
    if(instance != null){
      instance.unpersist(blocking)
      instance = sc.broadcast(newValue)
    }
    instance
  }

}
