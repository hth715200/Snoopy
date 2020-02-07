package com.example.filereview;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class ChatChatActivity extends AppCompatActivity {

    Context context;

    //文字输入EditText部分和发送按钮
    private EditText editText;
    private ImageButton imageButton_send;

    //适配器声明+数据数组组+ReecycleView+数据库
    private ChatViewAdapter chatViewAdapter;
    private ArrayList<Chat> chatArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatDBOpenHelper chatDBOpenHelper;
    SQLiteDatabase db;

    //发送的内容（字符串形式）+第一次打开聊天对话框显示的打招呼信息
    private String message;
    private String firsthello;

    //用户名id,先随便设定一个
    //todo 之后再和改ID联系起来
     private String user_name ="Snoopy";
     //聊天对象的id，用来从数据库辨识是哪一场对话
     private String friendid;
     //用来判断是send还是receive，方便数据库中的操作，省略一定的代码数量
     private int type;

     //时间time
    //之后再详细完成时间的部分
    private String time = "time";


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_chat);

        //关于ToolBar的一些操作
        //注意：Toolbar一定要导入androidx的包，其他包会报错
        Toolbar toolbar = (Toolbar)findViewById(R.id.chatchat_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        friendid = intent.getStringExtra("chat_id");

        //界面的初始化以及数据库的初始设定
        bindView();

        context=ChatChatActivity.this;
        chatDBOpenHelper = new ChatDBOpenHelper(ChatChatActivity.this,"ChatRecord.db",null,1);
        db = chatDBOpenHelper.getWritableDatabase();

        /**
         * 这里写curso，选择用户自己以及friendid，做一个判断，if curso.movertofirst（），则读取其中的数据储存在
         * 数据数组中。完成对于聊天记录的导入。
         * 如果其中无数据，则新建一条问候的数据
         *
         */
        Cursor cursor = db.rawQuery("SELECT * FROM userdata WHERE friendid=?", new String[]{friendid});

        //先判断对于某一个friendid是否DB内有数据，如果没有，则新建第一条”nice to meet“的短语，如果有数据，
        // 则根据type来将数据放到数组中，并且用moveToNext进行循环。
        //该friendid之下有多少条数据，则全部输出
        if (cursor.moveToFirst()){
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex("type")).equals("1")) {
                    type = ChatViewAdapter.TYPE_SEND_MESSAGE;
                } else if (cursor.getString(cursor.getColumnIndex("type")).equals("2")) {
                    type = ChatViewAdapter.TYPE_SEND_IMAGE;
                } else if (cursor.getString(cursor.getColumnIndex("type")).equals("3")) {
                    type = ChatViewAdapter.TYPE_RECEIVE_MESSAGE;
                } else if (cursor.getString(cursor.getColumnIndex("type")).equals("4")) {
                    type = ChatViewAdapter.TYPE_RECEIVE_IMAGE;
                }
                chatArrayList.add(new Chat(cursor.getString(cursor.getColumnIndex("message")), type, cursor.getString(cursor.getColumnIndex("userid")), cursor.getString(cursor.getColumnIndex("chattime"))));
//                cursor.moveToNext();
            }
        }else {
            //数据库中没有该friendid的对话的话，则新建一个friendid（也就是新建一个对话），并输出第一句欢迎辞令
            firsthello = "Nice To Meet You";
            chatArrayList.add(new Chat(firsthello,ChatViewAdapter.TYPE_RECEIVE_MESSAGE,user_name,TimeStampUtils.stampToDate(Long.parseLong(TimeStampUtils.getTimeStamp()))));
            db.execSQL("INSERT INTO USERDATA(friendid,userid,type,message,chattime,isreadchat,deletechat) values(?,?,?,?,?,?,?)",
                    new String[]{friendid,user_name,"3",firsthello,TimeStampUtils.stampToDate(Long.parseLong(TimeStampUtils.getTimeStamp())),String.valueOf(1),String.valueOf(1)});
        }


        //recycleview和适配器的一系列初始化固定操作
        //布局管理器初始化+适配器初始化+recycleview直接set这两个
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatChatActivity.this);
        chatViewAdapter = new ChatViewAdapter(ChatChatActivity.this,chatArrayList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chatViewAdapter);


        //做一个发送的按钮监听，来判断是否需要发送输入的message
        imageButton_send.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                //先定义一个string来获取输入的message字符串
                //这个地方点击发送按钮之后将文字内容保存到数据数组，同时将数据数组中的内容再保存到数据库

                message = editText.getText().toString();
                time = TimeStampUtils.stampToDate(Long.parseLong(TimeStampUtils.getTimeStamp()));
                chatArrayList.add(new Chat(message,ChatViewAdapter.TYPE_SEND_MESSAGE,user_name,time));
                db.execSQL("INSERT INTO USERDATA(friendid,userid,type,message,chattime,isreadchat,deletechat) values(?,?,?,?,?,?,?)",
                        new String[]{friendid,user_name,"1",message,time,String.valueOf(1),String.valueOf(1)});

                if (!TextUtils.isEmpty(message)){
                  //更新一下适配器，当文字发送出去之后把edit text重新变为空并且recycleview的position往上移动一位
                  chatViewAdapter.notifyDataSetChanged();
                  editText.setText("");
                  recyclerView.scrollToPosition(chatArrayList.size()-1);
                }
            }
        });
    }

    //做一些初始化的操作，主要时组件的绑定
    public void bindView(){
        recyclerView = (RecyclerView) findViewById(R.id.chat_chat_recycleview);
        editText = (EditText) findViewById(R.id.chat_edit_text);
        imageButton_send = (ImageButton) findViewById(R.id.send_button);
    }

}
