package com.example.filereview;

public class Chat {
    private String message;
    private int type;
    private String name;
    private String time;
    private int imagepath;

    //这个构造方法时为了send/receive 文字的时候调用
    public Chat(String message,int type,String name,String time){
        this.message = message;
        this.type = type;
        this.name = name;
        this.time = time;
    }
    //这个构造方法时为了send/receive 图片的时候调用
    public Chat(int imagepath,int type,String name,String time){
        this.imagepath = imagepath;
        this.type = type;
        this.name = name;
        this.time = time;
    }

    //这个构造方法是为了再chathome主界面显示各个聊天框的时候调用
    //参数包括图片，名字，聊天文字，时间，是否免打扰（type）
    public Chat(String message,int imagepath,String name,String time,int type){
        this.message = message;
        this.type = type;
        this.name = name;
        this.time = time;
        this.imagepath = imagepath;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImagepath() {
        return imagepath;
    }

    public void setImagepath(int imagepath) {
        this.imagepath = imagepath;
    }
}
