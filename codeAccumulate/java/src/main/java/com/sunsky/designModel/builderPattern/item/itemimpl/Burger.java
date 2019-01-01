package com.sunsky.designModel.builderPattern.item.itemimpl;

import com.sunsky.designModel.builderPattern.item.Item;
import com.sunsky.designModel.builderPattern.packet.Packing;
import com.sunsky.designModel.builderPattern.packet.packetimpl.Wrapper;

abstract public class Burger implements Item {
    public Packing packing() {
        return new Wrapper();
    }

    public abstract float price();
}
