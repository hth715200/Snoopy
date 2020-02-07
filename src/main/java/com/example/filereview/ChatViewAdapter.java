package com.example.filereview;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //type的初始赋值，来判断调用哪一个layout,随便什么数值都可以，只要不为null即可
    public static final int TYPE_SEND_MESSAGE = 1;
    public static final int TYPE_SEND_IMAGE = 2;
    public static final int TYPE_RECEIVE_MESSAGE = 3;
    public static final int TYPE_RECEIVE_IMAGE = 4;

    //上下文参数+bean_class的储存数据的数组
    private Context context;
    private ArrayList<Chat> chatArrayList = new ArrayList<>();


    //构造方法
    public ChatViewAdapter(Context context,ArrayList<Chat> chatArrayList){
        this.context =context;
        this.chatArrayList = chatArrayList;
    }

    //重写getitemview方法，来判断TYPE的种类
    @Override
    public int getItemViewType(int position){
        //根据position获得数组中对应的位置，从而用gettype方法获得type，settype在之后设置
        if (chatArrayList.get(position).getType()==TYPE_SEND_MESSAGE){
            //这里返回的类型时int viewtype，这将在后续的onCreatViewHolder中用来判断绑定对应的layout
            return TYPE_SEND_MESSAGE;
        }else if (chatArrayList.get(position).getType()==TYPE_SEND_IMAGE){
            return TYPE_SEND_MESSAGE;
        }else if (chatArrayList.get(position).getType()==TYPE_RECEIVE_MESSAGE){
            return TYPE_RECEIVE_MESSAGE;
        }else if (chatArrayList.get(position).getType()==TYPE_RECEIVE_IMAGE){
            return TYPE_RECEIVE_IMAGE;
        }else {
            return super.getItemViewType(position);
        }
    }


    //onCreateViewHolder中将不同的holder绑定到不同的layout上，做完初始化，这一个方法中的
    //返回值就是holder这个对象
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==TYPE_SEND_MESSAGE){
            view= LayoutInflater.from(context).inflate(R.layout.chat_chat_sendmessage,parent,false);
            return new SendMessageViewHolder(view);
        }else if (viewType==TYPE_SEND_IMAGE){
            view=LayoutInflater.from(context).inflate(R.layout.chat_chat_sendimage,parent,false);
            return new SendImageViewHolder(view);
        }else if (viewType==TYPE_RECEIVE_MESSAGE){
            view= LayoutInflater.from(context).inflate(R.layout.chat_chat_recievemessage,parent,false);
            return new ReceiveMessageViewHolder(view);
        }else if (viewType==TYPE_RECEIVE_IMAGE){
            view= LayoutInflater.from(context).inflate(R.layout.chat_chat_receiveimage,parent,false);
            return new ReceiveImageViewHolder(view);
        }

        return null;
    }

    //BindViewHolder部分要做有关数据的绑定,先制作了有关文字的部分。
    // todo 图片部分稍后完成
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //判断holder的对象属于哪一个内部class，从而对其完成相应的数据绑定
        /**
         * 时间判断的机制：
         * 在主Activity中，加入数据数组的时候，赋予时间为String的形式，在适配器中则负责获取与显示
         * 1.对于某一个位置（position）的view来说，先获取这个位置的数据数组中的时间（getTime（））
         * 2.然后获取position-1 位置的时间（同样是数据数组中的getTime（））
         * 3.设定一个long参数，目的是为了将之前的2个String型时间转化为long（用日期处理class中的返回值时long的DatetoLong（）
         * 4.把这2个long参数相减
         * 5.把这个long型的减数用stampToMinute（）方法[或者时其他的判定时间间隔的方法]转换成分钟mm的Sting形式，再用Integer强转成int型
         * 6.判断这个int的值多少（几分钟的间隔需要显示时间）
         * 7.需要显示的话先将上一个位置的String时间转化成long的数值放在stampToDate中，这样出来的就是我们再stampToDate中规定好的格式的String
         * 8.判断不需要显示的话直接隐藏控件即可
         */


        if (holder instanceof SendMessageViewHolder){
            //将holder赋值给对应的sendmessageholder，这样就可以调用其中的成员变量
            SendMessageViewHolder sendMessageViewHolder = (SendMessageViewHolder)holder;
            //将数组中的数据（message）取出来，放到对应的textview的组件中
            sendMessageViewHolder.item_send_message.setText(chatArrayList.get(position).getMessage());
            //将数组中的数据（time）取出来，放到对应的time-textview中
            sendMessageViewHolder.item_send_time.setText(chatArrayList.get(position).getTime());

            //后续可以加入时间间隔的判断机制，过于接近的时间可以不显示

            String end = chatArrayList.get(position).getTime();
            if (position!=0){
                String start = chatArrayList.get(position-1).getTime();
                long longend = TimeStampUtils.dateToLong(end);
                long longstart = TimeStampUtils.dateToLong(start);
                long minus_time = longend-longstart;

                long timestamp = Integer.valueOf(TimeStampUtils.stampToMinute(minus_time));
                //显示时间的条件时间隔1分钟
                if (timestamp>1){
                    sendMessageViewHolder.item_send_time.setVisibility(View.VISIBLE);
                    sendMessageViewHolder.item_send_time.setText(TimeStampUtils.stampToDate(longend));
                }else{
                    sendMessageViewHolder.item_send_time.setVisibility(View.GONE);
                }
            }else {
                sendMessageViewHolder.item_send_time.setVisibility(View.GONE);
            }

        }else if (holder instanceof SendImageViewHolder){
            SendImageViewHolder sendImageViewHolder = (SendImageViewHolder) holder;
            //todo 有关图片的发送在这之后再进行完成
        }else if (holder instanceof ReceiveMessageViewHolder){
            ReceiveMessageViewHolder receiveMessageViewHolder = (ReceiveMessageViewHolder)holder;
            receiveMessageViewHolder.item_receive_message.setText(chatArrayList.get(position).getMessage());
            receiveMessageViewHolder.item_receive_time.setText(chatArrayList.get(position).getTime());
            String end = chatArrayList.get(position).getTime();
            if (position!=0){
                String start = chatArrayList.get(position-1).getTime();
                long longend = TimeStampUtils.dateToLong(end);
                long longstart = TimeStampUtils.dateToLong(start);
                long minus_time = longend-longstart;

                long timestamp = Integer.valueOf(TimeStampUtils.stampToMinute(minus_time));
                //显示时间的条件时间隔1分钟
                if (timestamp>1){
                    receiveMessageViewHolder.item_receive_time.setVisibility(View.VISIBLE);
                    receiveMessageViewHolder.item_receive_time.setText(TimeStampUtils.stampToDate(longend));
                }else{
                    receiveMessageViewHolder.item_receive_time.setVisibility(View.GONE);
                }
            }else {
                receiveMessageViewHolder.item_receive_time.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return chatArrayList.size();
    }


    //建立4个不同viewholder内部类,并在其中完成不同的layout下各个组件的初始化和绑定工作

    //发送文字Holder
    private class SendMessageViewHolder extends RecyclerView.ViewHolder{
        //对应layout下的组件的声明,包括发送内容（文字/图片），时间。（发送者姓名和头像的话只要TYPE确定了就可以确定，因此没有变化）
        private TextView item_send_message;
        private TextView item_send_time;

        public SendMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            item_send_message = (TextView) itemView.findViewById(R.id.send_message);
            item_send_time = (TextView)itemView.findViewById(R.id.send_time);
        }
    }

    //发送图片Holder
    private class SendImageViewHolder extends RecyclerView.ViewHolder{
        //由于在layout中time的id在send—message/send-image中时一样的，因此不需要多次绑定，只需要一个即可
        private ImageView item_send_image;

        public SendImageViewHolder(@NonNull View itemView) {
            super(itemView);
            item_send_image = (ImageView)itemView.findViewById(R.id.send_image);
        }
    }

    //接受文字Holer
    private class ReceiveMessageViewHolder extends RecyclerView.ViewHolder{

        private TextView item_receive_message;
        private TextView item_receive_time;

        public ReceiveMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            item_receive_message = (TextView)itemView.findViewById(R.id.receive_message);
            item_receive_time = (TextView)itemView.findViewById(R.id.receive_time);
        }
    }

    //接受图片Holder
    private class ReceiveImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView item_receive_image;

        public ReceiveImageViewHolder(@NonNull View itemView) {
            super(itemView);
            item_receive_image = (ImageView)itemView.findViewById(R.id.receive_image);
        }
    }
}
