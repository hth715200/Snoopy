package com.example.filereview.ui.notifications;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filereview.AddFriendActivity;
import com.example.filereview.DataEntity;
import com.example.filereview.FriendCycleActivity;
import com.example.filereview.R;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    //定义各种所需要参数

    private View view;
    public RecyclerView findrecyclerView;
    private ArrayList<DataEntity> dataEntities = new ArrayList<DataEntity>();
    private HomeRecycleAdapter homeRecycleAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notifications, container, false);

        //调用初始化data方法
        initData();
        //调用自定义RecycleView初始化方法
        initRecycView();
        return view;
    }

    //自定义数据处理方法
    public void initData(){
        String[] textname = getResources().getStringArray(R.array.find_title);
        TypedArray prod = getResources().obtainTypedArray(R.array.find_icon);

        ArrayList<Integer> imagetitle = new ArrayList<>();
        for (int m=0;m<9;m++){
            int x = prod.getResourceId(m,-1);
            imagetitle.add(x);
        }

        for (int i=0;i<9;i++){
            DataEntity dataEntity = new DataEntity();
            dataEntity.setImagePath1(imagetitle.get(i));
            //dataEntity.setImagePath2(imagetitle[i]);
            dataEntity.setTextname1(textname[i]);
            dataEntities.add(dataEntity);
        }
    }



    //诸多初始化的完成以及按键监听的设置
    private void initRecycView() {
        //获取RecycleView的layout
        findrecyclerView = (RecyclerView) view.findViewById(R.id.find_RecycleView);

        //创建adapter对象
        homeRecycleAdapter = new HomeRecycleAdapter(getActivity(), dataEntities);
        //设置adapter（将adapter对象绑定到recycleview对象上）
        findrecyclerView.setAdapter(homeRecycleAdapter);
        //设置layoutmanager
        findrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置监听(监听要调用自定义的监听接口，因此时绑定在adapter上的，并非recycleview)
        homeRecycleAdapter.setOnItemClickListener(new HomeRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        Toast.makeText(getActivity(), "press button in position 0", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "press button in position 1", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(getActivity(), FriendCycleActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "press button in position 2", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "press button in position 3", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getActivity(), "press button in position 4", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(getActivity(), "press button in position 5", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(getActivity(), "press button in position 6", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(getActivity(), "press button in position 7", Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(getActivity(), "press button in position 8", Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                        Toast.makeText(getActivity(), "press button in position 9", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}

