package com.sunsky.designModel.abstarctFactory.zhiyingdemo.human;

import com.sunsky.designModel.abstarctFactory.zhiyingdemo.Unit;

public class Tank extends Unit {

    public Tank( int x, int y) {
        super(25, 100, 150, x, y);
    }

    @Override
    public void show() {
        System.out.println("坦克出现在坐标：[" + x + "," + y + "]");
    }

    @Override
    public void attack() {
        System.out.println("坦克用炮轰击，攻击力：" + attack);
    }
}
