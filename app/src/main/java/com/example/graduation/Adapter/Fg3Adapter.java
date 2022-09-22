package com.example.graduation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduation.Object.Product;
import com.example.graduation.R;

import java.util.ArrayList;

public class Fg3Adapter extends RecyclerView.Adapter<Fg3Adapter.Fg3ViewHolder> {

    private ArrayList<Product> arrayList;     //arraylist생성
    private Context context;

    public Fg3Adapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public interface  OnItemClickListener{
        void  onItemClick(View v,int pos);
    }

    private Fg3Adapter.OnItemClickListener mListener = null; //리스너 초기화



    //엑티비티나 프래그먼트에서 클릭 이벤트를 위한 리스너


    public void setOnItemClickListener(Fg3Adapter.OnItemClickListener listener){  //리스너 setter
        this.mListener = listener;
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
        if(arrayList.get(position).getRecommendation_count()!=null){
        holder.fg3_ct.setText(arrayList.get(position).getRecommendation_count().toString());
        }
        if(arrayList.get(position).getPd_avg()!=null){
            //holder.fg3_pdrb.setRating(arrayList.get(position).getPd_avg());
        }
        if(arrayList.get(position).getUs_avg()!=null){
            //holder.fg3_usrb.setRating(arrayList.get(position).getUs_avg());
        }
        //holder.fg3_usrb_tv.setText(arrayList.get(position).getUs_avg().toString());
        Float a = arrayList.get(position).getUs_avg();
        String usrbtv = String.format("%.1f",a);
        holder.fg3_usrb_tv.setText(usrbtv+"점");
        Float b = arrayList.get(position).getPd_avg();
        String pdrbtv = String.format("%.1f",b);
        holder.fg3_pdrb_tv.setText(pdrbtv+"점");
        //holder.fg3_pdrb_tv.setText(arrayList.get(position).getPd_avg().toString());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class Fg3ViewHolder extends RecyclerView.ViewHolder {
        TextView fg3_ct;
        ImageView fg3_iv;
        TextView fg3list_tv_productName;
        TextView fg3list_tv_brandName;
        RatingBar fg3_pdrb, fg3_usrb;
        TextView fg3_usrb_tv, fg3_pdrb_tv;
        ImageButton favoritebtn;


        public Fg3ViewHolder(@NonNull View itemView) {
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
            this.fg3_iv = itemView.findViewById(R.id.fg3_iv);
            this.fg3list_tv_productName = itemView.findViewById(R.id.fg3list_tv_pd_name);
            this.fg3list_tv_brandName = itemView.findViewById(R.id.fg3list_tv_pd_brandname);
            this.favoritebtn = itemView.findViewById(R.id.favoritebtn);
            this.fg3_ct = itemView.findViewById(R.id.ct);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {   //리사이클러뷰 아이템 클릭시 이벤트
                    int pos = getAdapterPosition();   //아이템 위치 변수
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });

            //this.fg3_pdrb = itemView.findViewById(R.id.fg3_pdrb);
            //this.fg3_usrb = itemView.findViewById(R.id.fg3_usrb);
            this.fg3_usrb_tv = itemView.findViewById(R.id.fg3_usrb_tv);
            this.fg3_pdrb_tv = itemView.findViewById(R.id.fg3_pdrb_tv);
        }
    }
}
