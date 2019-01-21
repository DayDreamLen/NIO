package com.design.pattern.decorator;

/**
 * @Auther: CQ02
 * @Date: 2018/12/21 15:08
 * @Description:
 */
public class DecoratorPatternDemo {

    public static void main(String[] args) {

        Shape circle = new Circle();

        Shape redCircle = new RedShapeDecorator(new Circle());

        Shape redRectangle = new RedShapeDecorator(new Rectangle());

        circle.draw();

        System.out.println("\n");
        redCircle.draw();

        System.out.println("\n");
        redRectangle.draw();
    }
}
