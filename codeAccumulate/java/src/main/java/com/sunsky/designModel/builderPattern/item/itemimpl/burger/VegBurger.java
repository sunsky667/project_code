package com.sunsky.designModel.builderPattern.item.itemimpl.burger;

import com.sunsky.designModel.builderPattern.item.itemimpl.Burger;

public class VegBurger extends Burger {
    public float price() {
        return 25.0f;
    }

    public String name() {
        return "veg burger";
    }
}
