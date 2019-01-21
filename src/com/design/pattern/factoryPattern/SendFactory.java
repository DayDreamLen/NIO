package com.design.pattern.factoryPattern;

/**
 * @Auther: CQ02
 * @Date: 2018/12/10 10:10
 * @Description:
 */
public class SendFactory {

    public Sender produceMail() {
        return new MailSender();
    }

    public Sender produceSms() {
        return new SmsSender();
    }
}
