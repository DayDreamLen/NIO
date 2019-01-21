package com.design.pattern.builder;

/**
 * @Auther: CQ02
 * @Date: 2018/12/14 14:17
 * @Description: 建造者模式
 */
public abstract class Builder {
    //声明为抽象方法，具体由子类实现
    public abstract void BuildCPU();


    //声明为抽象方法，具体由子类实现
    public abstract void BuildMainboard();


    //声明为抽象方法，具体由子类实现
    public abstract void BuildHD();

    //返回产品的方法：获得组装好的电脑
    public abstract Computer GetComputer();
}
