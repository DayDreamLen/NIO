package com.design.pattern.facade;

/**
 * @Auther: CQ02
 * @Date: 2018/12/24 10:11
 * @Description:
 */
public class Cpu implements IComputer {
    @Override
    public void startUp() {
        System.out.println("CPU start up");
    }

    @Override
    public void shutDown() {
        System.out.println("CPU shut down");
    }
}
