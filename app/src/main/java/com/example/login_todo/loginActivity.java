package com.example.login_todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class loginActivity extends AppCompatActivity {
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText login_id_text = findViewById(R.id.log_id);
        EditText login_pw_text = findViewById(R.id.log_pw);
        ArrayList<String> id_array = new ArrayList<>();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        Button btn = findViewById(R.id.create_customer);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this,login_form.class);
                startActivity(intent);
                finish();
            }
        });
        Button login_btn = findViewById(R.id.login_id);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference().child("Users");
                String user_id = login_id_text.getText().toString();
                String user_pw = login_pw_text.getText().toString();
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot2: snapshot.getChildren()){
                            id_array.add(snapshot2.child("uid").getValue().toString());
                        }
                            if(id_array.contains(user_id)){
                                myRef.orderByKey().equalTo(user_id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot snapshot1: snapshot.getChildren()){
                                            String check_id = snapshot1.child("uid").getValue().toString();
                                            String check_pw = snapshot1.child("upw").getValue().toString();
                                            if(check_id.equals(user_id)){
                                                Log.d(String.valueOf(this),check_pw);
                                                if(check_pw.equals(user_pw)){
                                                    Log.d(String.valueOf(this),"비밀번호 체크");
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity.this);
                                                    dialog = builder.setMessage("로그인 되었습니다.").setNegativeButton("확인",null).create();
                                                    dialog.show();
                                                    id_array.clear();
                                                //    Intent intent = new Intent(MainActivity.this,memo.class);
                                                    Intent intent = new Intent(loginActivity.this, main.class);
                                                    intent.putExtra("user_id",user_id );
                                                    startActivity(intent);
                                                    dialog.dismiss();
                                                    finish();
                                                }else{
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity.this);
                                                    dialog = builder.setMessage("비밀번호를 확인해주세요.").setNegativeButton("확인",null).create();
                                                    dialog.show();
                                                    id_array.clear();
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity.this);
                                dialog = builder.setMessage("아이디를 확인해주세요.").setNegativeButton("확인",null).create();
                                dialog.show();
                                id_array.clear();
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