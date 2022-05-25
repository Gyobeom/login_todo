package com.example.login_todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

public class Boardinfo extends AppCompatActivity {
    private TextView b_title;
    private TextView b_writer;
    private TextView b_content;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardinfo);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("게시판");
        actionBar.setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        b_title = findViewById(R.id.board_title);
        b_writer = findViewById(R.id.board_writer);
        b_content = findViewById(R.id.board_content);
        b_title.setText(intent.getStringExtra("boardtitle"));
        b_writer.setText(intent.getStringExtra("boardwriter"));
        b_content.setText(intent.getStringExtra("boardcontent"));
        

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}