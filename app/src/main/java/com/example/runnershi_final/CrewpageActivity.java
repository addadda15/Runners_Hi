package com.example.runnershi_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CrewpageActivity extends AppCompatActivity {

    Button btn_joinexit;
    FloatingActionButton btn_chat;
    TextView tv_Crew, tv_Intro;
    RecyclerView rv_member;
    FirebaseDatabase database;
    DatabaseReference dbRef, dbRefMem;
    LinearLayoutManager linearLayoutManager;
    ArrayList<String> arrayList;
    MemberAdapter memberAdapter;
    String name, intro, leaderID;
    String UID;
    int member_check = 0;
    String member_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crewpage);

        Intent intent = getIntent();
        name = intent.getStringExtra("crewname");
        UID = intent.getStringExtra("userID");
        tv_Intro = findViewById(R.id.tv_Intro);
        tv_Crew = findViewById(R.id.tv_crew);
        tv_Crew.setText(name);

        btn_chat = (FloatingActionButton)findViewById(R.id.btn_chat);
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplication(), ChatActivity.class);
                intent1.putExtra("UID",UID);
                intent1.putExtra("crewName",name);
                startActivity(intent1);
            }
        });

        btn_joinexit = findViewById(R.id.btn_joinexit);
        btn_joinexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leaderID.equals(UID)) {
                    dbRef.removeValue();
                    finish();
                } else if (member_check == 1) {
                    dbRef.child("member").child(member_key).removeValue();
                    member_check = 0;
                    btn_joinexit.setText("가입");
                    btn_chat.setVisibility(View.GONE);
                } else {
                    member_key = dbRef.child("member").push().getKey();
                    dbRef.child("member").child(member_key).setValue(UID);
                    member_check = 1;
                    btn_joinexit.setText("탈퇴");
                    btn_chat.setVisibility(View.VISIBLE);
                }
            }
        });

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("Crew").child(name);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Crew crew = snapshot.getValue(Crew.class);
                leaderID = crew.getLeaderID();
                intro = crew.getIntro();
                tv_Intro.setText(intro);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        rv_member = (RecyclerView) findViewById(R.id.rv_member);
        linearLayoutManager = new LinearLayoutManager(this);
        rv_member.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        dbRefMem = database.getReference("Crew").child(name).child("member");
        dbRefMem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String member = snapshot.getValue().toString();
                    arrayList.add(member);
                }
                memberAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        memberAdapter = new MemberAdapter(arrayList);
        rv_member.setAdapter(memberAdapter);


        dbRef.child("member").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                    if (snapshot2.getValue().equals(UID)) {
                        member_check = 1;
                        btn_joinexit.setText("탈퇴");
                        member_key = snapshot2.getKey();
                        btn_chat.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}