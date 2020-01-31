package com.example.filereview;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    Button btnregister;
    Button btnlogin;
    private EditText userid;
    private EditText password;
    String id;
    String pass;
    MyDBOpenHelper myDBOpenHelper;
    Context context;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnregister = (Button)findViewById(R.id.register);
        btnlogin = (Button)findViewById(R.id.login);
        userid = (EditText)findViewById(R.id.userid);
        password = (EditText)findViewById(R.id.password);


        context=LoginActivity.this;
        myDBOpenHelper = new MyDBOpenHelper(LoginActivity.this,"my.db",null,1);
        db = myDBOpenHelper.getWritableDatabase();


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v){
                id = userid.getText().toString();
                pass = password.getText().toString();
                Cursor cursor = db.rawQuery("SELECT * FROM USERDATA WHERE userid=?",new String[]{id});

                if (cursor.moveToFirst() && cursor.getString(cursor.getColumnIndex("userpassword")).equals(pass)) {

                    Intent intent = new Intent(LoginActivity.this,UserMessageActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("loginid", id);
                    intent.putExtras(bundle1);
                    startActivity(intent,bundle1);

                    

                } else if (cursor.moveToFirst() && !(cursor.getString(cursor.getColumnIndex("userpassword")) .equals(pass))) {
                    Toast.makeText(context, "Wrong Password", Toast.LENGTH_LONG).show();
                } else if (id ==""|pass == "") {
                    Toast.makeText(context, "Please Login", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(context, "Please go to Register", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}