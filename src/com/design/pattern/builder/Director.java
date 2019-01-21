package com.design.pattern.builder;

/**
 * @Auther: CQ02
 * @Date: 2018/12/14 14:21
 * @Description:
 */
public class Director {
    //指挥装机人员组装电脑
    public void Construct(Builder builder) {

        builder.BuildCPU();
        builder.BuildMainboard();
        builder.BuildHD();
    }
}
