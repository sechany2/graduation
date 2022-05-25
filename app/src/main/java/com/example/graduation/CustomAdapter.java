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

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHoder> {

    private ArrayList<Product> arrayList;     //arraylist생성
    private Context context;

    //컨스트럭쳐
    public CustomAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    //엑티비티나 프래그먼트에서 클릭 이벤트를 위한 리스너
    public interface  OnItemClickListener{
        void  onItemClick(View v,int pos);
    }
    private CustomAdapter.OnItemClickListener mListener = null; //리스너 초기화

    public  void setOnItemClickListener(CustomAdapter.OnItemClickListener listener){  //리스너 setter
        this.mListener = listener;
    }

    //커스텀뷰홀더 메소드
    @NonNull
    @Override
    //뷰홀더 실행했을때
    public CustomViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false); //list_item 연결
        CustomViewHoder holder = new CustomViewHoder(view);  //holder 생성
        return holder;
    }
    @Override
    //뷰홀더바인드할떄
    public void onBindViewHolder(@NonNull CustomViewHoder holder, int position) {
        //glide 로 이미지 받아온것 출력
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getPd_profile())
                .into(holder.list_iv_profile);
        //settext로 텍스트 받아온것 출력
        holder.list_tv_productName.setText(arrayList.get(position).getPd_brandname());
        holder.list_tv_brandName.setText(arrayList.get(position).getPd_name());

    }
    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0); //삼항연산자 (조건 ? 참일때 : 거짓일때)
    }

    //커스텀뷰홀더 클래스
    public class CustomViewHoder extends RecyclerView.ViewHolder {

        ImageView list_iv_profile;
        TextView list_tv_productName;
        TextView list_tv_brandName;

        public CustomViewHoder(@NonNull View itemView) {
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
            this.list_iv_profile = itemView.findViewById(R.id.list_iv_pd_profile);
            this.list_tv_productName = itemView.findViewById(R.id.list_tv_pd_name);
            this.list_tv_brandName = itemView.findViewById(R.id.list_tv_pd_brandname);


        }
    }
}
