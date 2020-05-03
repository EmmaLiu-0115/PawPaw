package com.example.pawpaw;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileImage extends AppCompatActivity {
    String locationAddress;
    private final int IMAGE_REQUEST = 73;
    //ImageView Uploaded;
    private static final int PICK_IMAGE_REQUEST = 1;
    Button UploadButton;
    //private Uri ImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image);
        /*Uploaded = (ImageView) findViewById(R.id.UploadedImage);
        Uploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
         */
    }
/*
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
*/
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            ImgUri = data.getData();
            Picasso.with(this).load(ImgUri).into(Uploaded);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                // Get String data from Intent
                locationAddress = data.getStringExtra("locationAddress");
                Log.i("AddALocation", "!!!"+locationAddress);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("edit_text_preference_10", locationAddress);
                editor.commit();
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
        ImageView imageView = (ImageView) findViewById(R.id.UploadedImage);
        ImageLoadAsyncTask imageLoadAsyncTask = new ImageLoadAsyncTask(uri, imageView);
        imageLoadAsyncTask.execute();
    }

    public void uploadImage(View view){
        Intent intent = new Intent(this, Image.class);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    public void clickFunction(View view) {
        //goToNext();
        Intent intent = new Intent(this, GetUserProfileLoc.class);
        startActivity(intent);
    }


    //public void goToNext (){
      //  if (locationAddress.isEmpty()) {
        //    Toast.makeText(ProfileImage.this, "Select an image", Toast.LENGTH_LONG).show();
          //  return;
           /* Intent intent = new Intent(this, GetUserProfileLoc.class);
            intent.putExtra("Image", ImgUri.toString());
            startActivity(intent);
            */

        //}
        //else{
            //Intent intent = new Intent(this, GetUserProfileLoc.class);
            //startActivity(intent);
        //}
    //}
}
