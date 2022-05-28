package com.example.runnershi_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag0 frag0;
    private Frag1 frag1;
    private Frag2 frag2;
    private Frag3 frag3;
    private String UID;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Intent getIntent = getIntent();
//        UID = getIntent.getExtras().getString("UID");
        UID = "준";
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_run:
                        setFrag(0);
                        break;
                    case R.id.menu_community:
                        setFrag(1);
                        break;
                    case R.id.menu_crew:
                        setFrag(2);
                        break;
                    case R.id.menu_my:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });

        frag0 = new Frag0();
        frag1 = new Frag1();
        frag2 = new Frag2();
        frag3 = new Frag3();
        setFrag(0);
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            backPressedTime = tempTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        }

    }

    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("UID", UID);

        switch (n) {
            case 0:
                ft.replace(R.id.fragment_frame, frag0);
                ft.commit();
                frag0.setArguments(bundle);
                break;
            case 1:
                ft.replace(R.id.fragment_frame, frag1);
                ft.commit();
                frag1.setArguments(bundle);
                break;
            case 2:
                ft.replace(R.id.fragment_frame, frag2);
                ft.commit();
                frag2.setArguments(bundle);
                break;
            case 3:
                ft.replace(R.id.fragment_frame, frag3);
                ft.commit();
                frag3.setArguments(bundle);
                break;
        }
    }
}