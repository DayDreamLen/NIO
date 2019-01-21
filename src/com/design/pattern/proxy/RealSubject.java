package com.design.pattern.proxy;

/**
 * @Auther: CQ02
 * @Date: 2018/12/21 15:45
 * @Description:
 */
public class RealSubject implements Subject {
    @Override
    public int sellBooks() {
        System.out.println("卖书");
        return 1;
    }

    @Override
    public String speak() {
        System.out.println("说话");
        return "张三";
    }
}
