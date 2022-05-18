package com.example.login_todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class memo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager; //리사이클러뷰 레이아웃 매니저 연결
    private ArrayList<Mcontent> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<HashMap> tempArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        recyclerView = findViewById(R.id.recyclerView); //아이디 연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 성능강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); //mcontent객체를 담은 어레이리스트
        tempArraylist = new ArrayList<>();


        database = FirebaseDatabase.getInstance(); //파이어베이스 연동

        databaseReference = database.getReference().child("Memo"); //DB연동
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear(); //기존 배열 초기화
                for (DataSnapshot snaphot: snapshot.getChildren()){
                    Log.d(String.valueOf(this),snaphot.getValue().toString());
                    Mcontent mcontent = snaphot.getValue(Mcontent.class);
                    arrayList.add(mcontent); //담은 데이터 배열 리스트에 넣고 리사이클러뷰로 보낼준비
                    Log.d(String.valueOf(this),tempArraylist.toString());
                }
                Log.d(String.valueOf(this),arrayList.toString());
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로 고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("memo", String.valueOf(error.toException()));
            }
        });


        adapter = new CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결


    }

    public void make_todo(View view) {
        databaseReference.child("fffff").child("memo").setValue("안녕하세요.");
    }
}