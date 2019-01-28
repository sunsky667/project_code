package com.sunsky.designModel.visitor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 对于数据的封装我们通常会用到POJO类，
 * 它除了getter和setter之外是不包含任何业务逻辑的，
 * 也就是说它只对应一组数据并不包含任何功能。
 * 举个最常见的例子，比如数据库对应的实体类，
 * 一般我们不会在类里封装上业务逻辑，
 * 而是放在专门的Service类里去处理，
 * 也就是Service作为拜访者去访问实体类封装的数据。
 *
 * @see https://www.javazhiyin.com/24812.html
 *
 * List<Product>已经被改为泛型Acceptable了，
 * 也就是说所有商品统统被泛化且当作“接待者”了，
 * 由于泛型化后的商品像是被打了包裹一样让拜访者无法识别品类，
 * 所以在迭代里面我们让这些商品对象主动去“接待”来访者（第13行）。
 * 这类似于警察（访问者）办案时嫌疑人（接待者）需主动接受调查并出示自己的身份证给警察，
 * 如此就可以基于个人信息查询前科并展开相关调查。
 */
public class Client {

    public static void main(String[] args) {

        List<Acceptable> products = new ArrayList<>();
        products.add(new Candy("小黑兔奶糖", LocalDate.of(2018, 10, 1), 20.00f));
        products.add(new Wine("猫泰白酒", LocalDate.of(2017, 1, 1), 1000.00f));
        products.add(new Fruit("草莓", LocalDate.of(2018, 12, 26), 10.00f, 2.5f));

        Visitor discountVisitor = new DiscountVisitor(LocalDate.of(2019, 1, 1));

        for(Acceptable product : products){
            product.accept(discountVisitor);
        }
    }

}
