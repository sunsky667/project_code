package com.sunsky.designModel.builderPattern;

public class BuilderPatternDemo {
    public static void main(String[] args) {
        //建造者
        MealBuilder mealBuilder = new MealBuilder();
        //点蔬菜汉堡套餐
        Meal vegMeal = mealBuilder.prepareVegMeal();

        vegMeal.showItems();

        System.out.println(vegMeal.getCost());

        //点肉汉堡套餐
        Meal novegMeal = mealBuilder.prepareNonVegMeal();
        novegMeal.showItems();
        System.out.println(novegMeal.getCost());
    }
}
