package com.sunsky.designModel.proxy.cglibProxy;

public class Main {
    public static void main(String[] args) {
        Target target = CglibProxy.newProxyInstance(TargetImpl.class);
        int rst = target.test(1);
        System.out.println(rst);
    }
}
