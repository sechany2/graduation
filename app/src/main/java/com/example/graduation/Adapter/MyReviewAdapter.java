package com.example.graduation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.Object.MyReview;
import com.example.graduation.R;

import java.util.ArrayList;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ReviewViewHolder> {

    private ArrayList<MyReview> arrayList;
    private Context context;

    public MyReviewAdapter(ArrayList<MyReview> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myreview_item, parent, false);
        ReviewViewHolder holder = new ReviewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.myreviewUserid.setText(arrayList.get(position).getUserid());
        String a = "/5";
        holder.myreviewRate.setText(String.valueOf(arrayList.get(position).getScore())+a);
        holder.myreviewReview.setText(arrayList.get(position).getReview());
        holder.myreviewProduct.setText(arrayList.get(position).getPd_name());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView myreviewUserid;
        TextView myreviewRate;
        TextView myreviewReview;
        TextView myreviewProduct;


        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            this.myreviewUserid = itemView.findViewById(R.id.myreviewUserid);
            this.myreviewRate = itemView.findViewById(R.id.myreviewRate);
            this.myreviewReview = itemView.findViewById(R.id.myreviewReview);
            this.myreviewProduct = itemView.findViewById(R.id.myreviewProduct);
        }
    }
}
