package com.example.runnershi_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NewCrewActivity extends AppCompatActivity {
    private FirebaseDatabase mdatabase;
    private DatabaseReference mDbRef,mDbRefMem;

    EditText EditText_CrewName, EditText_CrewIntro;
    String userId;
    Button Button_Create_NewCrew;
    ImageView Button_Back_NewCrew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcrew);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userID");
        mdatabase = FirebaseDatabase.getInstance();
        mDbRef = mdatabase.getReference("Crew");
        //mDbRefMem = mdatabase.getReference("com.example.crewpage.Member").child(userId);

        EditText_CrewName = findViewById(R.id.EditText_CrewName);
        EditText_CrewIntro = findViewById(R.id.EditText_CrewIntro);
        Button_Create_NewCrew = findViewById(R.id.Button_Create_NewCrew);

        Button_Create_NewCrew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = EditText_CrewName.getText().toString();
                String strIntro = EditText_CrewIntro.getText().toString();
                String recruit = "1";
                writeNewUser(userId, strName, strIntro, recruit,userId);
                finish();
            }
            public void writeNewUser(String userID, String strName, String strIntro, String recruit,String leaderID) {
                Crew crew = new Crew(strName, strIntro, recruit,leaderID);
                mDbRef.child(String.valueOf(crew.name)).setValue(crew);
                mDbRef.child(String.valueOf(crew.name)).child("member").push().setValue(leaderID);
            }
        });


    }

}