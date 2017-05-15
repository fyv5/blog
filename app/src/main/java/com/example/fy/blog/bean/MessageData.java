package com.example.fy.blog.bean;

/**
 * 类名:MessageData.java
 * Created by fy on 2016/4/7.
 * 加载数据结果
 */
public class MessageData<Result extends PageList<?>>{

    public static final int MESSAGE_STATE_ERROR = -1;
    public static final int MESSAGE_STATE_EMPTY = 0;
    public static final int MESSAGE_STATE_MORE = 1;
    public static final int MESSAGE_STATE_FULL = 2;

    public int state;
    public Result result;
    public Exception exception;

    public MessageData(int state){
        this.state = state;
        this.result = null;
        this.exception = null;
    }

    public MessageData(Result result){
        if(result != null){
            int size = result.getPageSize();
            if(size == 0){
                this.state = MESSAGE_STATE_EMPTY;
            }else if(size < 20){//默认分页大小是20，Appcontext类中会进行配置！！！！！！！！！！
                this.state = MESSAGE_STATE_FULL;
            }else{
                this.state = MESSAGE_STATE_MORE;
            }
        }else{
            this.state = MESSAGE_STATE_ERROR;
        }
        this.result = result;
        this.exception = null;
    }

    public MessageData(Exception exception){
        this.state = MESSAGE_STATE_ERROR;
        this.result = null;
        this.exception = exception;
    }

}
