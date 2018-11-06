package com.sunsky.javacode.secondSort;

import scala.math.Ordered;

import java.io.Serializable;

/**
 * Created by sunsky on 2017/3/20.
 */
public class SecondaryOrderKey implements Ordered<SecondaryOrderKey>,Serializable {

    private static final long serialVersionUID = 1L;
    private int first;
    private int second;

    public SecondaryOrderKey(int first, int second) {
        super();
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int compare(SecondaryOrderKey that) {
        if (this.first - that.getFirst() != 0){
            return  this.first - that.getFirst();
        }else{
            return  this.second - that.getSecond();
        }
    }

    public boolean $less(SecondaryOrderKey that) {
        if(this.first < that.getFirst()){
            return true;
        }else if(this.first == that.getFirst() && this.second < that.getSecond()){
            return true;
        }
        return false;
    }

    public boolean $greater(SecondaryOrderKey that) {
       if(this.first > that.getFirst()){
           return true;
       }else if(this.first == that.first && this.second > that.getSecond()){
           return true;
       }
       return false;
    }

    public boolean $less$eq(SecondaryOrderKey that) {
        if(this.$less(that)){
            return true;
        }else if(this.first == that.getFirst() && this.second == that.getSecond()){
            return true;
        }
        return false;
    }

    public boolean $greater$eq(SecondaryOrderKey that) {
        if(this.$greater(that)){
            return true;
        }else if(this.first == that.getFirst() && this.second == that.getSecond()){
            return true;
        }
        return false;
    }

    public int compareTo(SecondaryOrderKey that) {
        if(this.first - that.getFirst() != 0){
            return this.first - that.getFirst();
        }else{
            return this.second - that.getSecond();
        }
    }
}
