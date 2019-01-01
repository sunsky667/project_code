package com.sunsky.designModel.stragedy;

/**
 * 计算器
 * 通过注入不同的策略来实现不同的功能
 */
public class Calculator {

    private Strategy strategy;

    /**
     * 注入策略
     * @param strategy
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 调用策略的方法，得到结果
     * @param a
     * @param b
     * @return
     */
    public int getResult(int a , int b){
        return this.strategy.calculate(a,b);
    }
}
