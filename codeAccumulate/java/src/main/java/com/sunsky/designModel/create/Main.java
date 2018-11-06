package com.sunsky.designModel.create;

import com.sunsky.designModel.create.builder.IBuilder;
import com.sunsky.designModel.create.builder.Impl.ConBuilder;
import com.sunsky.designModel.create.desinger.Desinger;

/**
 * 创建者模式：将一个复杂对象的构建和其表示分离开来，使得同样的构建过程可以创建出不同的表示。

 例如我们需要盖一栋楼房，需要工人来砌墙，需要设计师来设计房子怎么盖，而这里的设计师本身是不干活的，
 它只是负责下命令让工人干活就行，而真正的创建者是工人，他们最后把楼房盖起来的。
 所以，最后是向民工要房子而不是向设计师要房子。
 在这里，房主（客户端）只需要请工人（new Builder()）和设计师(new Desinger())，
 并让设计师指挥工人干活(order2design())，最后房主向工人要房子(getRoom2Clinet())。
 */
public class Main {
    public static void main(String[] args){
        IBuilder builder = new ConBuilder();
        Desinger desinger = new Desinger(builder);
        desinger.order2Design();
        Rom rom = builder.getRom2Client();
        System.out.println(rom);
    }
}
