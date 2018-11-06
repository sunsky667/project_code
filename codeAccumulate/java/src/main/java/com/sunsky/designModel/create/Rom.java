package com.sunsky.designModel.create;

public class Rom {
    private String door;
    private String window;
    private String floor;
    private String wall;

    public Rom(String door, String window, String floor, String wall) {
        this.door = door;
        this.window = window;
        this.floor = floor;
        this.wall = wall;
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getWall() {
        return wall;
    }

    public void setWall(String wall) {
        this.wall = wall;
    }

    @Override
    public String toString() {
        return "Rom{" +
                "door='" + door + '\'' +
                ", window='" + window + '\'' +
                ", floor='" + floor + '\'' +
                ", wall='" + wall + '\'' +
                '}';
    }
}
