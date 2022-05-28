package com.example.runnershi_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    TextView tv_Title;
    TextView tv_Content;
    TextView tv_Id;
    EditText et_Comment;
    Button btnComment;

    private String UID;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Comment> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        tv_Title = (TextView) findViewById(R.id.detailTitle);
        tv_Content = (TextView) findViewById(R.id.detailContent);
        tv_Id = (TextView) findViewById(R.id.detailId);
        et_Comment = (EditText) findViewById(R.id.detailEditText);
        btnComment = (Button) findViewById(R.id.detailBtnComment);

        tv_Id.setText(intent.getExtras().getString("id"));
        tv_Title.setText(intent.getExtras().getString("title"));
        tv_Content.setText(intent.getExtras().getString("content"));
        String str = String.valueOf(intent.getExtras().getInt("tag"));

        switch (str) {
            case "0":
                str = "코스추천";
                break;
            case "1":
                str = "홍보";
                break;
            case "2":
                str = "자유";
                break;
            case "3":
                str = "같이뛰기";
                break;
            case "4":
                str = "거래";
                break;
        }
        UID = intent.getExtras().getString("UID");
        str = intent.getExtras().getString("key");

        recyclerView = (RecyclerView) findViewById(R.id.detailRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Comment").child("Comment" + str);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Comment comment = dataSnapshot.getValue(Comment.class);
                    arrayList.add(comment);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_Comment.getText().toString().equals(""))
                    return;

                Comment comment = new Comment(UID, et_Comment.getText().toString());
                et_Comment.setText("");
                databaseReference.push().setValue(comment);
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new CommentAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
    }
}