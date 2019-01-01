package com.sunsky.designModel.abstarctFactory.zhiyingdemo;

import com.sunsky.designModel.abstarctFactory.zhiyingdemo.alien.Mammoth;
import com.sunsky.designModel.abstarctFactory.zhiyingdemo.alien.Roach;
import com.sunsky.designModel.abstarctFactory.zhiyingdemo.alien.Spitter;

public class AlienFactory implements AbstractFactory {

    private int x;
    private int y;

    public AlienFactory(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Unit createLowClass() {
        Unit unit = new Roach(x, y);
        System.out.println("制造蟑螂兵成功。");
        return unit;
    }

    @Override
    public Unit createMidClass() {
        Unit unit = new Spitter(x, y);
        System.out.println("制造毒液兵成功。");
        return unit;
    }

    @Override
    public Unit createHighClass() {
        Unit unit = new Mammoth(x, y);
        System.out.println("制造猛犸巨兽成功。");
        return unit;
    }

}
