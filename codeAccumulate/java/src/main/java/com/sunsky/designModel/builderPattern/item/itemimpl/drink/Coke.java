package com.sunsky.designModel.builderPattern.item.itemimpl.drink;

import com.sunsky.designModel.builderPattern.item.itemimpl.ColdDrink;

public class Coke extends ColdDrink {
    public float price() {
        return 10.0f;
    }

    public String name() {
        return "coke";
    }
}
