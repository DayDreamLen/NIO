package com.design.pattern.strategy;

/**
 * @Auther: CQ02
 * @Date: 2019/1/21 10:44
 * @Description:
 */
public class OperationSubstract implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 - num2;
    }
}
