package com.example.login_todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class login_form extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    private AlertDialog dialog;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
            ArrayList<String> id_array = new ArrayList<>();
            EditText edt_id = findViewById(R.id.customer_id);
            EditText edt_pw = findViewById(R.id.f_pw);
            EditText edt_pw_check = findViewById(R.id.s_pw);
            EditText edt_name = findViewById(R.id.customer_name);
            EditText edt_ph_num = findViewById(R.id.edt_phone);
            //자동 하이픈 입력
            edt_ph_num.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
            RadioGroup edt_gender = findViewById(R.id.gender);
            Button btn = findViewById(R.id.button);
            Button check_id = findViewById(R.id.idcheck);



            //아이디 중복확인 버튼 클릭시 이벤트
            check_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ch_id = edt_id.getText().toString();
                   myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot_id: snapshot.getChildren()){
                                id_array.add(snapshot_id.child("uid").getValue().toString());
                                Log.d(String.valueOf(this),id_array.toString());
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(login_form.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    for (String i : id_array){
                        if (i.equals(ch_id)){
                            AlertDialog.Builder builder = new AlertDialog.Builder(login_form.this);
                            dialog = builder.setMessage("사용할 수 없는 아이디입니다.").setNegativeButton("확인",null).create();
                            dialog.show();

                        }
                    }

                }
            });

            btn.setOnClickListener(new View.OnClickListener() {
                //회원가입 버튼 클릭시 이벤트
                @Override
                public void onClick(View view) {
                    String u_id = edt_id.getText().toString();
                    String u_pw = edt_pw.getText().toString();
                    String u_pwcheck = edt_pw_check.toString();
                    String u_name = edt_name.getText().toString();
                    String u_ph = edt_ph_num.getText().toString();
                    int id = edt_gender.getCheckedRadioButtonId();
                    RadioButton rb = findViewById(id);
                    String u_gender = null;
                    //아무입력하지 않았을 경우
                    if (rb != null) {
                        u_gender = rb.getText().toString();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(login_form.this);
                        dialog = builder.setMessage("입력되지 않은 항목이 있습니다.").setNegativeButton("확인", null).create();
                        dialog.show();
                    }
                    //입력되지 않은 항목 찾기
                    if (u_id.equals("") || u_pw.equals("") || u_name.equals("") || u_ph.equals("") || u_pwcheck.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(login_form.this);
                        dialog = builder.setMessage("입력되지 않은 항목이 있습니다.").setNegativeButton("확인", null).create();
                        dialog.show();
                    } else {
                        HashMap<Object, String> hashMap = new HashMap<>();
                        hashMap.put("uid", u_id);
                        hashMap.put("upw", u_pw);
                        hashMap.put("uname", u_name);
                        hashMap.put("uphone", u_ph);
                        hashMap.put("ugender", u_gender);
                        //회원가입 데이터 쓰기
                        myRef.child(u_id).setValue(hashMap);

                        //데이터 읽기 특정 child 값인 uid 값 가져오기
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    //    ArrayList<String> id = new ArrayList<>();
                                    Log.d(String.valueOf(this), "실행결과 " + snapshot1.child("uid").getValue());
                                    //    id.add(snapshot1.child("uid").getValue().toString());
                                    //    Log.d(String.valueOf(this),id.toString());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(login_form.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        Toast.makeText(login_form.this, "회원가입이 되었습니다.", Toast.LENGTH_SHORT).show();


                    }
                }
            });

    }


}