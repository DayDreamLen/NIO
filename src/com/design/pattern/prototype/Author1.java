package com.design.pattern.prototype;

import java.io.Serializable;

/**
 * @Auther: CQ02
 * @Date: 2018/12/20 16:37
 * @Description: 原型模式深复制
 */
public class Author1 implements Serializable {
    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
