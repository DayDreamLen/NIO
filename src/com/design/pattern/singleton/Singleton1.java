package com.design.pattern.singleton;

/**
 * @Auther: CQ02
 * @Date: 2018/12/14 10:28
 * @Description: 饿汉式单例
 */
public class Singleton1 {

    private static Singleton1 singleton = new Singleton1();

    private Singleton1() {
    }

    // 静态工厂方法
    public static Singleton1 getInstance() {
        return singleton;
    }
}
