package com.example.login_todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class main extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager; //리사이클러뷰 레이아웃 매니저 연결
    private ArrayList<Mcontent> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    home homeFragment;
    board boardFragment;
    board_write boardWriteFragment;
    chat chatFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        homeFragment = new home();
        boardFragment = new board();
        boardWriteFragment = new board_write();
        chatFragment = new chat();
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

//        recyclerView = findViewById(R.id.recyclerView); //아이디 연결
//        recyclerView.setHasFixedSize(true); //리사이클러뷰 성능강화
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        arrayList = new ArrayList<>(); //mcontent객체를 담은 어레이리스트

        getSupportFragmentManager().beginTransaction().replace(R.id.containers,homeFragment).commit();
        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationview);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers,homeFragment).commit();
                        return true;
                    case R.id.board:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers,boardFragment).commit();
                        return true;
                    case R.id.board_write:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers,boardWriteFragment).commit();
                        return true;
                    case R.id.chat:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers,chatFragment).commit();
                        return true;
                }
                return false;
            }
        });

//        database = FirebaseDatabase.getInstance(); //파이어베이스 연동
//        databaseReference = database.getReference().child("Memo"); //DB연동
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            Intent memo_intent = getIntent();
//            String check_id = memo_intent.getStringExtra("user_id");
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                arrayList.clear(); //기존 배열 초기화
//                for (DataSnapshot snaphot: snapshot.getChildren()){
//                    String ma = snaphot.child("uid").getValue().toString();
//                    //특정 아이디를 가지고 있는 값만 출력
//                    System.out.println(ma);
//                    if(ma.equals(check_id)) {
//                        Log.d(String.valueOf(this),snaphot.getValue().toString());
//                        Mcontent mcontent = snaphot.getValue(Mcontent.class);
//                        arrayList.add(mcontent); //담은 데이터 배열 리스트에 넣고 리사이클러뷰로 보낼준비
//                    }else continue;
//                }
//                adapter.notifyDataSetChanged(); //리스트 저장 및 새로 고침
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("memo", String.valueOf(error.toException()));
//            }
//        });
//
//        adapter = new CustomAdapter(arrayList, this);
//        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결
    }

//    public void make_todo(View view) {
//        EditText todo_text = findViewById(R.id.todo_text);
//        String user_text = todo_text.getText().toString();
//
//        Intent memo_intent = getIntent();
//        String check_id = memo_intent.getStringExtra("user_id");
//        Map<String,Object> map  = new HashMap<>();
//        map.put("uid",check_id);
//        map.put("memo",user_text);
//        databaseReference.push().setValue(map);
//        todo_text.setText("");
//    }
}