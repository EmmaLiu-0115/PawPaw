package com.example.pawpaw;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;


public class LocationInfo extends AppCompatActivity {

    HorizontalScrollView imageview;

    List<Reviews> locationImages = new ArrayList<>(); //imageview
    Button Backtomap;
    String LocationID;
    TextView locationInfoName;
    TextView locationInfoType;
    TextView locationInfoRating;
    TextView locationInfoPrice;

//    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);


        Bundle bundle = getIntent().getExtras();
        LocationID = bundle.getString("LocationID");

        Log.w("LocationID", LocationID);

        Backtomap = (Button) findViewById(R.id.BacktoMap);
        Backtomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LocationInfo.this, MapHomePage.class));
            }

        });
    }
}