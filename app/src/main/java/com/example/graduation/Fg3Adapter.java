package com.example.graduation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Fg3Adapter extends RecyclerView.Adapter<Fg3Adapter.Fg3ViewHolder> {

    private ArrayList<Product> arrayList;     //arraylist생성
    private Context context;

    public Fg3Adapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Fg3ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fg3list_item,parent,false);
        Fg3ViewHolder holder = new Fg3ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Fg3ViewHolder holder, int position) {
        //glide 로 이미지 받아온것 출력
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getPd_profile())
                .into(holder.fg3_iv);
        //settext로 텍스트 받아온것 출력
        holder.fg3list_tv_productName.setText(arrayList.get(position).getPd_brandname());
        holder.fg3list_tv_brandName.setText(arrayList.get(position).getPd_name());
        if(arrayList.get(position).getPd_avg()!=null){
            holder.fg3_pdrb.setRating(arrayList.get(position).getPd_avg());
        }
        if(arrayList.get(position).getUs_avg()!=null){
            holder.fg3_usrb.setRating(arrayList.get(position).getUs_avg());
        }
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class Fg3ViewHolder extends RecyclerView.ViewHolder {
        ImageView fg3_iv;
        TextView fg3list_tv_productName;
        TextView fg3list_tv_brandName;
        RatingBar fg3_pdrb, fg3_usrb;
        public Fg3ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.fg3_iv = itemView.findViewById(R.id.fg3_iv);
            this.fg3list_tv_productName = itemView.findViewById(R.id.fg3list_tv_pd_name);
            this.fg3list_tv_brandName = itemView.findViewById(R.id.fg3list_tv_pd_brandname);
            this.fg3_pdrb = itemView.findViewById(R.id.fg3_pdrb);
            this.fg3_usrb = itemView.findViewById(R.id.fg3_usrb);
        }
    }
}
