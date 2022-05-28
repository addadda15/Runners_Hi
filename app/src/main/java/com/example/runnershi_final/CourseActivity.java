package com.example.runnershi_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> arrayList,arrayList1;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,databaseReference1;
    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Intent intent = getIntent();
        UID = intent.getStringExtra("UID");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_course);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Course").child(UID).child("lat");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot d : snapshot.getChildren()) {
                    arrayList.add(d.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference1 = firebaseDatabase.getReference("Course").child(UID).child("long");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList1.clear();
                for (DataSnapshot d : snapshot.getChildren()) {
                    arrayList1.add(d.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new CourseAdapter(arrayList, this);
        ((CourseAdapter) adapter).setOnItemClickListener((v, position) -> {
            Intent intent1 = new Intent(this, GetCourseActivity.class);
            intent1.putExtra("UID", UID);
            intent1.putExtra("keyLat",arrayList.get(position));
            intent1.putExtra("keyLng",arrayList1.get(position));
            startActivity(intent1);
        });

        recyclerView.setAdapter(adapter);
    }
}