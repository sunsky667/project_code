package com.sunsky.designModel.stragedy.impl;

import com.sunsky.designModel.stragedy.Strategy;

/**
 * 减法
 */
public class Subtraction implements Strategy {

    @Override
    public int calculate(int a, int b) {
        return a-b;
    }
}
