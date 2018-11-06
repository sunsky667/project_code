package com.sunsky.designModel.singleton;

/**
 * 单例模式是设计模式中比较简单的一种模式，也是使用的比较多的一种模式。
 特别是在某些对象只需要一个时，比如线程池、缓存、日志对象、注册表对象等。
 如果创建了多个，可能会导致很多问题。比如程序行为异常、资源使用过量等。

 单例模式确保程序中一个类最多只有一个实例。
 单例模式提供访问这个实例的全局点。
 在JAVA中单例模式需要：私有构造器、一个静态方法、一个静态变量。
 如果使用多个类加载器，可能会导致单例失效而产生多个实例。
 */
public class Main {
    public static void main(String[] args){
        Singleton singleton = Singleton.getInstance();
        singleton.setAge(27);
        singleton.setName("zhangshan");
        System.out.println(singleton);
    }
}
