package com.sunsky.designModel.proxy.staticproxy;

public class PorxyObject implements Itraget{

    private Itraget itraget;

    public PorxyObject(){
        this.itraget = new TargetObject();
    }

    public void say() {
        itraget.say();
    }
}
