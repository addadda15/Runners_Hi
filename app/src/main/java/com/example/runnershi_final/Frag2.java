package com.example.runnershi_final;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Frag2 extends Fragment {


    private View view;

    private RecyclerView recyclerView, recruitView;
    private RecyclerView.Adapter adapter, adapter1;
    private RecyclerView.LayoutManager layoutManager, layoutManager2;
    private ArrayList<Crew> arrayList, arrayList1;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference, dbRef2, dbRef3;
    private String userId;
    private ArrayList<String> keyList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2, container, false);


        recyclerView = view.findViewById(R.id.f2_recyclerCrew);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<>();
        keyList = new ArrayList<>();
        Bundle bundle = getArguments();
        userId = bundle.getString("UID");

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Crew");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key;
                arrayList.clear();
                keyList.clear();
                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                    key = snapshot2.getKey();
                    Crew crew = snapshot2.getValue(Crew.class);
                    getMyCrew(crew, key);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new CustomAdapter(arrayList, getContext());
        ((CustomAdapter) adapter).setOnItemClickListener((v, position) -> {
            Intent intent = new Intent(getActivity(), CrewpageActivity.class);
            String name = arrayList.get(position).name;
            intent.putExtra("crewname", name);
            intent.putExtra("userID", userId);
            startActivity(intent);

        });
        recyclerView.setAdapter(adapter);

        recruitView = view.findViewById(R.id.f2_recyclerRecruit);
        recruitView.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(getActivity());
        recruitView.setLayoutManager(layoutManager2);

        recruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CrewpageActivity.class);
                startActivity(intent);
            }
        });

        dbRef2 = database.getReference("Crew");
        dbRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList1.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Crew crew = snapshot.getValue(Crew.class);
                    arrayList1.add(crew);
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", String.valueOf(error.toException()));
            }
        });

        adapter1 = new CustomAdapter(arrayList1, getContext());
        ((CustomAdapter) adapter1).setOnItemClickListener((v, position) -> {
            Intent intent = new Intent(getActivity(), CrewpageActivity.class);
            String name = arrayList1.get(position).getName();
            intent.putExtra("crewname", name);
            intent.putExtra("userID", userId);
            startActivity(intent);
        });

        recruitView.setAdapter(adapter1);

        AppCompatButton ImageViewCreateCrew = (AppCompatButton) view.findViewById(R.id.btnCreateCrew);
        ImageViewCreateCrew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewCrewActivity.class);
                intent.putExtra("userID", userId);
                startActivity(intent);
            }
        });

        return view;
    }


    private void getMyCrew(Crew crew, String key) {

        databaseReference = database.getReference("Crew").child(key).child("member");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String str = snapshot.getValue(String.class);
                    if (str.equals(userId)) {
                        if (!keyList.contains(key)) {
                            arrayList.add(crew);
                            keyList.add(key);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}