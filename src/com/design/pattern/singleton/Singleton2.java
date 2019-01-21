package com.design.pattern.singleton;

/**
 * @Auther: CQ02
 * @Date: 2018/12/14 10:32
 * @Description: 懒汉式单例（延迟加载方式）
 */
public class Singleton2 {
    private static Singleton2 singleton = null;

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        if (singleton == null) {
            singleton = new Singleton2();
        }
        return singleton;
    }
}
