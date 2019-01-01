package com.sunsky.designModel.builderPattern;

import com.sunsky.designModel.builderPattern.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * 一餐的组成
 * 由多个item组成
 * 包含多个商品
 */
public class Meal{

    //多个商品
    private List<Item> items = new ArrayList<Item>();

    /**
     * 添加商品
     * @param item
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * 获取所有商品的总价格
     * @return
     */
    public float getCost(){
        float cost = 0.0f;
        for (Item item:items){
            cost += item.price();
        }
        return cost;
    }

    //打印商品，包装及价格
    public void showItems(){
        for (Item item : items){
            System.out.print("Item : "+item.name());
            System.out.print(", Packing : "+item.packing().pack());
            System.out.println(", Price : "+item.price());
        }
    }
}
