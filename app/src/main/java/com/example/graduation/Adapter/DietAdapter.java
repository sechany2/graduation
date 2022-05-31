package com.example.graduation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduation.Object.Product;
import com.example.graduation.R;

import java.util.ArrayList;

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.CustomViewHolder> {


    private ArrayList<Product> arrayList;
    private Context context;

    //엑티비티나 프래그먼트에서 클릭 이벤트를 위한 리스너
    public interface  OnItemClickListener{
        void  onItemClick(View v,int pos);
    }
    private  OnItemClickListener mListener = null; //리스너 초기화

    public  void setOnItemClickListener(OnItemClickListener listener){  //리스너 setter
        this.mListener = listener;
    }

    public DietAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        FrameLayout frame =(FrameLayout)parent.findViewById(R.id.frame);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getPd_profile())
                .into(holder.category_iv_pd_profile);
        holder.category_tv_pd_brandname.setText(arrayList.get(position).getPd_brandname());
        holder.category_tv_pd_name.setText(arrayList.get(position).getPd_name());
        Float b = arrayList.get(position).getPd_avg();
        holder.tv_cg_pd_rt.setText(String.format("%.1f",b));
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
        TextView tv_cg_pd_rt;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {   //리사이클러뷰 아이템 클릭시 이벤트
                    int pos = getAdapterPosition() ;   //아이템 위치 변수
                    if (pos != RecyclerView.NO_POSITION) {
                       if(mListener != null){
                           mListener.onItemClick(v,pos);
                       }
                    }
                }
            });
            this.category_iv_pd_profile = itemView.findViewById(R.id.category_iv_pd_profile);
            this.category_tv_pd_brandname = itemView.findViewById(R.id.category_tv_pd_brandname);
            this.category_tv_pd_name = itemView.findViewById(R.id.category_tv_pd_name);
            this.tv_cg_pd_rt = itemView.findViewById(R.id.tv_cg_pd_rt);
        }
    }
}
