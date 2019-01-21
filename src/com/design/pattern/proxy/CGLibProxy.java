package com.design.pattern.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


/**
 * @Auther: CQ02
 * @Date: 2018/12/24 17:30
 * @Description:
 */
public class CGLibProxy implements MethodInterceptor {

    private   Enhancer en = new Enhancer();

    //通过原对象返回代理对象
    public Object getInstance(Class clazz) {

        en.setSuperclass(clazz);
        en.setCallback(this);
        return en.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy methodProxy) throws Throwable {
        System.out.println("用户验证...");
        Object result = methodProxy.invokeSuper(obj, args);
        System.out.println("xx用户调用xx方法");
        return result;
    }
}
