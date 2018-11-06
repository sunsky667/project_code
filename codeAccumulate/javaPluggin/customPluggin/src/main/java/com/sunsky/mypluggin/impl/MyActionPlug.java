package com.sunsky.mypluggin.impl;

import com.sunsky.pluggin.ActionInterface;

public class MyActionPlug implements ActionInterface {
    public void process() {
        System.out.println("MyActionPlug print");
    }
}
