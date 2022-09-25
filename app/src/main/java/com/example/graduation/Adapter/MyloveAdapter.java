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

public class MyloveAdapter extends RecyclerView.Adapter<MyloveAdapter.CustomViewHolder> {


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

    public MyloveAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mylove_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        FrameLayout frame =(FrameLayout)parent.findViewById(R.id.frame);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getPd_profile())
                .into(holder.mylove_iv_pd_profile);
        holder.mylove_tv_pd_brandname.setText(arrayList.get(position).getPd_brandname());
        holder.mylove_tv_pd_name.setText(arrayList.get(position).getPd_name());
        Float b = arrayList.get(position).getPd_avg();
        holder.mylove_tv_pd_rt.setText(String.format("%.1f 점",b));
        holder.myHistory_tv_date.setText(arrayList.get(position).getHistory_date());
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView mylove_iv_pd_profile;
        TextView mylove_tv_pd_brandname;
        TextView mylove_tv_pd_name;
        TextView mylove_tv_pd_rt;
        TextView myHistory_tv_date;


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

            this.mylove_iv_pd_profile = itemView.findViewById(R.id.mylove_iv_pd_profile);
            this.mylove_tv_pd_brandname = itemView.findViewById(R.id.mylove_tv_pd_brandname);
            this.mylove_tv_pd_name = itemView.findViewById(R.id.mylove_tv_pd_name);
            this.mylove_tv_pd_rt = itemView.findViewById(R.id.mylove_tv_pd_rt);
            this.myHistory_tv_date = itemView.findViewById(R.id.myHistory_tv_date);
        }
    }
}
