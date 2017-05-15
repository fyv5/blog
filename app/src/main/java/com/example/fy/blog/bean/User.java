package com.example.fy.blog.bean;

/**
 * Created by fy on 2016/4/8.
 */
public class User extends Entity {

    private String username;
    private String portrait;
    private String new_portrait;



    public User(){

    }

    public User(String username,String portrait,String new_portrait){
        this.username = username;
        this.portrait = portrait;
        this.new_portrait = new_portrait;
    }

    public String get_username() {
        return username;
    }

    public void set_username(String username) {
        this.username = username;
    }

    public String get_portrait() {
        return portrait;
    }

    public void set_portrait(String _portrait) {
        this.portrait = _portrait;
    }

    public String getNew_portrait() {
        return new_portrait;
    }

    public void setNew_portrait(String _new_portriait) {
        this.new_portrait = new_portrait;
    }


}
