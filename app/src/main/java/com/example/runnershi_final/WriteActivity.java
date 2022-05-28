package com.example.runnershi_final;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WriteActivity extends AppCompatActivity {

    private TextView btnCourse, btnFree, btnPromotion, btnTogether;
    private EditText tv_title;
    private EditText tv_content;
    private int tag;
    private String title;
    private String content;
    private String UID;
    private Post post;
    private Button btnWrite, btnGetCourse;
    private PopupMenu popup;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    int tag0, tag1, tag2, tag3, tag4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Intent getIntent = getIntent();
        UID = getIntent.getExtras().getString("UID");
        tag = 2;

        tag0 = -1;
        tag1 = -1;
        tag2 = -1;
        tag3 = -1;
        tag4 = -1;

        btnCourse = findViewById(R.id.tvCourse);
        btnPromotion = findViewById(R.id.tvPromotion);
        btnFree = findViewById(R.id.tvFree);
        btnTogether = findViewById(R.id.tvTogether);

        tv_title = (EditText) findViewById(R.id.postWriteTitle);
        tv_content = (EditText) findViewById(R.id.postWriteContent);
        btnWrite = (Button) findViewById(R.id.btnPostWrite);
        btnGetCourse = (Button) findViewById(R.id.btnGetCourse);

        btnCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag0 == -1) {
                    tag0 = 0;
                    btnCourse.setTextColor(getResources().getColor(R.color.colorAccent));
                    tag = 0;
                } else {
                    tag0 = -1;
                    btnCourse.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        btnPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag1 == -1) {
                    tag1 = 1;
                    btnPromotion.setTextColor(getResources().getColor(R.color.colorAccent));
                    tag = 1;

                } else {
                    tag1 = -1;
                    btnPromotion.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        btnFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag2 == -1) {
                    tag2 = 2;
                    btnFree.setTextColor(getResources().getColor(R.color.colorAccent));
                    tag = 2;

                } else {
                    tag2 = -1;
                    btnFree.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        btnTogether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag3 == -1) {
                    tag3 = 3;
                    btnTogether.setTextColor(getResources().getColor(R.color.colorAccent));
                    tag = 3;

                } else {
                    tag3 = -1;
                    btnTogether.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Post");

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = tv_title.getText().toString();
                content = tv_content.getText().toString();
                post = new Post(title, content, UID, tag);
                databaseReference.push().setValue(post);
                setResult(0);
                finish();
            }
        });

    }
}