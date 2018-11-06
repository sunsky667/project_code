package com.sunsky.designModel.adapter.exampl;

public class Women implements IWomen{
    public void womenRun() {
        System.out.println("women run slow");
    }

    public void womenSleep() {
        System.out.println("women need sleep");
    }

    public void createBaby() {
        System.out.println("women can create baby");
    }
}
