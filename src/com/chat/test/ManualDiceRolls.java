package com.chat.test;

/**
 * @Auther: CQ02
 * @Date: 2018/12/3 16:56
 * @Description:
 */
@FunctionalInterface
public interface ManualDiceRolls {
    void sayMessage(String message);
    static void printHello(){
        System.out.println("Hello");
    }
}
