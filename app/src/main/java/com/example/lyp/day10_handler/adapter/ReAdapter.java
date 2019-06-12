package com.example.lyp.day10_handler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.lyp.day10_handler.R;
import com.example.lyp.day10_handler.beans.Artical;

import java.util.ArrayList;

public class ReAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Artical.DataBean.DatasBean> list;

    public ReAdapter(Context context, ArrayList<Artical.DataBean.DatasBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i==0){

            View view=View.inflate(context,R.layout.item_one,null);
            ViewHolder1 holder1 = new ViewHolder1(view);

            return holder1;

        }else {

            View view=View.inflate(context,R.layout.item_two,null);

            ViewHolder2 holder2 = new ViewHolder2(view);

            return holder2;

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        final Artical.DataBean.DatasBean bean = list.get(i);

        int type = getItemViewType(i);
        if (type==0){

            ViewHolder1 holder1= (ViewHolder1) viewHolder;

            Glide.with(context).load(bean.getEnvelopePic()).apply(new RequestOptions().bitmapTransform(new RoundedCorners(30))).into(holder1.one_iv);

            holder1.one_title.setText(bean.getTitle());
        }else if (type==1){

            ViewHolder2 holder2= (ViewHolder2) viewHolder;

            Glide.with(context).load(bean.getEnvelopePic()).apply(new RequestOptions().bitmapTransform(new RoundedCorners(30))).into(holder2.two_iv);

        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myOnClick!=null){
                    myOnClick.onClick(i,bean);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public int getItemViewType(int position) {

        if (position==6){

            return 1;
        }else {
            return 0;
        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder{

        private ImageView one_iv;
        private TextView one_title;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);

            one_iv=itemView.findViewById(R.id.one_iv);
            one_title=itemView.findViewById(R.id.one_title);
        }
    }
    public class ViewHolder2 extends RecyclerView.ViewHolder{
        private ImageView two_iv;
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);

            two_iv=itemView.findViewById(R.id.two_iv);
        }
    }

    private MyOnClick myOnClick;

    public void setMyOnClick(MyOnClick myOnClick) {
        this.myOnClick = myOnClick;
    }

    public interface MyOnClick{
        void onClick(int i,Artical.DataBean.DatasBean datasBean);
    }

}
