package com.sunsky.designModel.adapter.power;

public class TV implements DoublePin {
    @Override
    public void electrify(int l, int n) {
        System.out.println("火线通电："+l);
        System.out.println("零线通电："+n);
    }
}
