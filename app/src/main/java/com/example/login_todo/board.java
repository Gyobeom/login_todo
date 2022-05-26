package com.example.login_todo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class board extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager; //리사이클러뷰 레이아웃 매니저 연결
    private ArrayList<Bcontent> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public board() {
        // Required empty public constructor
    }
    public static board newInstance(){
        return new board();
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_board,container,false);
        Context context = view.getContext();

        recyclerView = view.findViewById(R.id.recyclerView_board
        );
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Board");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    String test = snapshot1.getValue().toString();
                    System.out.println(test);
                    Bcontent bcontent = snapshot1.getValue(Bcontent.class);
                    arrayList.add(bcontent);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ActionBar actionBar =((main)getActivity()).getSupportActionBar();
        actionBar.setTitle("게시판");
        adapter = new CustomAdapterBoard(arrayList, getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }

}