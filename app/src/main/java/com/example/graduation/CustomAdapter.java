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

    private ArrayList<product> arrayList;     //arraylist생성
    private Context context;

    //컨스트럭쳐
    public CustomAdapter(ArrayList<product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    //커스텀뷰홀더 메소드
    @NonNull
    @Override
    //뷰홀더 실행했을때
    public CustomViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false); //list_item 연결
        CustomViewHoder hoder = new CustomViewHoder(view);  //hoder 생성
        return null;
    }
    @Override
    //뷰홀더바인드할떄
    public void onBindViewHolder(@NonNull CustomViewHoder holder, int position) {
        //glide 로 이미지 받아온것 출력
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);
        //settext로 텍스트 받아온것 출력
        holder.tv_productName.setText(arrayList.get(position).getProductName());
        holder.tv_brandName.setText(arrayList.get(position).getBrandName());

    }
    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0); //삼항연산자 (조건 ? 참일때 : 거짓일때)
    }

    //커스텀뷰홀더 클래스
    public class CustomViewHoder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_productName;
        TextView tv_brandName;

        public CustomViewHoder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.tv_productName = itemView.findViewById(R.id.tv_productName);
            this.tv_brandName = itemView.findViewById(R.id.tv_brandName);


        }
    }
}
