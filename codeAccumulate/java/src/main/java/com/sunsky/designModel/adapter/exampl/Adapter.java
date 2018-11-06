package com.sunsky.designModel.adapter.exampl;

public class Adapter implements IWomen{

    private IMen iMen;

    public Adapter(IMen iMen){
        super();
        this.iMen = iMen;
    }

    public void womenRun() {
        iMen.run();
    }

    public void womenSleep() {
        iMen.sleep();
    }

    public void createBaby() {
        iMen.groupBaby();
    }
}
