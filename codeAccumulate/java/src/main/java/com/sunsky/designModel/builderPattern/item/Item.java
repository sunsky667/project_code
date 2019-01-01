package com.sunsky.designModel.builderPattern.item;

import com.sunsky.designModel.builderPattern.packet.Packing;

/**
 * 商品接口
 * 包含商品名，包装以及价格
 */
public interface Item {
    public String name();
    public Packing packing();
    public float price();
}
