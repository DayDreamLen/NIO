package com.design.pattern.proxy;

/**
 * @Auther: CQ02
 * @Date: 2018/12/21 15:31
 * @Description:
 */
public class RealImage implements Image {
    private String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromDisk(fileName);
    }

    @Override
    public void display() {

    }

    private void loadFromDisk(String fileName) {
        System.out.println("Loading " + fileName);
    }
}
