package com.example.fy.blog.bean;

import android.graphics.Bitmap;

/**
 * Created by fy on 2016/6/10.
 */
public class Comment extends Entity {

    private String id;//commentId

    private String pid;//commentPid

    private String blogId;

    private String username;

    private String avatarUrl;

    private String time;

    private String content;

    public Comment(){

    }

    public Comment(String id, String pid, String blogId,
                   String username, String avatarUrl, String time, String content) {
        this.id = id;
        this.pid = pid;
        this.blogId = blogId;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.time = time;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
