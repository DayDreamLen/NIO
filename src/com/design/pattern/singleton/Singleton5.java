package com.design.pattern.singleton;

/**
 * @Auther: CQ02
 * @Date: 2018/12/14 11:30
 * @Description: 静态内部类实现
 */
public class Singleton5 {
    /* 私有构造方法，防止被实例化 */
    private Singleton5() {
    }

    /* 此处使用一个内部类来维护单例 */
    private static class SingletonFactory {
        private static Singleton5 singleton = new Singleton5();
    }

    /* 获取实例 */
    public static Singleton5 getInstance() {
        return SingletonFactory.singleton;
    }
}
