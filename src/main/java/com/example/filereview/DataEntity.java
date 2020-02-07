package com.example.filereview;

import java.io.Serializable;

public class DataEntity implements Serializable {

    public int imagePath1;//图标地址1
    public int imagePath2;//图标地址2
    public int imagePath3;//图标地址3
    public String textname1;//文本内容1
    public String textname2;//文本内容2
    public String textname3;//文本内容3


    public int getImagePath1() {
        return imagePath1;
    }

    public void setImagePath1(int imagePath1) {
        this.imagePath1 = imagePath1;
    }

    public int getImagePath2() {
        return imagePath2;
    }

    public void setImagePath2(int imagePath2) {
        this.imagePath2 = imagePath2;
    }

    public int getImagePath3() {
        return imagePath3;
    }

    public void setImagePath3(int imagePath3) {
        this.imagePath3 = imagePath3;
    }

    public String getTextname1() {
        return textname1;
    }

    public void setTextname1(String textname1) {
        this.textname1 = textname1;
    }

    public String getTextname2() {
        return textname2;
    }

    public void setTextname2(String textname2) {
        this.textname2 = textname2;
    }

    public String getTextname3() {
        return textname3;
    }

    public void setTextname3(String textname3) {
        this.textname3 = textname3;
    }
}
