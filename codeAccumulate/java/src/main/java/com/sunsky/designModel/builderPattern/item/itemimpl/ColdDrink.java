package com.sunsky.designModel.builderPattern.item.itemimpl;

import com.sunsky.designModel.builderPattern.packet.packetimpl.Bottle;
import com.sunsky.designModel.builderPattern.item.Item;
import com.sunsky.designModel.builderPattern.packet.Packing;

abstract public class ColdDrink implements Item {
    public Packing packing() {
        return new Bottle();
    }

    public abstract float price();
}
