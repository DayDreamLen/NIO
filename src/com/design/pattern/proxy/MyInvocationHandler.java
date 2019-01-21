package com.design.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Auther: CQ02
 * @Date: 2018/12/21 15:50
 * @Description: jdk动态代理
 */
public class MyInvocationHandler implements InvocationHandler {

    Subject realSubject;

    public MyInvocationHandler(Subject realSubject) {
        this.realSubject = realSubject;
    }

    /**
     * @param proxy  代理类
     * @param method 正在调用的方法
     * @param args   方法的参数
     * @return
     * @throws Throwable
     */

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("sellBooks")) {
            int invoke = (int) method.invoke(realSubject, args);
            return invoke;
        } else if (method.getName().equals("speak")) {
            String string = (String) method.invoke(realSubject, args);
            return string;
        }
        return null;
    }
}
