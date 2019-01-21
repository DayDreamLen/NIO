package com.design.pattern.prototype;

/**
 * @Auther: CQ02
 * @Date: 2018/12/20 16:34
 * @Description: 原型模式浅复制
 */
public class Book implements Cloneable {

    private String title;

    private int pageNum;

    private Author author;

    @Override
    public Book clone() {
        Book book = null;
        try {
            book = (Book) super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return book;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
