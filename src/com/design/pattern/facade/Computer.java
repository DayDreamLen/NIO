package com.design.pattern.facade;

/**
 * @Auther: CQ02
 * @Date: 2018/12/24 10:14
 * @Description:
 */
public class Computer implements IComputer {

    private Cpu cpu;
    private Memory memory;
    private Disk disk;

    public Computer() {
        cpu = new Cpu();
        memory = new Memory();
        disk = new Disk();
    }

    @Override
    public void startUp() {
        System.out.println("start the computer!");
        cpu.startUp();
        memory.startUp();
        disk.startUp();
        System.out.println("start computer finished!");
    }

    @Override
    public void shutDown() {
        System.out.println("begin to close the computer!");
        cpu.shutDown();
        memory.shutDown();
        disk.shutDown();
        System.out.println("computer closed!");
    }
}
