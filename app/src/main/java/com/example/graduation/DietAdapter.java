package com.example.graduation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.CustomViewHolder> {

    private ArrayList<Product> arrayList;
    private Context context;

    public DietAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getPd_profile())
                .into(holder.category_iv_pd_profile);
        holder.category_tv_pd_brandname.setText(arrayList.get(position).getPd_brandname());
        holder.category_tv_pd_name.setText(arrayList.get(position).getPd_name());
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView category_iv_pd_profile;
        TextView category_tv_pd_brandname;
        TextView category_tv_pd_name;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.category_iv_pd_profile = itemView.findViewById(R.id.category_iv_pd_profile);
            this.category_tv_pd_brandname = itemView.findViewById(R.id.category_tv_pd_brandname);
            this.category_tv_pd_name = itemView.findViewById(R.id.category_tv_pd_name);
        }
    }
}
