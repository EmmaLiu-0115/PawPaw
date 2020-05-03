package com.example.pawpaw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class AccountPage extends AppCompatActivity {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Database database = new Database(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = prefs.edit();
        //database.
        String phone = prefs.getString("edit_text_preference_3",  "");
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firebaseFirestore.collection("users").document(phone);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = prefs.edit();
                String name = user.getName();
                String State = user.getArea();
                String Description = user.getIntro();
                String Image = user.getImage();
                editor.putString("edit_text_preference_6", State);
                editor.putString("edit_text_preference_4", name);
                editor.putString("edit_text_preference_5", Description);
                editor.putString("edit_text_preference_10", Image);
                //TODO: Call the function which uses user info from the other class
            }
        });

        //Now I need to update the database

        database.getUserFromDB(phone);
        editor.commit();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);
        boolean privacy = prefs.getBoolean("switch_preference_1", true);
        String name = prefs.getString("edit_text_preference_4",  "");
        String description = prefs.getString("edit_text_preference_5",  "");
        String location = prefs.getString("edit_text_preference_6",  "");
        String Img = prefs.getString("edit_text_preference_10", "");
        String locationAddress = Img;
        Bundle bundle = getIntent().getExtras();
        NameText = (TextView) findViewById(R.id.profile_name);
        PhoneText = (TextView) findViewById(R.id.phone);
        DescriptionText = (TextView) findViewById(R.id.info);
        Location = (TextView) findViewById(R.id.location);
        StorageReference storageReference = storage.getReference();
        storageReference.child(locationAddress).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
        NameText.setText(name);
        PhoneText.setText(phone);
        DescriptionText.setText(description);
        Location.setText(location);
        //Picasso.with(this).load(MyImg).into(ProfilePic);
    }
    private void helper(String uri){
        View view1 = getLayoutInflater().inflate(R.layout.row_data,null);
        //ImageView image = view1.findViewById(R.id.images);
        ImageView image = findViewById(R.id.ProfilePic);
        ImageLoadAsyncTask imageLoadAsyncTask = new ImageLoadAsyncTask(uri, image);
        imageLoadAsyncTask.execute();
    }

    public void clickFunction (View view){
        goToActivityEdit();
    }
    public void goToActivityEdit (){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void clickFunction2 (View view){
        Intent intent = new Intent(this, AddALocation.class);
        startActivity(intent);
    }

    public void clickFunction3 (View view){
        Intent intent = new Intent(this, AddFriend.class);
        startActivity(intent);
    }

    public void clickFunction4 (View view){
        Intent intent = new Intent(this, FriendList.class);
        startActivity(intent);
    }

    public void clickFunction5 (View view){
        Intent intent = new Intent(this, MapHomePage.class);
        startActivity(intent);
    }

    TextView NameText;
    TextView PhoneText;
    TextView DescriptionText;
    TextView Location;
    //de.hdodenhof.circleimageview.CircleImageView ProfilePic;
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean privacy = prefs.getBoolean("switch_preference_1", true);
        String name = prefs.getString("edit_text_preference_4",  "");
        String phone = prefs.getString("edit_text_preference_3",  "");
        String description = prefs.getString("edit_text_preference_5",  "");
        String location = prefs.getString("edit_text_preference_6",  "");

        //TODO update db
        Database database = new Database(this);
        database.updateUserInDB(phone, "name", name);
        database.updateUserInDB(phone, "area", location);
        database.updateUserInDB(phone, "intro", description);
        NameText = (TextView) findViewById(R.id.profile_name);
        PhoneText = (TextView) findViewById(R.id.phone);
        DescriptionText = (TextView) findViewById(R.id.info);
        Location = (TextView) findViewById(R.id.location);
        NameText.setText(name);
        PhoneText.setText(phone);
        DescriptionText.setText(description);
        Location.setText(location);


        //REMEMBER TO UPDATE THE SERVER AFTER UPDATING SETTINGS
    }




    public void getUserData(User user){

    }

}
