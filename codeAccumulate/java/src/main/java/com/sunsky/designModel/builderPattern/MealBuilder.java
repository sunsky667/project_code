package com.sunsky.designModel.builderPattern;

import com.sunsky.designModel.builderPattern.item.itemimpl.burger.ChickenBurger;
import com.sunsky.designModel.builderPattern.item.itemimpl.burger.VegBurger;
import com.sunsky.designModel.builderPattern.item.itemimpl.drink.Coke;
import com.sunsky.designModel.builderPattern.item.itemimpl.drink.Pepsi;

/**
 * 一餐的建造者，
 * 类似服务员，规定什么就组合什么
 */
public class MealBuilder {

    /**
     * 组合蔬菜汉堡和可口可乐
     * @return
     */
    public Meal prepareVegMeal(){
        Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Coke());
        return meal;
    }

    /**
     * 组合鸡肉汉堡和百事可乐
     * @return
     */
    public Meal prepareNonVegMeal(){
        Meal meal = new Meal();
        meal.addItem(new ChickenBurger());
        meal.addItem(new Pepsi());
        return meal;
    }
}
