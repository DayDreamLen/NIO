package com.design.pattern.bridge;

/**
 * @Auther: CQ02
 * @Date: 2018/12/25 15:39
 * @Description:
 */
public class White implements Color {
    @Override
    public void bepaint(String shape) {
        System.out.println("白色的" + shape);
    }
}
