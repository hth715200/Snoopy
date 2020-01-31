package com.example.filereview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBOpenHelper extends SQLiteOpenHelper {

    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,"my.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Userdata(usernumber INTEGER PRIMARY KEY AUTOINCREMENT,userid VARCHAR(20),userpassword VARCHAR(20),username VARCHAR(20),userbirthday INTEGER(8) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE Userdata ADD EMAIL VARCHAR(20)");

    }
}
