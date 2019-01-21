package com.design.pattern.factoryPattern;

/**
 * @Auther: CQ02
 * @Date: 2018/12/10 10:19
 * @Description:
 */
public class SendSmsFactory implements FactoryProvider {
    @Override
    public Sender produce() {
        return new SmsSender();
    }
}
