package com.design.pattern.abstractFactory;

/**
 * @Auther: CQ02
 * @Date: 2018/12/10 11:06
 * @Description:
 */
public class WhiteAnimalFactory implements AnimalFactory {
    @Override
    public Cat createCat() {
        return new WhiteCat();
    }

    @Override
    public Dog createDog() {
        return new WhiteDog();
    }
}
