package com.examdfple.mykcb;

import static com.examdfple.mykcb.R.mipmap.lianxi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

public class Eceely_Demo_Adpdet extends RecyclerView.Adapter<Eceely_Demo_Adpdet.MyMOdelDemo> {
    Context mcintent;
    ArrayList<Draw_Dome_Model> itemlist;
    public Eceely_Demo_Adpdet(Context mcontent, ArrayList<Draw_Dome_Model> itemlist) {
        this.mcintent = mcontent;
        this.itemlist = itemlist;
    }

    @NonNull
    @Override
    public Eceely_Demo_Adpdet.MyMOdelDemo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mcintent).inflate(R.layout.draw_item,parent,false);
        return new MyMOdelDemo(view);
    }

    @Override
    @SuppressLint("ResourceType")
    public void onBindViewHolder(@NonNull Eceely_Demo_Adpdet.MyMOdelDemo holder, @SuppressLint("RecyclerView") int position) {
    // 设置数据
        holder.textviewitem_name.setText(itemlist.get(position).getItem_name());
        Bitmap bitmap = BitmapFactory.decodeResource(mcintent.getResources(),itemlist.get(position).getItem_icon());
        holder.imgview_item.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcintent.startActivity(new Intent(mcintent,itemlist.get(position).getItemActivity()));  // 跳转到首页

            }
        });
    }
  //  获取传入数据的大小
    @Override
    public int getItemCount() {
        return this.itemlist.size();
    }

    public static class MyMOdelDemo extends ViewHolder{
        TextView textviewitem_name;
        ImageView imgview_item;
        public MyMOdelDemo(@NonNull View itemView) {
            super(itemView);
            textviewitem_name = itemView.findViewById(R.id.drawitem_id);
            imgview_item = itemView.findViewById(R.id.imgviewdar);

        }
    }
}
