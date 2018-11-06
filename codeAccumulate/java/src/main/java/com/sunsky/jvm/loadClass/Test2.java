package com.sunsky.jvm.loadClass;

public class Test2 {
    static {
        System.out.println("静态初始化块执行了");
    }

    public void hello(){
        System.out.println("this is hello method");
    }

    @Override
    public String toString() {
        return "this is test2 to string method";
    }
}
