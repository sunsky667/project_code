package com.sunsky.designModel.builder;

import com.sunsky.designModel.builder.builderimpl.ApartmentBuilder;
import com.sunsky.designModel.builder.builderimpl.HouseBuilder;

/**
 * @see
 * @url https://www.javazhiyin.com/24482.html
 * 用于对复杂对象的构造、初始化，与工厂模式不同的是，
 * 建造者的目的在于把复杂构造过程从不同对象展现中抽离出来，
 * 使得同样的构造工序可以展现出不同的产品对象。
 * 建造者模式：
 *      1.得到的结果是建筑物(Building)，Building的成员变量是一个List
 *          一个Building由地基，一层楼，房顶组成，或者由地基，多层楼，房顶组成
 *      2.Builder是建造者的接口，规定的建造者方法
 *      3.HouseBuilder，ApartmentBuilder是Builder的实现类，
 *          成员变量是Building，由一系列操作建成Building
 *      4.Director是施工方，成员变量是Builder，注入不同的Builder，得到不同的Building
 *
 * 建造者模式与策略模式的区别：
 *      https://www.cnblogs.com/jjhe369/archive/2011/07/03/2096881.html
 * @see com.sunsky.designModel.stragedy.Client
 * 策略模式与创建者模式不同的是创建者类还有一个产品类。
 *
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
        //招工，建别墅。
        Builder builder = new HouseBuilder();
        //交给工程总监
        Director director = new Director(builder);
        System.out.println(director.direct());
        //替换施工方，建公寓。
        director.setBuilder(new ApartmentBuilder());
        System.out.println(director.direct());
    }

}
