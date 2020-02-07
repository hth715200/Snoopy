package com.example.filereview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ChatDBOpenHelper extends SQLiteOpenHelper {

    public ChatDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context,"ChatRecord.db",null,1);
    }

    //数据库名字：ChatRecord.db

    /**
     * 1.chatnumber 聊天编号
     * 2.friendid 友人id
     * 3.userid 自己的id
     * 4.message 聊天内容
     * 5.imagepath 图片path
     * 6.chattime 聊天记录的时间
     * 7.isread 是否可读
     * 8.deletechat 是否删除
     * 9.type 属于send部分还是receive部分
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERDATA(chatnumber INTEGER PRIMARY KEY AUTOINCREMENT,friendid VARCHAR(64),userid VARCHAR(64),type VARCHAR(8),message VARCHAR(255),imagepath VARCHAR(255),chattime VARCHAR(64),isreadchat integer,deletechat integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
