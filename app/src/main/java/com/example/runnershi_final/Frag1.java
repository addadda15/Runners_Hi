package com.example.runnershi_final;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Frag1 extends Fragment {

    private View view;
    private String UID;
    private TextView btnCourse, btnFree, btnPromotion, btnTogether;
    FloatingActionButton btnWrite;
    int tag0, tag1, tag2, tag3, tag4;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Post> arrayList;
    private ArrayList<String> keyList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag1, container, false);
        btnWrite = view.findViewById(R.id.f1_write);
        btnCourse = view.findViewById(R.id.btnCourse);
        btnPromotion = view.findViewById(R.id.btnPromotion);
        btnFree = view.findViewById(R.id.btnFree);
        btnTogether = view.findViewById(R.id.btnTogether);
        Intent intent = new Intent(getActivity(), WriteActivity.class);
        tag0 = -1;
        tag1 = -1;
        tag2 = -1;
        tag3 = -1;
        tag4 = -1;
        Bundle bundle = getArguments();
        UID = bundle.getString("UID");

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.f1_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        keyList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Post");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    arrayList.add(post);
                    keyList.add(dataSnapshot.getKey());

                }
                Collections.reverse(arrayList);
                Collections.reverse(keyList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("UID", UID);
                startActivityForResult(intent, 0);
            }
        });

        btnCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag0 == -1) {
                    tag0 = 0;
                    btnCourse.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tag0 = -1;
                    btnCourse.setTextColor(Color.parseColor("#000000"));
                }
                mySort();
            }
        });
        btnPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag1 == -1) {
                    tag1 = 1;
                    btnPromotion.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tag1 = -1;
                    btnPromotion.setTextColor(Color.parseColor("#000000"));
                }
                mySort();
            }
        });
        btnFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag2 == -1) {
                    tag2 = 2;
                    btnFree.setTextColor(getResources().getColor(R.color.colorAccent));

                } else {
                    tag2 = -1;
                    btnFree.setTextColor(Color.parseColor("#000000"));
                }
                mySort();
            }
        });

        btnTogether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag3 == -1) {
                    tag3 = 3;
                    btnTogether.setTextColor(getResources().getColor(R.color.colorAccent));

                } else {
                    tag3 = -1;
                    btnTogether.setTextColor(Color.parseColor("#000000"));
                }
                mySort();
            }
        });

        adapter = new PostAdapter(arrayList, getActivity());

        ((PostAdapter) adapter).setOnItemClickListener((v, position) -> {
            Intent intent1 = new Intent(getActivity(), DetailActivity.class);
            intent1.putExtra("title", arrayList.get(position).title);
            intent1.putExtra("content", arrayList.get(position).content);
            intent1.putExtra("tag", arrayList.get(position).tag);
            intent1.putExtra("id", arrayList.get(position).id);
            intent1.putExtra("key", keyList.get(position));
            intent1.putExtra("UID", UID);
            startActivityForResult(intent1, 1);
        });
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void mySort() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                if (tag0 == -1 && tag1 == -1 && tag2 == -1 && tag3 == -1 && tag4 == -1) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Post p = dataSnapshot.getValue(Post.class);
                        arrayList.add(p);
                    }
                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Post p = dataSnapshot.getValue(Post.class);
                        if (p.getTag() == tag0 || p.getTag() == tag1 || p.getTag() == tag2 || p.getTag() == tag3 || p.getTag() == tag4) {
                            arrayList.add(p);
                        }
                    }
                }
                Collections.reverse(arrayList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 || requestCode == 1) {
            databaseReference = database.getReference("Post");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mySort();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }


}
