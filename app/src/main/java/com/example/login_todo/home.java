package com.example.login_todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class home extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager; //리사이클러뷰 레이아웃 매니저 연결
    private ArrayList<Mcontent> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private int check_text;

    public home() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.recyclerView_frag); //아이디 연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 성능강화
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); //mcontent객체를 담은 어레이리스트

        Intent memo_intent = getActivity().getIntent();
        String check_id = memo_intent.getStringExtra("user_id");

        Button btn_make = view.findViewById(R.id.make_todo);
        btn_make.setOnClickListener(this);

        database = FirebaseDatabase.getInstance(); //파이어베이스 연동
        databaseReference = database.getReference().child("Memo"); //DB연동
        databaseReference.addValueEventListener(new ValueEventListener() {
            Intent memo_intent = getActivity().getIntent();
            String check_id = memo_intent.getStringExtra("user_id");
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear(); //기존 배열 초기화
                for (DataSnapshot snaphot: snapshot.getChildren()){
                    String ma = snaphot.child("uid").getValue().toString();
                    //특정 아이디를 가지고 있는 값만 출력
                    System.out.println(ma);
                    if(ma.equals(check_id)) { // if(ma.equals(check_id)) {
                        Log.d(String.valueOf(this),snaphot.getValue().toString());
                        Mcontent mcontent = snaphot.getValue(Mcontent.class);
                        arrayList.add(mcontent); //담은 데이터 배열 리스트에 넣고 리사이클러뷰로 보낼준비
                    }else continue;
                }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로 고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("memo", String.valueOf(error.toException()));
            }
        });
        ActionBar actionBar =((main)getActivity()).getSupportActionBar();
        actionBar.setTitle("TODOLIST");
        adapter = new CustomAdapter(arrayList, getActivity());
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결


        // Inflate the layout for this fragment
        return view; //view 반환
    }
    @Override
    public void onClick(View view) {
        EditText todo_text = getView().findViewById(R.id.todo_text);
        String user_text = todo_text.getText().toString();
        Intent memo_intent = getActivity().getIntent();
        String check_id = memo_intent.getStringExtra("user_id");
        Map<String,Object> map  = new HashMap<>();
        map.put("uid",check_id);
        map.put("memo",user_text);
        databaseReference.push().setValue(map);
        todo_text.setText("");

    }

//    public boolean CheckText(View view){
//        EditText todo_text = getView().findViewById(R.id.todo_text);
//        String user_text = todo_text.getText().toString();
//        Intent memo_intent = getActivity().getIntent();
//
//        database = FirebaseDatabase.getInstance(); //파이어베이스 연동
//        databaseReference = database.getReference().child("Memo"); //DB연동
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            Intent memo_intent = getActivity().getIntent();
//            String check_id = memo_intent.getStringExtra("user_id");
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                arrayList.clear(); //기존 배열 초기화
//                for (DataSnapshot snaphot: snapshot.getChildren()){
//                    String ma = snaphot.child("uid").getValue().toString();
//                    //특정 아이디를 가지고 있는 값만 출력
//                    System.out.println(ma);
//                    if(ma.equals(check_id)) { // if(ma.equals(check_id)) {
//                        Log.d(String.valueOf(this),snaphot.getValue().toString());
//                        if(snaphot.child("memo").getValue().toString().equals(user_text)){
//                            Log.d(String.valueOf(this),snaphot.child("memo").getValue().toString());
//                            Toast.makeText(getContext(),"이미 존재하는 리스트입니다.",Toast.LENGTH_SHORT).show();
//                            break;
//                        }
//                        else {
//                            check_text = 1;
//                        }
//                    }else continue;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("memo", String.valueOf(error.toException()));
//            }
//        });
//        if(check_text == 0)
//        {
//            return false;
//        }
//        else {
//            return true;
//        }
//    }
}