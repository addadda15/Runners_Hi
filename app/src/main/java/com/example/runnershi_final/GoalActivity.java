package com.example.runnershi_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GoalActivity extends AppCompatActivity {
    TextView tv_km, tv_accomplish, tv_thanks;
    String UID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int goal;
    double dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        Intent intent = getIntent();
        UID = intent.getStringExtra("UID");
        tv_km = (TextView) findViewById(R.id.tv_km);
        tv_accomplish = (TextView) findViewById(R.id.tv_accomplish);
        tv_thanks = (TextView) findViewById(R.id.tv_thanks);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    LoginMember loginMember = d.getValue(LoginMember.class);
                    if (loginMember.id.equals(UID)) {
                        dis = loginMember.dis;
                        goal = loginMember.goal;
                        setText();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @SuppressLint("DefaultLocale")
    private void setText() {
        if (dis < goal) {
            String str = String.format("%.2f", goal - dis) + " Km";
            tv_km.setText(str);
            tv_accomplish.setText("남았습니다");
            tv_thanks.setText("아자아자 화이팅!!");
        } else {
            String str = String.format("%.2f", dis - goal) + " Km";
            tv_km.setText(str);
            tv_accomplish.setText("넘었습니다");
            tv_thanks.setText("축하합니다!!");
        }
    }
}