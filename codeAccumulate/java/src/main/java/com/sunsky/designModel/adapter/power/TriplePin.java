package com.sunsky.designModel.adapter.power;

/**
 * 三相插头
 */
public interface TriplePin {
    //参数分别为火线live，零线null，地线earth
    public void electrify(int l,int n,int e);
}
