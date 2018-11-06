package com.sunsky.reflect.myproxy;

public class ServiceImpl implements Service{

    @Override
    public void test(String a, String b) {
        System.out.println(a+" ======= "+b);
        System.out.println("a"+"$$$$$$"+a);
    }
}
