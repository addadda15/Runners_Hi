package com.example.runnershi_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private EditText et_msg;
    private Button btn_send;
    private String UID,crewName;
    private ArrayList<Chat> cList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyvlerv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        UID = intent.getStringExtra("UID");
        crewName = intent.getStringExtra("crewName");
        et_msg = findViewById(R.id.editText1);
        btn_send = findViewById(R.id.btn_send);
        cList = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Chat").child(crewName);


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = et_msg.getText().toString();
                if(!msg.equals("")){
                    et_msg.setText("");
                    Chat tmpChat = new Chat(UID,msg);
                    databaseReference.push().setValue(tmpChat);
                }

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if(chat.getId().equals(UID)){
                        chat.setType(1);
                    }
                    cList.add(chat);
                }
                adapter.notifyDataSetChanged();
                if(cList.size()>1)
                    recyvlerv.scrollToPosition(cList.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyvlerv = findViewById(R.id.recyvlerv);
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyvlerv.setLayoutManager(manager);

        adapter = new ChatAdapter(cList);
        recyvlerv.setAdapter(adapter);

        if(cList.size()>1)
            recyvlerv.scrollToPosition(cList.size()-1);
    }

}