package com.sunsky.designModel.builderPattern.item.itemimpl.burger;

import com.sunsky.designModel.builderPattern.item.itemimpl.Burger;

public class ChickenBurger extends Burger {
    public float price() {
        return 50.5f;
    }

    public String name() {
        return "chicken burger";
    }
}
