package com.design.pattern.template;

/**
 * @Auther: CQ02
 * @Date: 2019/1/21 11:18
 * @Description:
 */
public class TemplatePatternDemo {
    public static void main(String[] args) {

        Game game = new Cricket();
        game.play();
        System.out.println();
        game = new Football();
        game.play();
    }
}
