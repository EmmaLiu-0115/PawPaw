package com.example.pawpaw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddALocation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button submitButton;
    EditText reviewText;
    String typeOfLocation;

    //Storage
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //FirebaseStorage storage = FirebaseStorage.getInstance();

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



                //private String locationID;
                //    private String locationName;
                //    private String locationType;
                //    private String locationAddress;
                //    private double avgPrice;
                //    private double avgRating;
                //    private List<String> photos;
                List<String> photos = new ArrayList<>();
                Location location = new Location("latitude+longitude","location name", typeOfLocation,
                        "location address", price.getRating(),rating.getRating(),photos);


                db.collection("locations").document("latitude+longitude").set(location);

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
}
