package com.sunsky.jvm.methodAreaOut;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Described：方法区溢出测试
 * 使用技术 CBlib
 * @VM args : -XX:PermSize=10M -XX:MaxPermSize=10M
 *
 * jdk1.8 is Metaspace
 * @VM args :  -XX:MetaspaceSize=5M -XX:MaxMetaspaceSize=5M
 *
 */
public class MethodAreaOutOfMemory {
    public static void main(String[] args) {
        while (true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(TestCase.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invoke(o,objects);
                }
            });
            enhancer.create();
        }
    }
}

class TestCase{

}