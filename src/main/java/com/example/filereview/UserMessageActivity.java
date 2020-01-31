package com.example.filereview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class UserMessageActivity extends AppCompatActivity {
    private TextView displayid;
    private TextView displaypassword;
    private TextView displayname;
    private TextView displaybirthday;
    private TextView welcome;
    private  MyDBOpenHelper myDBOpenHelper;
    private SQLiteDatabase db;
    private Button btnback;
    private Button btnchange;
    Cursor cursor;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message);

        //Initial Button/Text View
        bindViews();

        //Get the Intent and the userID from Login/Register
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String userIDregister = bundle.getString("registerid");
        String userIDlogin = bundle.getString("loginid");
        Toast.makeText(UserMessageActivity.this,userIDlogin,Toast.LENGTH_LONG).show();

        //Judge the Intent resource is Login/Register
        if (userIDlogin==null&&userIDregister!=null){
            id = userIDregister;
        }else if (userIDlogin!=null&&userIDregister==null){
            id = userIDlogin;
        }

        //Initial the Database
        myDBOpenHelper = new MyDBOpenHelper(this,"my.db",null,1);
        db = myDBOpenHelper.getReadableDatabase();

        //Use Cursor to get data from Database According to userid
        //And show the information in the layout
        cursor=db.rawQuery("SELECT * FROM userdata WHERE userid=?",new String[]{id});
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            displayid.setText(id);
            displayname.setText(cursor.getString(cursor.getColumnIndex("username")));
            displaypassword.setText(cursor.getString(cursor.getColumnIndex("userpassword")));
            displaybirthday.setText(cursor.getString(cursor.getColumnIndex("userbirthday")));
            welcome.setText("Welcome   "+id);
        }

        //Initial and listen of the BackToHome button and Change button
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMessageActivity.this,HomeActivity.class);
                intent.putExtra("id",id);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                setResult(RESULT_OK,intent);
//                LoginActivity loginActivity = new LoginActivity();
//                loginActivity.finish();
                startActivity(intent);
            }
        });
        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public  void bindViews() {

        displayid = (TextView)findViewById(R.id.displayid);
        displaypassword = (TextView)findViewById(R.id.displaypassword);
        displayname = (TextView)findViewById(R.id.displayname);
        displaybirthday = (TextView)findViewById(R.id.displaybirthday);
        welcome = findViewById(R.id.welcome);
        btnback = (Button)findViewById(R.id.backtoHome);
        btnchange = (Button)findViewById(R.id.change);
    }
}
