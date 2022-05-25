package com.example.login_todo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterChat extends RecyclerView.Adapter<CustomAdapterChat.CustomViewHolder> {
    private ArrayList<Ccontent> arrayList;
    private Context context;

    public CustomAdapterChat(ArrayList<Ccontent> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chat,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.chat_title.setText(arrayList.get(position).getCtitle());
        holder.chat_writer.setText(arrayList.get(position).getCuser());
        Log.d(String.valueOf(this),"채팅 어댑터");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView chat_title;
        TextView chat_writer;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chat_title = itemView.findViewById(R.id.chat_title);
            this.chat_writer = itemView.findViewById(R.id.chat_writer);
        }
    }
}
