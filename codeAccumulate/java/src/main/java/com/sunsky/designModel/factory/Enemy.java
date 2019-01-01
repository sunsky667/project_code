package com.sunsky.designModel.factory;

public abstract class Enemy {

    protected int x;
    protected int y;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void show();
}
