package com.sunsky.designModel.proxy.jdkProxyActorDemo;


/**
 * 所以在这里明确代理对象的两个概念：
　　　　1、代理对象存在的价值主要用于拦截对真实业务对象的访问。
　　　　2、代理对象应该具有和目标对象(真实业务对象)相同的方法。
             刘德华(真实业务对象)会唱歌，会跳舞，会拍戏，我们现在不能直接找他唱歌，跳舞，拍戏了，
             只能找他的代理人(代理对象)唱歌，跳舞，拍戏，一个人要想成为刘德华的代理人，
             那么他必须具有和刘德华一样的行为(会唱歌，会跳舞，会拍戏)，刘德华有什么方法，
             他(代理人)就要有什么方法，我们找刘德华的代理人唱歌，跳舞，拍戏，
             但是代理人不是真的懂得唱歌，跳舞，拍戏的，真正懂得唱歌，跳舞，拍戏的是刘德华，
             在现实中的例子就是我们要找刘德华唱歌，跳舞，拍戏，那么只能先找他的经纪人，
             交钱给他的经纪人，然后经纪人再让刘德华去唱歌，跳舞，拍戏。
 */
//2、定义目标业务对象类
public class Actor implements Person {
    public String sing(String name) {
        System.out.println("actor sing "+name + "song");
        return "sing finished , thanks";
    }

    public String dance(String name) {
        System.out.println("actor dance "+name+"dance");
        return "dance finished , thanks";
    }
}
