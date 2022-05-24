package com.example.login_todo;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Memo");
        CheckBox ch_memo;
        Button btn;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ch_memo = itemView.findViewById(R.id.tv_memo);
            btn = itemView.findViewById(R.id.delete_memo);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ch_text = ch_memo.getText().toString();
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot parentsnap : snapshot.getChildren()){
                                String ma = parentsnap.child("memo").getValue().toString();
                                if (ma.equals(ch_text)){
                                    myRef.child(parentsnap.getKey()).setValue(null);
                                    System.out.println("삭제되었습니다.");
                                }

                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });
        }
    }

}
