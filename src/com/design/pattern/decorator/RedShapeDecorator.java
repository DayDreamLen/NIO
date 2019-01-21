package com.design.pattern.decorator;

/**
 * @Auther: CQ02
 * @Date: 2018/12/21 15:05
 * @Description:
 */
public class RedShapeDecorator extends ShapeDecorator {

    public RedShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw() {
        decoratedShape.draw();
        setRedBorder(decoratedShape);
    }

    private void setRedBorder(Shape decoratedShape) {
        System.out.println("red");
    }
}
