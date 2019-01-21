package com.design.pattern.adapter;

/**
 * @Auther: CQ02
 * @Date: 2018/12/21 10:43
 * @Description:
 */
public class VlcPlayer implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        System.out.println(fileName);
    }

    @Override
    public void playMp4(String fileName) {

    }
}
