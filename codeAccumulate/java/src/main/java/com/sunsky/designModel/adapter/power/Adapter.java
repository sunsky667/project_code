package com.sunsky.designModel.adapter.power;

public class Adapter implements TriplePin {

    private DoublePin doublePinDevice;

    //创建适配器地时候，需要把双插设备接入进来
    public Adapter(DoublePin doublePinDevice){
        this.doublePinDevice = doublePinDevice;
    }

    //适配器实现的是目标接口
    @Override
    public void electrify(int l, int n, int e) {
        doublePinDevice.electrify(l,n);
    }
}
