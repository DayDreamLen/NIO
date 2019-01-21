package com.design.pattern.bridge;

import java.awt.*;

/**
 * @Auther: CQ02
 * @Date: 2018/12/25 15:29
 * @Description:
 */
public abstract class Shape {
    Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract void draw();
}
