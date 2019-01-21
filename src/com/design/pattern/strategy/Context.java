package com.design.pattern.strategy;

import java.util.TreeSet;

/**
 * @Auther: CQ02
 * @Date: 2019/1/21 10:47
 * @Description:
 */
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public int executeStrategy(int num1, int num2) {
        return strategy.doOperation(num1, num2);
    }
}
