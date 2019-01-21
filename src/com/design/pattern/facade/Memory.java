package com.design.pattern.facade;

/**
 * @Auther: CQ02
 * @Date: 2018/12/24 10:12
 * @Description:
 */
public class Memory implements IComputer {
    @Override
    public void startUp() {
        System.out.println("Memory start up");
    }

    @Override
    public void shutDown() {
        System.out.println("Memory shut down");
    }
}
