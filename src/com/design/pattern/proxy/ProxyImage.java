package com.design.pattern.proxy;

/**
 * @Auther: CQ02
 * @Date: 2018/12/21 15:34
 * @Description: 静态代理
 */
public class ProxyImage implements Image {

    private RealImage realImage;
    private String fileName;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}
