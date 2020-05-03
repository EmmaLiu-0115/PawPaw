package com.example.pawpaw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddALocation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button submitButton;
    EditText reviewText;
    String typeOfLocation;
    Location location = new Location();
    String locationAddress;
    String userID;
    String reText;

    TextView locationNameContent;
    TextView locationAddressContent;


    private final int IMAGE_REQUEST = 73;

    //Storage
    Database db = new Database(this);
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_location);

        locationNameContent = (TextView) findViewById(R.id.location_name_content);
        locationAddressContent = (TextView) findViewById(R.id.location_address_content);

        //Choose type of location
        Spinner spinner = (Spinner) findViewById(R.id.type_of_location);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_location, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Price and rating
        final RatingBar price = (RatingBar) findViewById(R.id.price_content);
        final RatingBar rating = (RatingBar) findViewById(R.id.rating_content);


        Bundle bundle = getIntent().getParcelableExtra("bundle");
        LatLng currentLocation = bundle.getParcelable("currentLocation");
        //get userID from intent
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AddALocation.this);
        //userID = preferences.getString("edit_text_preference_3", "Eileen");

        userID = "Eileen";

        location.setLocationID(String.valueOf(currentLocation.latitude)+","+String.valueOf(currentLocation.longitude));
        location.setLatitude(currentLocation.latitude);
        location.setLongitude(currentLocation.longitude);

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(currentLocation.latitude, currentLocation.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();; // Only if available else return NULL
            if (knownName != null) {
                locationNameContent.setText(knownName);
                location.setLocationName(knownName);
            } else {
                locationNameContent.setText(address);
                location.setLocationName(address);
            }
            locationAddressContent.setText(address);
            location.setLocationAddress(address);

        } catch (IOException e) {
            e.printStackTrace();
        }

        submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priceAndRating = "Price is :" + price.getRating() + "; " + "Rating is :" + rating.getRating();
                //TODO: Save to database
                Toast.makeText(AddALocation.this, priceAndRating, Toast.LENGTH_LONG).show();

                reviewText = (EditText) findViewById(R.id.editTextBox);
                reText = reviewText.getText().toString();

                //Add location to database
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                DocumentReference ref = database.collection("locations").document(location.getLocationID());

                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Location existLocation = document.toObject(Location.class);
                            if (document.exists()) {
                                db.updateLocationPhotosInDB(location.getLocationID(),locationAddress);
                                db.updateLocationInDB(location.getLocationID(), "avgPrice", ((existLocation.getAvgPrice()+price.getRating())/2));
                                db.updateLocationInDB(location.getLocationID(), "avgRating", ((existLocation.getAvgRating()+rating.getRating())/2));
                                db.updatereviewedUsersInDB(location.getLocationID(),userID);
                                db.updatereviewedUsersContentInDB(location.getLocationID(),reText);
                                Log.d("AddALocation", "Update exist document to: " + document.getData());
                            } else {
                                location.setLocationType(typeOfLocation);
                                location.setPhotos(new ArrayList<String>());
                                location.getPhotos().add(locationAddress);
                                location.setAvgPrice(price.getRating());
                                location.setAvgRating(rating.getRating());
                                db.addLocationToDB(location);
                                location.setReviewedUsers(new ArrayList<String>());
                                location.getReviewedUsers().add(userID);
                                location.setReviewedUsersContent(new ArrayList<String>());
                                location.getReviewedUsersContent().add(reText);
                                Log.d("AddALocation", "Create a new location");
                            }
                        } else {
                            Log.d("AddALocation", "get failed with ", task.getException());
                        }
                    }
                });



                db.addReviewsToDB(new Reviews(userID,location.getLocationID(),price.getRating(),rating.getRating(),reText,locationAddress,location.getLocationName()));
                //db.getLocationFromDB("123");
                //Log.w("AAL",Database.ll.getLocationType());
                //TEST:
                //db.addLocationToDB(location);

                startActivity(new Intent(AddALocation.this,MapHomePage.class));

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        typeOfLocation = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),typeOfLocation,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    public void uploadImage(View view){
        Intent intent = new Intent(this, Image.class);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {

                // Get String data from Intent
                locationAddress = data.getStringExtra("locationAddress");
                Log.i("AddALocation", "!!!"+locationAddress);

                //Display image
                //FirebaseStorage storage = FirebaseStorage.getInstance(); (I have declared it at the beginning of the class)
                StorageReference storageRef = storage.getReference();

                storageRef.child(locationAddress).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        helper(uri.toString());
                        // Got the download URL for 'users/me/profile.png'
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }
        }
    }

    //Helper method to display image
    private void helper(String uri){
        ImageView imageView = (ImageView) findViewById(R.id.imageView4);
        ImageLoadAsyncTask imageLoadAsyncTask = new ImageLoadAsyncTask(uri, imageView);
        imageLoadAsyncTask.execute();
    }

    //Helper function for database's getLocationFromDb
    public void getLocationData(Location ll){
        Log.i("Shawn", "get Location worked");
        if (ll.getLocationID().equals(location.getLocationID())){
            Log.i("TAG", "get Location worked and equal");
            db.updateLocationPhotosInDB("123",locationAddress);
            db.updateLocationInDB(location.getLocationID(), "avgPrice", (ll.getAvgPrice()+location.getAvgPrice())/2);
            db.updateLocationInDB(location.getLocationID(), "avgRating", (ll.getAvgRating()+location.getAvgRating())/2);
        } else {
            db.addLocationToDB(location);
        }
    }

    public void gotoMap(View view){
        startActivity(new Intent(AddALocation.this,MapHomePage.class));
    }
}
