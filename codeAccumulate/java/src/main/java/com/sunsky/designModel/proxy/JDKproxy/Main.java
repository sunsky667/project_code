package com.sunsky.designModel.proxy.JDKproxy;

/**
 * java RMI（远程接口调用）中的stub对象就是代理对象，客户必须取得了stub对象才能给你调用其中的方法（具体情况不在此讲解感兴趣可以看看源码）。java.lang.reflect.Proxy也使用了代理模式，可以去看看源码学习学习。

 动态代理的优点：动态代理类比较简洁，避免了创建多个不同静态代理的麻烦和重复多余的代码。调用目标代码时，在方法“运行时”动态的加入，更加灵活。

 动态代理的缺点：系统变得灵活了，但是效率有所降低，其比静态代理慢一点。代码的可读性不好，不太容易理解。只能对实现了接口的类进行代理。
 */
public class Main {
    public static void main(String[] args){
        PorxyHandle porxyHandle = new PorxyHandle();
        ITarget iTarget = (ITarget) porxyHandle.bind(new ConcreateTarget());
        iTarget.update();
    }
}
