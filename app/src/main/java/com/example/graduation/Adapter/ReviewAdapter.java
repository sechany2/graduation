package com.example.graduation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.R;
import com.example.graduation.Object.Review;
import com.example.graduation.Object.Review_write;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<Review> arrayList;
    private ArrayList<Review_write> arrayList2;
    private List arlist;
    private Context context;

    public ReviewAdapter(ArrayList<Review> arrayList,ArrayList<Review_write> arrayList2, Context context) {
        this.arrayList = arrayList;
        this.arrayList2 = arrayList2;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        ReviewViewHolder holder = new ReviewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.reviewUserid.setText(arrayList.get(position).getUserid());
        String a = "/5";
        holder.reviewRate.setText(String.valueOf(arrayList.get(position).getScore())+a);
        holder.reviewReview.setText(arrayList.get(position).getReview());
        holder.reviewDate.setText(arrayList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView reviewUserid;
        TextView reviewRate;
        TextView reviewReview;
        TextView reviewDate;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            this.reviewUserid = itemView.findViewById(R.id.reviewUserid);
            this.reviewRate = itemView.findViewById(R.id.reviewRate);
            this.reviewReview = itemView.findViewById(R.id.reviewReview);
            this.reviewDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
