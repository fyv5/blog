package com.example.fy.blog.bean;

import java.util.List;

/**
 * Created by fy on 2016/4/7.
 * 类的描述
 */
public interface PageList<T> {
    public int getPageSize();
    public int getCount();
    public List<T> getList();
}
