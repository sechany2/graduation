package com.example.graduation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Object.MyReview;
import com.example.graduation.Object.Request;
import com.example.graduation.R;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private ArrayList<Request> arrayList;
    private Context context;

    public RequestAdapter(ArrayList<Request> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    public interface  OnItemClickListener{
        void  onItemClick(View v,int pos);
    }
    private RequestAdapter.OnItemClickListener mListener = null; //리스너 초기화

    public  void setOnItemClickListener(RequestAdapter.OnItemClickListener listener){  //리스너 setter
        this.mListener = listener;
    }
    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pull_request_item, parent, false);
        RequestViewHolder holder = new RequestViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        holder.rq_title.setText(arrayList.get(position).getTitle());
        holder.rq_name.setText(String.valueOf(arrayList.get(position).getName()));
        holder.rq_category.setText(arrayList.get(position).getCategory());
        holder.rq_content.setText(arrayList.get(position).getContents());
        if( arrayList.get(position).getAnswer() != null ) {
            holder.rq_layout.setVisibility(View.VISIBLE);
            holder.rq_answer.setText(arrayList.get(position).getAnswer());
            holder.answer_btn.setVisibility(View.GONE);
        }
        holder.rq_date.setText(arrayList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView rq_title;
        TextView rq_name;
        TextView rq_category;
        TextView rq_content;
        TextView rq_answer;
        TextView rq_date;
        LinearLayout rq_layout;
        Button answer_btn;
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
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


            this.rq_title = itemView.findViewById(R.id.tv_rq_title);
            this.rq_name = itemView.findViewById(R.id.tv_rq_name);
            this.rq_category = itemView.findViewById(R.id.tv_rq_category);
            this.rq_content = itemView.findViewById(R.id.tv_rq_contents);
            this.rq_answer = itemView.findViewById(R.id.tv_rq_answer);
            this.rq_layout = itemView.findViewById(R.id.answer);
            this.answer_btn = itemView.findViewById(R.id.answer_btn);
            this.rq_date = itemView.findViewById(R.id.tv_rq_date);
        }
    }
}
