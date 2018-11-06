package com.sunsky.designModel.adapter.power;

/**
 * 两相插头
 */
public interface DoublePin {
    //参数分别为火线live，零线null，没有地线earth
    public void electrify(int l ,int n);
}
