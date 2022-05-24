package com.example.login_todo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class board_write extends Fragment implements View.OnClickListener{
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public board_write() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_write,container,false);
        Intent memo_intent = getActivity().getIntent();
        String user_id = memo_intent.getStringExtra("user_id");

        Button input_board = view.findViewById(R.id.input_board);
        TextView board_writer = view.findViewById(R.id.board_writer);
        board_writer.setText(user_id);
        input_board.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Board");

//        Log.d(String.valueOf(this),"board_write 진입했습니다.");
//        Log.d(String.valueOf(this),user_id);


        return view;
    }

    @Override
    public void onClick(View view) {
        Date nowDate = new Date();
        TextView board_writer = getView().findViewById(R.id.board_writer);
        EditText board_title = getView().findViewById(R.id.board_title);
        EditText board_content = getView().findViewById(R.id.board_content);
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd a HH:mm:ss");

        String b_writer = board_writer.getText().toString();
        String b_title = board_title.getText().toString();
        String b_content = board_content.getText().toString();
        String b_date = date.format(nowDate);

        Map<String,Object> map = new HashMap<>();
        map.put("bwriter",b_writer);
        map.put("btitle",b_title);
        map.put("bcontent",b_content);
        map.put("bdate",b_date);
        databaseReference.push().setValue(map);
        board_title.setText("");
        board_content.setText("");
    }
}