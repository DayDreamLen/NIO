package com.design.pattern.adapter;

/**
 * @Auther: CQ02
 * @Date: 2018/12/21 10:43
 * @Description:
 */
public class Mp4Player implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {

    }

    @Override
    public void playMp4(String fileName) {
        System.out.println(fileName);
    }
}
