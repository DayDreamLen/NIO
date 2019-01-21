package com.design.pattern.Flyweight;

import java.util.HashMap;

/**
 * @Auther: CQ02
 * @Date: 2018/12/25 16:44
 * @Description:
 */
public class ShapeFactory {
    public static final HashMap<String, Shape> circleMap = new HashMap<>();

    public static Shape getCircle(String color) {
        Circle circle = (Circle) circleMap.get(color);

        if (circle == null) {
            circle = new Circle(color);
            circleMap.put(color, circle);
            System.out.println("Creating circle of color : " + color);
        }
        return circle;
    }
}
