package com.sunsky.designModel.builderPattern.item.itemimpl.drink;

import com.sunsky.designModel.builderPattern.item.itemimpl.ColdDrink;

public class Pepsi extends ColdDrink {
    public float price() {
        return 9.5f;
    }

    public String name() {
        return "pepsi";
    }
}
