package com.design.pattern.abstractFactory;

/**
 * @Auther: CQ02
 * @Date: 2018/12/10 11:06
 * @Description:
 */
public class BlackAnimalFactory implements AnimalFactory {
    @Override
    public Cat createCat() {
        return new BlackCat();
    }

    @Override
    public Dog createDog() {
        return new BlackDog();
    }
}
