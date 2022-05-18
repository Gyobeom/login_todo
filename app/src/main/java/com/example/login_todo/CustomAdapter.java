package com.example.login_todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{

    private ArrayList<Mcontent> arrayList;
    private Context context; //어댑트에서 액티비티 액션을 가져올 때 context가 필요함 선택된 액티비티 context가져올 때 필요

    public CustomAdapter(ArrayList<Mcontent> arrayList,Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //리스트뷰 어댑트 연결 후 뷰홀더 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        //각아이템들에 대해서 매칭해야함
        holder.ch_memo.setText(arrayList.get(position).getMemo()); //메모값 가져와서 넣어줌
    }

    @Override
    public int getItemCount() {
        //삼항 연산자 조건식 ? 참 실행문 : 거짓 실행문
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        CheckBox ch_memo;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ch_memo = itemView.findViewById(R.id.tv_memo);
        }
    }
}
