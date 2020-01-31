package com.example.filereview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<File> datalist=new ArrayList<>();
    String dir;                                     //用于存放每个文件夹的路径
    static View.OnClickListener myOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



        myOnClickListener = new MyOnClickListener(this);

        //接受下一个页面传来的的intent，（在本Activity中完成进入下一层文件的操作）
        Intent intent = getIntent();
        dir = intent.getStringExtra("dir");

        //如果dir为空（第一次启动的情况）找到储存卡的url路径赋值给dir
        if (dir != null) {
        } else {
            dir = Environment.getRootDirectory().getAbsolutePath();
//            dir = Environment.getExternalStorageDirectory().getCanonicalPath();
        }
        Log.e("Message","第一次的dir路径"+dir);


        //获得dir下的所有文件
        getFile();

        /*
        RecycleView的各种初始化操作
         */
        //初始化RecycleView的layout
        recyclerView = findViewById(R.id.recycle_view);
        //初始化RecycleView中的布局的layout
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //给RecycleView装载适配器
        adapter = new FilesAdapter(datalist);
        recyclerView.setAdapter(adapter);
    }

    public void Button1onClick(View view){
        finish();

    }

    public class MyOnClickListener implements View.OnClickListener{
        public final Context context;

        public MyOnClickListener(Context context){
            this.context = context;
        }

        @Override
        public void onClick(View v) {


            int itemS=recyclerView.getChildAdapterPosition(v);
            RecyclerView.ViewHolder viewHolder =recyclerView.findViewHolderForAdapterPosition(itemS);
            TextView Name = (TextView) viewHolder.itemView.findViewById(R.id.file_text);
            String SelectedName=(String) Name.getText();

            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra("dir",dir+"/"+SelectedName);

            Uri uri = Uri.parse(dir+"/"+SelectedName);
            File file = new File(String.valueOf(uri));
            if (file.isDirectory()){
                if (file.canRead()) {
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this,"Your Have No Permission",Toast.LENGTH_LONG).show();
                }
            }else if (file.isFile()){
                Toast.makeText(MainActivity.this,"No Further Folder",Toast.LENGTH_LONG).show();
            }

        }

    }





    //获取dir路径下的所有文件，储存在datalist中
    public List<File> getFile() {
        File file = new File(dir);
        if (file.exists()) {
            File[] file1 = file.listFiles();
            for (File fileview : file1) {
                datalist.add(fileview);
            }
        }else{
            Log.d("Message","获取dir下文件的状况："+"文件不存在");
        }
        return datalist;
    }
}

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

