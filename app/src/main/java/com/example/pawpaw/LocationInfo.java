package com.example.pawpaw;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class LocationInfo extends AppCompatActivity {

    HorizontalScrollView imageview;

    Button Backtomap;
    String LocationID;
    TextView locationInfoName;
    TextView locationInfoType;
    TextView locationInfoRating;
    TextView locationInfoPrice;

    RatingBar ratingbar;
    RatingBar ratingbar2;

    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);


        Bundle bundle = getIntent().getExtras();
        LocationID = bundle.getString("LocationID");

        Log.w("LocationID", LocationID);

        locationInfoName= findViewById(R.id.location_name);
        locationInfoPrice = findViewById(R.id.location_price);
        locationInfoType = findViewById(R.id.location_type);
        locationInfoRating = findViewById(R.id.location_rating);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("locations").document(LocationID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Location location = documentSnapshot.toObject(Location.class);
                Log.w("LocationName", location.getLocationName());
                //ll.setLocationType(location.getLocationType());

                //aal.getLocationData(location);
                //TODO: Call the function which uses location info from the other class
                locationInfoName.setText(location.getLocationName());

                locationInfoType.setText("Type: "+location.getLocationType());
                locationInfoPrice.setText("Price: "+String.valueOf(location.getAvgPrice()));
                locationInfoRating.setText("Rating: "+String.valueOf(location.getAvgRating()));

                ratingbar=(RatingBar)findViewById(R.id.ratingBar);
                ratingbar.setRating((float) location.getAvgRating());
                ratingbar2 = (RatingBar)findViewById(R.id.ratingBar2);
                ratingbar2.setRating((float) location.getAvgPrice());

                imageview = (HorizontalScrollView) findViewById(R.id.imageview);


                List<String> PhotoAddress = new ArrayList<>();
                for(int i = 0; i<location.getPhotos().size(); i++){
                    PhotoAddress.add(location.getPhotos().get(i));
                }

                //Display image
                StorageReference storageRef = storage.getReference();

                for(int i=0; i<PhotoAddress.size(); i++){
                    final int finalI = i;
                    storageRef.child(PhotoAddress.get(i)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            helper(uri.toString(), finalI);
                            // Got the download URL for 'users/me/profile.png'
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }

                String id = location.getLocationID();
                Log.d("LocationInfo", id);
            }
        });

        Backtomap = (Button) findViewById(R.id.BacktoMap);
        Backtomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LocationInfo.this, MapHomePage.class));
            }

        });
    }

    //Helper method to display image
    private void helper(String uri, int imageId){
        ImageView image;
        if (imageId == 0){
            image = findViewById(R.id.photo1);
            ImageLoadAsyncTask imageLoadAsyncTask = new ImageLoadAsyncTask(uri, image);
            imageLoadAsyncTask.execute();
        } else if (imageId == 1) {
            image = findViewById(R.id.photo2);
            ImageLoadAsyncTask imageLoadAsyncTask = new ImageLoadAsyncTask(uri, image);
            imageLoadAsyncTask.execute();
        } else if (imageId == 2){
            image = findViewById(R.id.photo3);
            ImageLoadAsyncTask imageLoadAsyncTask = new ImageLoadAsyncTask(uri, image);
            imageLoadAsyncTask.execute();
        } else if (imageId == 3){
            image = findViewById(R.id.photo4);
            ImageLoadAsyncTask imageLoadAsyncTask = new ImageLoadAsyncTask(uri, image);
            imageLoadAsyncTask.execute();
        }

    }
}