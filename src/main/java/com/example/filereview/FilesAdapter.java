package com.example.filereview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {

    private List<File>filelist;


    //为holder关联View
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView filename;

        public ViewHolder(View view) {
            super(view);
            filename = (TextView)view.findViewById(R.id.file_text);

        }
    }

    //构造方法，参数为储存File的数组
    public FilesAdapter(List<File>list){
        filelist = list;

    }

    /*
    重写父类的三个方法
     */

    //1.重写onCreate方法，创建一个holder实例并加载数据的布局Layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.files_items,parent,false);
        view.setOnClickListener(MainActivity.myOnClickListener);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }
    //2.重写onBind方法，获取数据的位置，并设定该位置的监听
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        File file = filelist.get(position);
        holder.filename.setText(file.getName());
    }

    //3.重写getItemCount方法，获取数组长度
    @Override
    public int getItemCount() {
        return filelist.size();
    }

}
