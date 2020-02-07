package com.example.filereview.ui.home;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filereview.ChatChatActivity;
import com.example.filereview.ChatDBOpenHelper;
import com.example.filereview.DataEntity;
import com.example.filereview.R;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    Context context;
    SQLiteDatabase dbhome;
    private ChatDBOpenHelper chatDBOpenHelper;
    private String friendid="bot1";

    private TextView textView_test;
    private View view;
    public RecyclerView findchathomerecycleview;
    private ArrayList<DataEntity> chathomedata = new ArrayList<>();
    private ChatHomeRecycleAdapter chatHomeRecycleAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar myToolbar = getActivity().findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);


        initData();
        initRecycView();
        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    //直接从数据库中取数据
    public void initData(){

        context=getActivity();
        chatDBOpenHelper = new ChatDBOpenHelper(getActivity(),"ChatRecord.db",null,1);
        dbhome = chatDBOpenHelper.getWritableDatabase();
        Cursor cursor=dbhome.rawQuery("select * from userdata where friendid=?",new String[]{friendid});

        DataEntity dataEntity = new DataEntity();
        if (cursor.moveToLast()){
            dataEntity.setTextname1(friendid);
            dataEntity.setTextname2(cursor.getString(cursor.getColumnIndex("message")));
            dataEntity.setTextname3(cursor.getString(cursor.getColumnIndex("chattime")));
        }
        chathomedata.add(dataEntity);
    }

    //对RecycleView进行一系列的初始化+
    //启动点击事件之后，带着friendid 跳转到具体的聊天Activity(ChatChat)

    private void initRecycView(){
        findchathomerecycleview = (RecyclerView)view.findViewById(R.id.home_RecycleView);
        chatHomeRecycleAdapter = new ChatHomeRecycleAdapter(getActivity(),chathomedata);
        findchathomerecycleview.setAdapter(chatHomeRecycleAdapter);
        findchathomerecycleview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));

        chatHomeRecycleAdapter.setOnItemClickListener(new ChatHomeRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, String chatid) {
                Intent intent = new Intent(getActivity(),ChatChatActivity.class);
                intent.putExtra("chat_id",chatid);
                startActivity(intent);
            }
        });


    }
}


    //todo 以下时有关toolbar的一些方法的复写，等需要修改toolbar了再来研究
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//
//        menu.clear();
//        inflater.inflate(R.menu.chat_home_menu, menu);
//        if (menu != null) {
//            if (menu.getClass() == MenuBuilder.class) {
//                try {
//                    Method m = menu.getClass().getDeclaredMethod("setOpetionallconsVisible", Boolean.TYPE);
//                    m.setAccessible(true);
//                    m.invoke(menu, true);
//                } catch (Exception e) {
//
//                }
//            }
//        }
//        super.onCreateOptionsMenu(menu, inflater);
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.chat_home_menu_1:
//                Toast.makeText(getContext(), "press button", Toast.LENGTH_SHORT).show();
//                Intent intent1 = new Intent(getActivity(), GroupChatActivity.class);
//                startActivity(intent1);
//                return true;
//            case R.id.chat_home_menu_2:
//                Intent intent2 = new Intent(getActivity(), AddFriendActivity.class);
//                startActivity(intent2);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//                }
//    }
