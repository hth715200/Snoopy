package com.example.filereview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class HomeActivity extends AppCompatActivity {
    Intent intent2;
    Bundle bd;
    String idget;
    Button buttonlogin;
    Button btnfilemanager;
    Button buttonregister;
    Button buttonlogout;
    TextView unknowid;
    TextView loginid;
    Context mcontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mcontext = HomeActivity.this;

        bindView();

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentlogin = new Intent(mcontext,LoginActivity.class);
                startActivity(intentlogin);
            }
        });

        btnfilemanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentfile = new Intent(mcontext,MainActivity.class);
                startActivity(intentfile);
            }
        });

        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentregister = new Intent(mcontext,RegisterActivity.class);
                startActivity(intentregister);
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String data = intent.getStringExtra("id");
        buttonlogin.setVisibility(View.INVISIBLE);
        buttonregister.setVisibility(View.INVISIBLE);
        unknowid.setVisibility(View.INVISIBLE);
        buttonlogout.setVisibility(View.VISIBLE);
        loginid.setText(data);

        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,HomeActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0){
            buttonlogin.setVisibility(View.VISIBLE);
            buttonregister.setVisibility(View.VISIBLE);
            unknowid.setVisibility(View.VISIBLE);
            buttonlogout.setVisibility(View.INVISIBLE);
        }
    }

    public void bindView(){
        buttonlogin = (Button)findViewById(R.id.LOGIN);
        btnfilemanager = (Button)findViewById(R.id.filemanager_button);
        buttonregister = (Button)findViewById(R.id.registerathome);
        buttonlogout = (Button)findViewById(R.id.logout);
        unknowid = (TextView)findViewById(R.id.unknowuser);
        loginid = (TextView)findViewById(R.id.knownuser);
    }


}

