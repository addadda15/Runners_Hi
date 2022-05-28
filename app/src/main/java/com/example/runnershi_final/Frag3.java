package com.example.runnershi_final;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Frag3 extends Fragment {

    EditText myAge, myHeight, myWeight,et_goal;
    TextView myID, myDis, myTime, myHistory, myMaxim, myGoal;
    Button btnRevise, btnLogout;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String UID;
    LoginMember user;
    private View view;
    ImageView iv_profile;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3, container, false);

        Bundle bundle = getArguments();
        UID = bundle.getString("UID");

        myAge = (EditText) view.findViewById(R.id.myAge);
        myHeight = (EditText) view.findViewById(R.id.myHeight);
        myWeight = (EditText) view.findViewById(R.id.myWeight);
        et_goal = (EditText) view.findViewById(R.id.Et_goal);
        myID = (TextView) view.findViewById(R.id.myID);
        myDis = (TextView) view.findViewById(R.id.myDis);
        myTime = (TextView) view.findViewById(R.id.myTime);
        myHistory = (TextView) view.findViewById(R.id.myHistory);
        myMaxim = (TextView) view.findViewById(R.id.myMaxim);
        myGoal = (TextView) view.findViewById(R.id.myGoal);
        btnRevise = (Button) view.findViewById(R.id.btnMyRevise);
        btnLogout = (Button) view.findViewById(R.id.btnLogout);

        iv_profile = (ImageView) view.findViewById(R.id.myProfile);
        GradientDrawable drawable = (GradientDrawable) getContext().getDrawable(R.drawable.round_background);
        iv_profile.setBackground(drawable);
        iv_profile.setClipToOutline(true);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    LoginMember loginMember = dataSnapshot.getValue(LoginMember.class);
                    if(loginMember.id.equals(UID)) {
                        user = loginMember;
                        String id = "ID : " + user.id;
                        myID.setText(id);
                        myHeight.setText(String.valueOf(user.height));
                        myWeight.setText(String.valueOf(user.weight));
                        myAge.setText(String.valueOf(user.age));
                        et_goal.setText(String.valueOf(user.goal));
                        String dis = user.dis + " Km";
                        String time = (user.time/60) + " Min";

                        myDis.setText(dis);
                        myTime.setText(time);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnRevise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child(UID).child("age").setValue(Integer.parseInt(myAge.getText().toString()));
                databaseReference.child(UID).child("weight").setValue(Integer.parseInt(myWeight.getText().toString()));
                databaseReference.child(UID).child("height").setValue(Integer.parseInt(myHeight.getText().toString()));
                databaseReference.child(UID).child("goal").setValue(Integer.parseInt(et_goal.getText().toString()));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        myHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CourseActivity.class);
                intent.putExtra("UID",UID);
                startActivity(intent);
            }
        });
        myMaxim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MaximActivity.class);
                startActivity(intent);
            }
        });

        myGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),GoalActivity.class);
                intent.putExtra("UID",UID);
                startActivity(intent);
            }
        });
        return view;
    }
}
