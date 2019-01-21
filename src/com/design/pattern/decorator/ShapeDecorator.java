package com.design.pattern.decorator;

/**
 * @Auther: CQ02
 * @Date: 2018/12/21 15:01
 * @Description:
 */
public abstract class ShapeDecorator implements Shape {
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    @Override
    public void draw() {
        decoratedShape.draw();
    }
}
