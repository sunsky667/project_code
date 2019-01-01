package com.sunsky.designModel.abstarctFactory.zhiyingdemo.human;

import com.sunsky.designModel.abstarctFactory.zhiyingdemo.Unit;

public class Marine extends Unit {

    public Marine(int x, int y) {
        super(6, 5, 40, x, y);
    }

    @Override
    public void show() {
        System.out.println("士兵出现在坐标：[" + x + "," + y + "]");
    }

    @Override
    public void attack() {
        System.out.println("士兵用机关枪射击，攻击力：" + attack);
    }
}
