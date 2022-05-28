package com.example.runnershi_final;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button loginBtn, registerBtn;
    TextInputLayout regID, regPassword, regPasswordCheck, regGoal;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regID = findViewById(R.id.reg_id);
        regPassword = findViewById(R.id.reg_pw);
        regPasswordCheck = findViewById(R.id.reg_pwCheck);
        regGoal = findViewById(R.id.reg_goal);

        loginBtn = findViewById(R.id.reg_loginBtn);
        registerBtn = findViewById(R.id.reg_registerBtn);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("User");

        loginBtn.setOnClickListener((view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }));

        registerBtn.setOnClickListener((view -> {
            register();
        }));
    }

    private void register() {
        if (!validatePassword() || !validatePasswordCheck() || !validateStudentID()) {
            return;
        }

        String password = regPassword.getEditText().getText().toString();
        String id = regID.getEditText().getText().toString();
        int goal = Integer.parseInt(regGoal.getEditText().getText().toString());

        LoginMember loginMember = new LoginMember(id, password, goal);

        databaseReference.child(id).setValue(loginMember);
        Toast.makeText(this, "Your Account has been created successfully!", Toast.LENGTH_SHORT).show();

        finish();
    }

    private Boolean validateStudentID() {
        String val = regID.getEditText().getText().toString();

        if (val.isEmpty()) {
            regID.setError("Field cannot be empty");
            return false;
        } else {
            regID.setError(null);
            return true;
        }
    }


    private Boolean validatePasswordCheck() {
        String val = regPasswordCheck.getEditText().getText().toString();
        String val1 = regPassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPasswordCheck.setError("Field cannot be empty");
            return false;
        } else if (!val1.equals(val)) {
            regPasswordCheck.setError("Password isn't same");
            return false;
        } else {
            regPasswordCheck.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else {
            regPassword.setError(null);
            return true;
        }
    }

}