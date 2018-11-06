package com.sunsky.designModel.create.desinger;

import com.sunsky.designModel.create.builder.IBuilder;

public class Desinger {

    private IBuilder iBuilder;

    public Desinger(IBuilder iBuilder){
        this.iBuilder = iBuilder;
    }

    public void order2Design(){
        iBuilder.makeDoor();
        iBuilder.makeFloor();
        iBuilder.makeWall();
        iBuilder.makeWindow();
    }
}
