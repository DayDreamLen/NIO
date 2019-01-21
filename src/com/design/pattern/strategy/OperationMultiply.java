package com.design.pattern.strategy;

/**
 * @Auther: CQ02
 * @Date: 2019/1/21 10:45
 * @Description:
 */
public class OperationMultiply implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 * num2;
    }
}
