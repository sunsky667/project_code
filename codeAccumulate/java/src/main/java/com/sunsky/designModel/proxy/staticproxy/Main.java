package com.sunsky.designModel.proxy.staticproxy;

import com.sunsky.designModel.proxy.staticproxy.Itraget;
import com.sunsky.designModel.proxy.staticproxy.PorxyObject;

/**
 *
 抽象角色：声明共同接口。这样，在任何可以使用目标对象的地方都可以使用代理对象。

 代理角色：代理对象包含对目标对象的引用，在任何时候可操作目标对象；代理对象提供一个与目标对象相同的接口，以便可以在任何时候替代目标对象。

 真实角色：代理对象所代表的目标对象，代理角色所代表的真实对象，其是最终要引用的对象。
 *
 */

public class Main {
    public static void main(String[] args){
        Itraget itraget = new PorxyObject();
        itraget.say();
    }
}
