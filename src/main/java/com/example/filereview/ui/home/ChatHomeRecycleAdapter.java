package com.example.filereview.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filereview.DataEntity;
import com.example.filereview.R;

import java.util.ArrayList;

public class ChatHomeRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<DataEntity> chatslist; //存放数据对象的专门list

    private static final int HEAD_TYPE=00001;
    private static final int BODY_TYPE=00002;
    private static final int FOOT_TYPE=00003;
    private int headCount=1;//头部个数，后续可以自己拓展
    private int footCount=1;//尾部个数，后续可以自己拓展


    //建立几个返回值为boolean的方法从位置判断是否到达头部或底部
    private int getBodySize() {
        return chatslist.size();
    }
    private boolean isHead(int position) {
        return headCount!=0&&position<headCount;
    }
    private boolean isFoot(int position) {
        return footCount!=0&&(position>=getBodySize()+headCount);
    }

    //复写getItemViewType从而引入判断是否时头部/尾部的参数：viewtype
    @Override
    public int getItemViewType(int position) {
        if (isHead(position)) {
            return HEAD_TYPE;
        }else if (isFoot(position)) {
            return FOOT_TYPE;
        }else {
            return BODY_TYPE;
        }
    }

    //构造方法
    public ChatHomeRecycleAdapter(Context context, ArrayList<DataEntity> chatslist){

        //传递来的数据赋值给本地变量
        this.context = context;
        this.chatslist = chatslist;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case HEAD_TYPE:
                View view = inflater.inflate(R.layout.chat_items_chathome_head, parent, false);
                HeadViewHolder headViewHolder = new HeadViewHolder(view);
                holder = headViewHolder;
                break;
            case BODY_TYPE:
                View view2 = inflater.inflate(R.layout.chat_items_chathome, parent, false);
                BodyViewHolder bodyViewHolder = new BodyViewHolder(view2);
                holder = bodyViewHolder;
                break;
            case FOOT_TYPE:
                View view3 = inflater.inflate(R.layout.chat_items_chathome_foot,parent,false);
                FootViewHolder footViewHolder = new FootViewHolder(view3);
                holder = footViewHolder;
                break;
            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder){

        }else if (holder instanceof BodyViewHolder){
          DataEntity data = chatslist.get(position-headCount);
         //((BodyViewHolder) holder).imageView1.setImageResource(data.getImagePath1());
         //((BodyViewHolder) holder).imageView2.setImageResource(data.getImagePath2());
         ((BodyViewHolder) holder).textView_name.setText(data.getTextname1());
         ((BodyViewHolder) holder).textView_message.setText(data.getTextname2());
         ((BodyViewHolder) holder).textView_time.setText(data.getTextname3());

        }else if (holder instanceof FootViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return chatslist.size()+headCount+footCount;
    }

    class BodyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView1;
        public ImageView imageView2;
        public TextView textView_name;//friend name
        public TextView textView_message;//message
        public TextView textView_time;//time

        public BodyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView1 = (ImageView)itemView.findViewById(R.id.chathome_friend_image_icon);
            imageView2 = (ImageView)itemView.findViewById(R.id.chathome_info_change);
            textView_name = (TextView)itemView.findViewById(R.id.chathome_friend_id_text);
            textView_message = (TextView)itemView.findViewById(R.id.chathome_new_message_text);
            textView_time = (TextView)itemView.findViewById(R.id.chathome_new_message_time_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v,textView_name.getText().toString());

                    }
                }
            });
        }
    }

    //头部的viewholder（可以像body部分一样做一些对于子类内部组件的属性定义和初始化）
    class HeadViewHolder extends RecyclerView.ViewHolder{

        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    //尾部的viewholder（可以像body部分一样做一些对于子类内部组件的属性定义和初始化）
    class FootViewHolder extends RecyclerView.ViewHolder{

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }



    //定义监听的接口
    public interface OnItemClickListener{
        //接口中定义一个方法，参数是对话id（也就是聊天目标任务的friendid）
        void OnItemClick(View view, String chatid);
    }
    //因为需要被外部的Activity/Fragment调用，设置一个监听的对象并且做一个set的方法入口

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
