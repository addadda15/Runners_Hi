package com.example.runnershi_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GetCourseActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String UID;
    private String keyLat, keyLng;
    private FirebaseDatabase database;
    private DatabaseReference databaseReferenceLat;
    private DatabaseReference databaseReferenceLng;
    private ArrayList<Double> latList, lngList;
    private ArrayList<LatLng> cList;

    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_course);
        Intent intent = getIntent();
        UID = intent.getStringExtra("UID");
        keyLat = intent.getStringExtra("keyLat");
        keyLng = intent.getStringExtra("keyLng");
        latList = new ArrayList<>();
        lngList = new ArrayList<>();
        cList = new ArrayList<>();
        flag = false;

        database = FirebaseDatabase.getInstance();
        databaseReferenceLat = database.getReference("Course").child(UID).child("lat").child(keyLat);
        databaseReferenceLng = database.getReference("Course").child(UID).child("long").child(keyLng);

        databaseReferenceLat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Double lat = dataSnapshot.getValue(Double.class);
                    latList.add(lat);
                }
                if (flag)
                    addList();
                flag = true;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReferenceLng.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Double lng = dataSnapshot.getValue(Double.class);
                    lngList.add(lng);
                }
                if (flag)
                    addList();
                flag = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void addList() {
        int size = latList.size();
        for (int i = 0; i < size; i++) {
            cList.add(new LatLng(latList.get(i),lngList.get(i)));
        }
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.getCourse_map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .addAll(cList));
        polyline1.setColor(getResources().getColor(R.color.colorAccent));
        polyline1.setWidth(14);
        if (cList.size() > 1) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(((cList.get(0).latitude + cList.get(cList.size() - 1).latitude) / 2), ((cList.get(0).longitude + cList.get(cList.size() - 1).longitude) / 2)), 17));
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(cList.get(0).latitude, cList.get(0).longitude), 17));
        }

    }
}