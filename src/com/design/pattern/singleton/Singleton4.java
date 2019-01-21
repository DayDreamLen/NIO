package com.design.pattern.singleton;

/**
 * @Auther: CQ02
 * @Date: 2018/12/14 10:52
 * @Description:
 */
public class Singleton4 {
    // 私有构造
    private Singleton4() {
    }

    private static Singleton4 single = null;

    // 双重检查
    public static Singleton4 getInstance() {
        if (single == null) {
            synchronized (Singleton4.class) {
                if (single == null) {
                    single = new Singleton4();
                }
            }
        }
        return single;
    }
}
