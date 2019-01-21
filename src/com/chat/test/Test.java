package com.chat.test;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Auther: CQ02
 * @Date: 2018/12/3 10:15
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        System.out.println(list.isEmpty());
    }


    private Integer twoDiceThrows() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(1, 7) + random.nextInt(1, 7);
    }
}
