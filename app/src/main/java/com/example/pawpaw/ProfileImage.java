package com.example.pawpaw;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ProfileImage extends AppCompatActivity {
    ImageView Uploaded;
    private static final int PICK_IMAGE_REQUEST = 1;
    Button UploadButton;
    private Uri ImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image);
        Uploaded = (ImageView) findViewById(R.id.UploadedImage);
        Uploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            ImgUri = data.getData();
            Picasso.with(this).load(ImgUri).into(Uploaded);
        }
    }

    public void clickFunction(View view) {
        goToNext();
    }


    public void goToNext (){
        if (ImgUri != null) {
           /* Intent intent = new Intent(this, GetUserProfileLoc.class);
            intent.putExtra("Image", ImgUri.toString());
            startActivity(intent);
            */
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("edit_text_preference_10",ImgUri.toString());
            editor.commit();
            Intent intent = new Intent(this, GetUserProfileLoc.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(ProfileImage.this, "Select an image", Toast.LENGTH_LONG).show();
            return;
        }
    }
}
