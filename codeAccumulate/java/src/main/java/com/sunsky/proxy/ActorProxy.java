package com.sunsky.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 现在要生成某一个对象的代理对象，这个代理对象通常也要编写一个类来生成，所以首先要编写用于生成代理对象的类。
 * 在java中如何用程序去生成一个对象的代理对象呢，java在JDK1.5之后提供了一个"java.lang.reflect.Proxy"类，
 * 通过"Proxy"类提供的一个newProxyInstance方法用来创建一个对象的代理对象，如下所示：
    static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
 　　newProxyInstance方法用来返回一个代理对象，这个方法总共有3个参数，
         ClassLoader loader用来指明生成代理对象使用哪个类装载器，
         Class<?>[] interfaces用来指明生成哪个对象的代理对象，通过接口指定，
         InvocationHandler h用来指明产生的这个代理对象要做什么事情。
    所以我们只需要调用newProxyInstance方法就可以得到某一个对象的代理对象了。
 */
//3、创建生成代理对象的代理类
public class ActorProxy {

    //设计一个类变量记住代理类要代理的目标对象
    private Person actor = new Actor();

    /**
     * 设计一个方法生成代理对象
     * @return
     */
    public Person getProxy(){
        //使用Proxy.newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)返回某个对象的代理对象
        return (Person) Proxy.newProxyInstance(Actor.class.getClassLoader(), actor.getClass().getInterfaces(),
                /**
                  * InvocationHandler接口只定义了一个invoke方法，因此对于这样的接口，我们不用单独去定义一个类来实现该接口，
                  * 而是直接使用一个匿名内部类来实现该接口，new InvocationHandler() {}就是针对InvocationHandler接口的匿名实现类
                  */
                /**
                  * 在invoke方法编码指定返回的代理对象干的工作
                  * proxy : 把代理对象自己传递进来
                  * method：把代理对象当前调用的方法传递进来
                  * args:把方法参数传递进来
                  *
                  * 当调用代理对象的person.sing("bingyu");或者 person.dance("江南style");方法时，
                  * 实际上执行的都是invoke方法里面的代码，
                  * 因此我们可以在invoke方法中使用method.getName()就可以知道当前调用的是代理对象的哪个方法
                  */
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        System.out.println("proxy object "+proxy.getClass().toString());

                        //如果调用的是代理对象的sing方法
                        if(method.getName().equals("sing")){
                            System.out.println("this is proxy,before song, give me the money");
                            //已经给钱了，经纪人自己不会唱歌，就只能找刘德华去唱歌！
                            return method.invoke(actor,args); //代理对象调用真实目标对象的sing方法去处理用户请求
                        }

                        //如果调用的是代理对象的dance方法
                        if(method.getName().equals("dance")){
                            System.out.println("this is proxy,before dance, give me the money");
                            //已经给钱了，经纪人自己不会唱歌，就只能找刘德华去跳舞！
                            return method.invoke(actor,args); //代理对象调用真实目标对象的dance方法去处理用户请求
                        }

                        return null;
                    }
        });
    }

}
