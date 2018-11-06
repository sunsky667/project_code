package com.sunsky.designModel.proxy.cglibProxy;

public class TargetImpl implements Target {
    @Override
    public int test(int i) {
        return i+1;
    }
}
