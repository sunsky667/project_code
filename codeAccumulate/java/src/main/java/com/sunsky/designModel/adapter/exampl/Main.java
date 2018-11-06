package com.sunsky.designModel.adapter.exampl;

public class Main {
    public static void main(String[] args){
        IMen men = new Men();
        IWomen women = new Women();

        IWomen aWomen = new Adapter(men); //将男人包装到一个女人适配器，让他看起来像女人

        aWomen.womenRun();
        aWomen.womenSleep();
        aWomen.createBaby();


        System.out.println("-----------------");
        women.womenRun();
        women.womenSleep();
        women.createBaby();
    }
}
