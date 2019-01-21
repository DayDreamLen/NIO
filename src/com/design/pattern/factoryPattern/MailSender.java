package com.design.pattern.factoryPattern;

/**
 * @Auther: CQ02
 * @Date: 2018/12/10 10:09
 * @Description:
 */
public class MailSender implements Sender {
    @Override
    public void Send() {
        System.out.println("this is mailsender!");
    }
}
