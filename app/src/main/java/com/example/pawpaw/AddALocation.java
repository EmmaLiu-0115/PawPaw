package com.example.pawpaw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AddALocation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button submitButton;
    EditText reviewText;
    String typeOfLocation;
    Location location = new Location();
    String locationAddress;

    //FirebaseStorage storage;

    private final int IMAGE_REQUEST = 73;

    //Storage
    Database db = new Database(this);
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_location);

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
        submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priceAndRating = "Price is :" + price.getRating() + "; " + "Rating is :" + rating.getRating();
                //TODO: Save to database
                Toast.makeText(AddALocation.this, priceAndRating, Toast.LENGTH_LONG).show();

                reviewText = (EditText) findViewById(R.id.editTextBox);
                String reText = reviewText.getText().toString();

                location.setLocationID("1234");

                location.setLocationName("madison");
                location.setLocationType(typeOfLocation);
                location.setAvgPrice(price.getRating());
                location.setAvgRating(rating.getRating());
                location.setPhotos(new ArrayList<String>());
                location.getPhotos().add(locationAddress);

                Log.w("AAL","check point 1.0");
                db.addLocationToDB(location);
                Log.w("AAL","check point 2.0");
                //Test with get method
                db.addReviewsToDB(new Reviews("Eileen","123",price.getRating(),rating.getRating(),reText,locationAddress,"madison"));
                db.getLocationFromDB("123");
                //Log.w("AAL",Database.ll.getLocationType());
                //TEST:
                //db.addLocationToDB(location);

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
}
