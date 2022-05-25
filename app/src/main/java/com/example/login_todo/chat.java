package com.example.login_todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class chat extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager; //리사이클러뷰 레이아웃 매니저 연결
    private ArrayList<Ccontent> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    public chat() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        Context context = view.getContext();
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        Intent memo_intent = getActivity().getIntent();
        String user_id = memo_intent.getStringExtra("user_id");

        recyclerView = view.findViewById(R.id.recyclerView_chat);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Chat");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    String test = snapshot1.getValue().toString();
                    System.out.println(test);
                    Ccontent ccontent = snapshot1.getValue(Ccontent.class);
                    arrayList.add(ccontent);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"채팅방을 생성합니다.",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                String c_title = user_id + "의 채팅방 입니다.";
                String c_maker = user_id;

                Map<String, Object> map = new HashMap<>();
                map.put("ctitle",c_title);
                map.put("cuser",c_maker);
                databaseReference.push().setValue(map);
            }
        });

        ActionBar actionBar =((main)getActivity()).getSupportActionBar();
        actionBar.setTitle("채팅");
        // Inflate the layout for this fragment
        adapter = new CustomAdapterChat(arrayList,getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }
}