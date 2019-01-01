package com.sunsky.designModel.abstarctFactory.zhiyingdemo.alien;

import com.sunsky.designModel.abstarctFactory.zhiyingdemo.Unit;

public class Spitter extends Unit {

    public Spitter(int x, int y) {
        super(10, 8, 80, x, y);
    }

    @Override
    public void show() {
        System.out.println("口水兵出现在坐标：[" + x + "," + y + "]");
    }

    @Override
    public void attack() {
        System.out.println("口水兵用毒液喷射，攻击力：" + attack);
    }

}
