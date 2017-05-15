package com.example.fy.blog.bean;

/**
 * Created by fy on 2016/4/5.
 */
public class Blog extends Entity {
    private String blogId;
    private String title;
    private User author;
    private String time;
//    private String author;
    private String description;
    private String content;
    private int zan;
    private int cai;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User  author) {
        this.author = author;
    }
//    public String getAuthor(){
//        return author;
//    }
//
//    public void setAuthor(String author){
//        this.author = author;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public int getCai() {
        return cai;
    }

    public void setCai(int cai) {
        this.cai = cai;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
}
