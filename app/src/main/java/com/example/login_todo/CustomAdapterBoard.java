package com.example.login_todo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomAdapterBoard extends RecyclerView.Adapter<CustomAdapterBoard.CustomViewHolder>{
    private ArrayList<Bcontent> arrayList;
    private Bcontent arrayList2;
    private Context context;
    private AdapterView.OnItemClickListener itemClickListener;

    public CustomAdapterBoard(ArrayList<Bcontent> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_board,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) { //실제 각 아이템들에 대하여 매칭
        holder.board_title.setText(arrayList.get(position).getBtitle());
        holder.board_writer.setText(arrayList.get(position).getBwriter());
        holder.board_date.setText(arrayList.get(position).getBdate());
        Log.d(String.valueOf(this),"홀더 매칭 게시판");
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView board_title;
        TextView board_writer;
        TextView board_date;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.board_title = itemView.findViewById(R.id.board_title);
            this.board_writer = itemView.findViewById(R.id.board_writer);
            this.board_date = itemView.findViewById(R.id.board_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        String s = arrayList.get(pos).getBcontent();
                        System.out.println(s);
                        Intent intent = new Intent(view.getContext(),Boardinfo.class);
                        intent.putExtra("boardtitle",arrayList.get(pos).getBtitle());
                        intent.putExtra("boardwriter",arrayList.get(pos).getBwriter());
                        intent.putExtra("boardcontent",arrayList.get(pos).getBcontent());
                        view.getContext().startActivity(intent);
                    }
                }
            });
        }
    }

}
