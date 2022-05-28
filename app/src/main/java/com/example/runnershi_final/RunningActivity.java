package com.example.runnershi_final;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RunningActivity extends AppCompatActivity {
    Button btnStop;
    TextView mTimeTextView, text_km, text_cal;
    Boolean isRunning = true;
    Thread timeThread;
    Message msg;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference, reference1,reference2;
    ArrayList<Double> latList, lngList;
    LocationManager locationManager;
    Location location;
    double distance;
    int tmp, cnt;
    String UID;
    int time;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        Intent intent = getIntent();
        UID = intent.getStringExtra("UID");
        btnStop = (Button) findViewById(R.id.btn_stop);
        mTimeTextView = (TextView) findViewById(R.id.tv_lap);
        text_km = (TextView) findViewById(R.id.text_km);
        text_cal = (TextView) findViewById(R.id.text_cal);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Course").child(UID);
        reference1 = firebaseDatabase.getReference("User").child(UID).child("dis");
        reference2 = firebaseDatabase.getReference("User").child(UID).child("time");

        latList = new ArrayList<>();
        lngList = new ArrayList<>();
        distance = 0;
        time = 0;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                latList.add(location.getLatitude());
                lngList.add(location.getLongitude());
            }
            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }
            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };

        location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 2000, 3, locationListener);

        timeThread = new Thread(new timeThread());
        timeThread.start();
        tmp = 0;
        cnt = 0;
        btnStop.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                reference.child("lat").push().setValue(latList);
                reference.child("long").push().setValue(lngList);

                ArrayList<LoginMember> aList = new ArrayList<>();
                DatabaseReference ref = firebaseDatabase.getReference("User");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        aList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            LoginMember loginMember = dataSnapshot.getValue(LoginMember.class);
                            if (loginMember.id.equals(UID)) {
                                distance += loginMember.dis;
                                time += loginMember.time;
                                reference1.setValue(Double.parseDouble(String.format("%.2f", distance)));
                                reference2.setValue(time);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @SuppressLint("MissingPermission")
        @Override
        public void handleMessage(Message msg) {
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
            time = (min * 60) + sec;

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d", min, sec);
            if (sec % 1 == 0) {
                if (sec != tmp) {
                    @SuppressLint("DefaultLocale") String str1 = String.format("%.2f", distance);
                    @SuppressLint("DefaultLocale") String str2 = String.format("%.0f", distance * 49);
                    text_km.setText(str1);
                    text_cal.setText(str2);

                    if (latList.size() > 1) {
                        if (cnt != latList.size()) {
                            Location tmpLoc = new Location("tmp");
                            tmpLoc.setLatitude(latList.get(latList.size() - 1));
                            tmpLoc.setLongitude(lngList.get(lngList.size() - 1));
                            distance += location.distanceTo(tmpLoc) / 1000;
                            location = tmpLoc;
                            cnt = latList.size();
                        }

                    }
                }
            }
            mTimeTextView.setText(result);


        }
    };

    public class timeThread implements Runnable {
        @Override
        public void run() {
            int i = 0;

            while (true) {
                while (isRunning) { //일시정지를 누르면 멈춤
                    msg = new Message();
                    msg.arg1 = i++;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                        return;
                    }
                }
            }
        }
    }
}