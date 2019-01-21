package com.design.pattern.template;

/**
 * @Auther: CQ02
 * @Date: 2019/1/21 11:10
 * @Description:
 */
public abstract class Game {
    abstract void initialize();

    abstract void startPlay();

    abstract void endPlay();

    public final void play() {

        //初始化游戏
        initialize();

        //开始游戏
        startPlay();

        //结束游戏
        endPlay();
    }
}
