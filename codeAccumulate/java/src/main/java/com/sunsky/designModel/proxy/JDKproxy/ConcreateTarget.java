package com.sunsky.designModel.proxy.JDKproxy;

public class ConcreateTarget implements ITarget {
    public void update() {
        System.out.println("I am a jdk proxy");
    }
}
