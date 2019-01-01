package com.sunsky.designModel.builder;

/**
 * 施工方接口
 * 主要功能是负责建造建筑物
 */
public interface Builder {

    //建造地基
    public void buildBasement();

    //建造墙体
    public void buildWall();

    //建造房顶
    public void buildRoof();

    //返回建筑物
    public Building getBuilding();

}
