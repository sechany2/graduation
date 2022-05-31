package com.example.graduation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            this.rq_title = itemView.findViewById(R.id.tv_rq_title);
            this.rq_name = itemView.findViewById(R.id.tv_rq_name);
            this.rq_category = itemView.findViewById(R.id.tv_rq_category);
            this.rq_content = itemView.findViewById(R.id.tv_rq_contents);
        }
    }
}
