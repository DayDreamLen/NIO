package com.design.pattern.builder;

/**
 * @Auther: CQ02
 * @Date: 2018/12/14 14:22
 * @Description:
 */
public class ConcreteBuilder extends Builder {

    Computer computer = new Computer();

    @Override
    public void BuildCPU() {
        computer.Add("组装CPU");
    }

    @Override
    public void BuildMainboard() {
        computer.Add("组装主板");
    }

    @Override
    public void BuildHD() {
        computer.Add("组装主板");
    }

    @Override
    public Computer GetComputer() {
        return computer;
    }
}
