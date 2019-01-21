package com.design.pattern.factoryPattern;

/**
 * @Auther: CQ02
 * @Date: 2018/12/10 10:18
 * @Description:
 */
public class SendMailFactory implements FactoryProvider {
    @Override
    public Sender produce() {
        return new MailSender();
    }
}
