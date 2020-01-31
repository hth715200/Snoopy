package com.example.filereview;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class RegisterActivity extends AppCompatActivity{
    private Context mcontext;
    private EditText userid;
    private EditText userpassword;
    private EditText username;
    private EditText userbirthday;
    private Button btnregister;
    private Button btnbacktologin;
    private SQLiteDatabase db;
    private MyDBOpenHelper myDBOpenHelper;
    private Boolean a;
    private Boolean b;
    private Boolean c;
    private Boolean d;
    Bundle bundle1;
    Cursor cursor;
    StringBuilder sb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        mcontext = RegisterActivity.this;
        myDBOpenHelper = new MyDBOpenHelper(mcontext,"my.db",null,1);
        bindViews();
        btnregister = (Button)findViewById(R.id.RegisterGo);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                db = myDBOpenHelper.getWritableDatabase();

                db.execSQL("INSERT INTO Userdata(userid,userpassword,username,userbirthday) values(?,?,?,?)",
                        new String[]{userid.getText().toString(),userpassword.getText().toString(),
                                username.getText().toString(),userbirthday.getText().toString()});
                Toast.makeText(mcontext,"Register OK",Toast.LENGTH_LONG).show();

                //Check if the data is put into database
                sb = new StringBuilder();
                cursor = db.query("userdata",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    String pid = cursor.getString(cursor.getColumnIndex("userid"));
                    Toast.makeText(mcontext,pid,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mcontext,"No data in database",Toast.LENGTH_SHORT).show();
                }

                //Judge if the information is legal
                a = !TextUtils.isEmpty(userid.getText());
                b = !TextUtils.isEmpty(userpassword.getText());
                c = !TextUtils.isEmpty(username.getText());
                d = !TextUtils.isEmpty(userbirthday.getText());

                if (a&&b&&c&&d) {
                    //intent to the page which shows the user's message
                    Intent intent = new Intent(RegisterActivity.this, UserMessageActivity.class);
                    bundle1 = new Bundle();
                    bundle1.putString("registerid", userid.getText().toString());
                    intent.putExtras(bundle1);
                    startActivity(intent, bundle1);
                }else {
                    Toast.makeText(mcontext,"Please input your message",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnbacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });





    }

    private void bindViews(){
        userid = (EditText)findViewById(R.id.useridR);
        userpassword = (EditText)findViewById(R.id.passwordR);
        username = (EditText)findViewById(R.id.nameR);
        userbirthday = (EditText)findViewById(R.id.birthdayR);
        btnregister = (Button)findViewById(R.id.RegisterGo);
        btnbacktologin = (Button)findViewById(R.id.BacktoLogin);
    }

}
