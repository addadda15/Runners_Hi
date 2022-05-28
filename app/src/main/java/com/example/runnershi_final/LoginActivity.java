package com.example.runnershi_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn, registerBtn;
    TextInputLayout id, password;
    boolean flag;
    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.login_email);
        password = findViewById(R.id.login_pw);

        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        loginBtn.setOnClickListener((this::loginUser));

        registerBtn.setOnClickListener((view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        }));
    }

    private Boolean validateStudentID() {
        String val = id.getEditText().getText().toString();

        if (val.isEmpty()) {
            id.setError("Field cannot be empty");
            return false;
        } else {
            id.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {
        if (!validateStudentID() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        flag = false;
        final String userEnteredStudentID = id.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();
        LoginMember loginUser = new LoginMember(userEnteredStudentID, userEnteredPassword);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    LoginMember loginMember = dataSnapshot.getValue(LoginMember.class);
                    if (loginUser.id.equals(loginMember.id)) {
                        if (loginUser.pw.equals(loginMember.pw)) {
                            UID = loginUser.id;
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("UID",UID);
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}