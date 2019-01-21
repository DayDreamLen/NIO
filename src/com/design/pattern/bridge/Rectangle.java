package com.design.pattern.bridge;

/**
 * @Auther: CQ02
 * @Date: 2018/12/25 15:40
 * @Description:
 */
public class Rectangle extends Shape {
    @Override
    public void draw() {
        color.bepaint("正方形");
    }
}
