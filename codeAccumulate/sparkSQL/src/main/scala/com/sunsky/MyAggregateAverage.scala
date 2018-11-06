package com.sunsky

import com.sunsky.entity.{Average, Person}
import org.apache.spark.sql.{Encoder, Encoders}
import org.apache.spark.sql.expressions.Aggregator

object MyAggregateAverage extends Aggregator[Person,Average,Double]{
  // A zero value for this aggregation. Should satisfy the property that any b + zero = b
  override def zero: Average = Average(0L,0L)
  // Combine two values to produce a new value. For performance, the function may modify `buffer`
  // and return it instead of constructing a new object
  override def reduce(b: Average, a: Person): Average = {
    b.sum += a.age.toLong
    b.count += 1
    b
  }
  // Merge two intermediate values
  override def merge(b1: Average, b2: Average): Average = {
    b1.sum = b1.sum + b2.sum
    b1.count = b1.count + b2.count
    println(b1+"====="+b2)
    b1
  }
  // Transform the output of the reduction
  override def finish(reduction: Average): Double = {
    reduction.sum.toDouble/reduction.count.toDouble
  }
  // Specifies the Encoder for the intermediate value type
  override def bufferEncoder: Encoder[Average] = Encoders.product
  // Specifies the Encoder for the final output value type
  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}
