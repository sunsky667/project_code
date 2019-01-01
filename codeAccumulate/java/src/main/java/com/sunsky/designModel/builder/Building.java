package com.sunsky.designModel.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 最后的建筑物的实体
 * 由地基，墙体，房顶组成
 */
public class Building {

    //建筑物的组成结构
    private List<String> buildingComponents = new ArrayList<>();

    //地基
    public void setBasement(String basement) {// 地基
        this.buildingComponents.add(basement);
    }

    //墙体
    public void setWall(String wall) {// 墙体
        this.buildingComponents.add(wall);
    }

    //屋顶
    public void setRoof(String roof) {// 房顶
        this.buildingComponents.add(roof);
    }

    //重写toString，类似建筑物整体给人的外观
    @Override
    public String toString() {
        String buildingStr = "";
        for (int i = buildingComponents.size() - 1; i >= 0; i--) {
            buildingStr += buildingComponents.get(i);
        }
        return buildingStr;
    }

}
