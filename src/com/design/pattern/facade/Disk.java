package com.design.pattern.facade;

/**
 * @Auther: CQ02
 * @Date: 2018/12/24 10:13
 * @Description:
 */
public class Disk implements IComputer {

    @Override
    public void startUp() {
        System.out.println("Disk start up");
    }

    @Override
    public void shutDown() {
        System.out.println("Disk shut down");
    }
}
