package com.sunsky.designModel.abstarctFactory.zhiyingdemo.alien;

import com.sunsky.designModel.abstarctFactory.zhiyingdemo.Unit;

public class Mammoth extends Unit {

    public Mammoth(int x, int y) {
       super(20, 100, 400, x, y);
    }

   @Override
    public void show() {
       System.out.println("猛犸巨兽兵出现在坐标：[" + x + "," + y + "]");
    }

    @Override
    public void attack() {
        System.out.println("猛犸巨兽用獠牙顶，攻击力：" + attack);
    }

}
