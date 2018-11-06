package com.sunsky.designModel.create.builder;

import com.sunsky.designModel.create.Rom;

public interface IBuilder {
    public void makeDoor();
    public void makeWindow();
    public void makeFloor();
    public void makeWall();

    public Rom getRom2Client();
}
