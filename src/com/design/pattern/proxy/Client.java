package com.design.pattern.proxy;

import java.lang.reflect.Proxy;

/**
 * @Auther: CQ02
 * @Date: 2018/12/21 16:05
 * @Description:
 */
public class Client {
    public static void main(String[] args) {
        //真实对象
        Subject realSubject = new RealSubject();

        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(realSubject);
        //代理对象
        Subject proxyClass = (Subject) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{Subject.class}, myInvocationHandler);

        proxyClass.sellBooks();

        proxyClass.speak();

        CGLibProxy cgLibProxy = new CGLibProxy();
        Subject proxyClass1 = (Subject) cgLibProxy.getInstance(RealSubject.class);

        proxyClass1.sellBooks();
    }
}
