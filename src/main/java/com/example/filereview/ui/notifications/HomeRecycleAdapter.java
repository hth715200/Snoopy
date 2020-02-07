package com.example.filereview.ui.notifications;

import android.content.Context;
import android.icu.text.Transliterator;
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

public class HomeRecycleAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    //参数定义
    private Context context;
    private ArrayList<DataEntity>dataEntities; //存放数据对象的专门list
    private static final int HEAD_TYPE=00001;
    private static final int BODY_TYPE=00002;
    private static final int FOOT_TYPE=00003;
    private int headCount=1;//头部个数，后续可以自己拓展
    private int footCount=1;//尾部个数，后续可以自己拓展

    //建立几个返回值为boolean的方法从位置判断是否到达头部或底部
    private int getBodySize() {
        return dataEntities.size();
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
    public HomeRecycleAdapter(Context context,ArrayList<DataEntity>dataEntities){
        //传递来的数据赋值给本地变量
        this.context = context;
        this.dataEntities = dataEntities;
    }

    /**
     * 绑定子类的layout（一个单位的view）
     * 创建Viewholder，返回一个ViewHolder对象
     *
     * 根据viewtype来分别绑定子类的layout
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case HEAD_TYPE:
                View view = inflater.inflate(R.layout.chat_items_find_head, parent, false);
                HeadViewHolder headViewHolder = new HeadViewHolder(view);
                holder = headViewHolder;
                break;
            case BODY_TYPE:
                View view2 = inflater.inflate(R.layout.chat__items_find, parent, false);
                BodyViewHolder bodyViewHolder = new BodyViewHolder(view2);
                holder = bodyViewHolder;
                break;
            case FOOT_TYPE:
                View view3 = inflater.inflate(R.layout.chat_items_find_foot,parent,false);
                FootViewHolder footViewHolder = new FootViewHolder(view3);
                holder = footViewHolder;
                break;
            default:
                break;
        }
            return holder;

    }


    /**
     * 数据与view的绑定，根据位置（position）绑定数据（position——>data对象）
     *用holder获取data实体类中的属性（获取数据），绑定到上一条的位置。（holder中set一下data对象中需要的属性）
     *
     *
     * 根据对象的所属来分别将数据关联到position上（也就是holder）
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeadViewHolder){

        }else if (holder instanceof BodyViewHolder) {
            DataEntity data = dataEntities.get(position - headCount);
            ((BodyViewHolder) holder).imageView1.setImageResource(data.getImagePath1());
            ((BodyViewHolder) holder).imageView2.setImageResource(data.getImagePath2());
            ((BodyViewHolder) holder).textView.setText(data.getTextname1());

        }else if (holder instanceof FootViewHolder){

        }

    }

    // 整个list的长度
    @Override
    public int getItemCount() {
        return dataEntities.size()+headCount+footCount;
    }


    /**
     * t完成一个子类view中各个组件的初始化和绑定
     * itemView上设置点击事件的监听，并回传点击监听事件，实现接口中的方法
     *
     * body中的操作
     */
    class BodyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView1;
        public ImageView imageView2;
        public TextView textView;
        public BodyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView1 = (ImageView)itemView.findViewById(R.id.find_icon);
            imageView2 = (ImageView)itemView.findViewById(R.id.find_user_icon);
            textView = (TextView)itemView.findViewById(R.id.find_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v,getLayoutPosition());

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
        //接口中定义一个方法，参数根据需要来
        void OnItemClick(View view, int position);
    }
    //因为需要被外部的Activity/Fragment调用，设置一个监听的对象并且做一个set的方法入口

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}

