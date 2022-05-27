package com.example.graduation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduation.R;
import com.example.graduation.Object.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<User> arrayList;
    private Context context;

    public UserAdapter(ArrayList<User> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    public interface  OnItemClickListener{
        void  onItemClick(View v,int pos);
    }
    private UserAdapter.OnItemClickListener mListener = null; //리스너 초기화

    public  void setOnItemClickListener(UserAdapter.OnItemClickListener listener){  //리스너 setter
        this.mListener = listener;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        UserViewHolder holder = new UserViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.userID.setText(arrayList.get(position).getUserID());
        holder.userPassword.setText(String.valueOf(arrayList.get(position).getPassword()));
        holder.userName.setText(arrayList.get(position).getName());
        holder.reviewVolume.setText(arrayList.get(position).getReviewVolume());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userID;
        TextView userPassword;
        TextView userName;
        TextView reviewVolume;



        public UserViewHolder(@NonNull View itemView) {
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
            this.userID = itemView.findViewById(R.id.userID);
            this.userPassword = itemView.findViewById(R.id.userPassword);
            this.userName = itemView.findViewById(R.id.userName);
            this.reviewVolume = itemView.findViewById(R.id.reviewVolume);
        }
    }
}
