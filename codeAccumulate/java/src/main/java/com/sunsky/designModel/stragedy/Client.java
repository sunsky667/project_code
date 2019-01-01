package com.sunsky.designModel.stragedy;

import com.sunsky.designModel.stragedy.impl.Addition;
import com.sunsky.designModel.stragedy.impl.Subtraction;

/**
 * 策略模式
 * 思路：
 *      1.一个策略接口，规定了计算的方法
 *      2.加法和减法的类，实现策略接口，实现的方法相同，逻辑不通
 *      3.计算器类，成员变量是策略类型，先初始化需要的策略，然后再调用策略的方法
 *      4.使用时，同一个计算器，通过注入不同的策略来实现不同的功能
 * @url https://mp.weixin.qq.com/s/O-lFPu07s9WGQL7bKM63qA?scene=25#wechat_redirect
 * 建造者模式与策略模式的区别：
 *      https://www.cnblogs.com/jjhe369/archive/2011/07/03/2096881.html
 * @see com.sunsky.designModel.builder.Client
 * 策略模式与创建者模式不同的是创建者类还有一个产品类。
 * 策略模式与创建者模式从功能上来说两者相差很远：
 *
 * （1）建造者模式是创建型的，也就是说用来创建对象的，而策略模式属于行为型模式，
 * 通过将行为封装成对象来降低类之间的耦合度；
 * （2）策略模式的抽象类仅仅定义了一个算法接口，而建造者模式的抽象类则已经定义好了算法骨架或者过程的步骤，
 * 也就是说策略模式的各具体策略在实现上可以差之千里，
 * 但是建造者模式的具体建造者必须按照接口中定义好的骨架或步骤去实现；
 * （3）策略模式的StrategyContext类通过提供一个上下文环境来维护具体策略；
 * 而建造者模式的Director类则是封装了Product类的创建细节，
 * 便于客户端程序调用。
 */
public class Client {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();//实例化计算器
        calculator.setStrategy(new Addition());//接入加法实现
        int result = calculator.getResult(1, 1);//计算！
        System.out.println(result);//得到的是加法结果2

        calculator.setStrategy(new Subtraction());//再次接入减法实现
        result = calculator.getResult(1, 1);//计算！
        System.out.println(result);//得到的是减法结果0
    }

}
