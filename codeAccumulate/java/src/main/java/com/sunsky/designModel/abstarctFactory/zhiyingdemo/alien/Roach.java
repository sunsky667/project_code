package com.sunsky.designModel.abstarctFactory.zhiyingdemo.alien;

import com.sunsky.designModel.abstarctFactory.zhiyingdemo.Unit;

public class Roach extends Unit {

    public Roach(int x, int y) {
        super(5, 2, 35, x, y);
    }
    @Override

    public void show() {
        System.out.println("蟑螂兵出现在坐标：[" + x + "," + y + "]");
    }

    @Override
    public void attack() {
        System.out.println("蟑螂兵用爪子挠，攻击力：" + attack);
    }

}
