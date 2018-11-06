package com.sunsky.designModel.adapter.power;

public class Main {
    public static void main(String[] args) {
        DoublePin doublePinDevice = new TV();
        Adapter adapter = new Adapter(doublePinDevice);
        adapter.electrify(220,0,-1);

        System.out.println("====================================");

        //另一种更简单的方式叫做“类适配器”
        ClassAdapter classAdapter = new ClassAdapter();
        classAdapter.electrify(220,0,-1);
    }
}
