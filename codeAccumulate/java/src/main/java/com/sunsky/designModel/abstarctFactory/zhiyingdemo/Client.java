package com.sunsky.designModel.abstarctFactory.zhiyingdemo;

/**
 * 这和我们之前讲过的工厂方法模式十分类似，
 * 不同之处在于抽象工厂定义了更多的抽象行为，
 * 也就是多个工厂方法于抽象工厂中，
 * 其实它就是工厂方法的变种而已
 * this is abstract factory
 * to the factory designed
 * @see com.sunsky.designModel.factory.Client
 * @url https://www.javazhiyin.com/23516.html
 *
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("游戏开始。。。");
        System.out.println("双方挖矿攒钱。。。");

        //第一位玩家选择了地球人族
        System.out.println("工人建造人族工厂。。。");
        AbstractFactory factory = new HumanFactory(10, 10);

        Unit marine = factory.createLowClass();
        marine.show();

        Unit tank = factory.createMidClass();
        tank.show();

        Unit ship = factory.createHighClass();
        ship.show();

        //另一位玩家选择了外星族
        System.out.println("工蜂建造外星虫族工厂。。。");
        factory = new AlienFactory(200, 200);

        Unit roach = factory.createLowClass();
        roach.show();

        Unit spitter = factory.createMidClass();
        spitter.show();

        Unit mammoth = factory.createHighClass();
        mammoth.show();

        System.out.println("两族开始大混战。。。");
        marine.attack();
        roach.attack();
        spitter.attack();
        tank.attack();
        mammoth.attack();
        ship.attack();

        /*
            游戏开始。。。
            双方挖矿攒钱。。。
            工人建造人族工厂。。。
            制造海军陆战队员成功。
            士兵出现在坐标：[10,10]
            制造变形坦克成功。
            坦克出现在坐标：[10,10]
            制造巨型战舰成功。
            战舰出现在坐标：[10,10]
            工蜂建造外星虫族工厂。。。
            制造蟑螂兵成功。
            蟑螂兵出现在坐标：[200,200]
            制造毒液兵成功。
            口水兵出现在坐标：[200,200]
            制造猛犸巨兽成功。
            猛犸巨兽兵出现在坐标：[200,200]
            两族开始大混战。。。
            士兵用机关枪射击，攻击力：6
            蟑螂兵用爪子挠，攻击力：5
            口水兵用毒液喷射，攻击力：10
            坦克用炮轰击，攻击力：25
            猛犸巨兽用獠牙顶，攻击力：20
            战舰用激光炮打击，攻击力：25
         */
    }
}
