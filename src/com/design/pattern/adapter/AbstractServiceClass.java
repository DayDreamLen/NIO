package com.design.pattern.adapter;

/**
 * @Auther: CQ02
 * @Date: 2018/12/21 14:38
 * @Description: 缺省适配器模式
 */
public abstract class AbstractServiceClass implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
    }

    @Override
    public void playMp4(String fileName) {
    }
}
