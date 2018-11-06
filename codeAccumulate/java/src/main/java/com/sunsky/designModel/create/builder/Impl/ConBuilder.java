package com.sunsky.designModel.create.builder.Impl;

import com.sunsky.designModel.create.Rom;
import com.sunsky.designModel.create.builder.IBuilder;

public class ConBuilder implements IBuilder {

    private String door;
    private String window;
    private String floor;
    private String wall;

    public void makeDoor() {
        System.out.println("make door");
        this.door = "door is ok";
    }

    public void makeWindow() {
        System.out.println("make window");
        this.window = "window is ok";
    }

    public void makeFloor() {
        System.out.println("make floor");
        this.floor = "floor is ok";
    }

    public void makeWall() {
        System.out.println("make wall");
        this.wall = "wall is ok";
    }

    public Rom getRom2Client() {
        if(door != null && window != null && floor != null && wall != null){
            return new Rom(door,window,floor,wall);
        }
        return null;
    }
}
