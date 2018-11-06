package com.sunsky.designModel.builderPattern;

public class BuilderPatternDemo {
    public static void main(String[] args) {
        MealBuilder mealBuilder = new MealBuilder();
        Meal vegMeal = mealBuilder.prepareVegMeal();

        vegMeal.showItems();

        System.out.println(vegMeal.getCost());


        Meal novegMeal = mealBuilder.prepareNonVegMeal();
        novegMeal.showItems();
        System.out.println(novegMeal.getCost());
    }
}
