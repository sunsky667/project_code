package com.sunsky.designModel.stragedy.impl;

import com.sunsky.designModel.stragedy.Strategy;

/**
 * 加法
 */
public class Addition implements Strategy {
    @Override
    public int calculate(int a, int b) {
        return a+b;
    }
}
