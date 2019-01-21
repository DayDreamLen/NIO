package com.design.pattern.bridge;

/**
 * @Auther: CQ02
 * @Date: 2018/12/25 15:37
 * @Description:
 */
public class Circle extends Shape {
    @Override
    public void draw() {
        color.bepaint("圆形");
    }
}
