package com.sunsky.designModel.abstarctFactory.zhiyingdemo.human;

import com.sunsky.designModel.abstarctFactory.zhiyingdemo.Unit;

public class Battleship extends Unit {

    public Battleship(int x, int y) {
        super(25, 200, 500, x, y);
    }

    @Override
    public void show() {
        System.out.println("战舰出现在坐标：[" + x + "," + y + "]");
    }

    @Override
    public void attack() {
        System.out.println("战舰用激光炮打击，攻击力：" + attack);
    }
}
