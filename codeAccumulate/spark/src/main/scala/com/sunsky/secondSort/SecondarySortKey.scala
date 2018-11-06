package com.sunsky.secondSort

/**
  * Created by sunsky on 2017/3/20.
  */
class SecondarySortKey(val first : Int,val second : Int) extends Ordered[SecondarySortKey] with Serializable{
  override def compare(that: SecondarySortKey):Int = {
    if(this.first - that.first != 0){
      this.first - that.first
    }else{
      this.second - that.second
    }
  }
}
