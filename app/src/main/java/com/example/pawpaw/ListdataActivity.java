package com.example.pawpaw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ListdataActivity extends AppCompatActivity {
    TextView name;
    ImageView image;
    Button back;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdata);

        name = findViewById(R.id.listdata);
        image = findViewById(R.id.imageView);
        back = findViewById(R.id.button3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListdataActivity.this, ListMainActivity.class));
            }
        });


        Intent intent = getIntent();

        name.setText(intent.getStringExtra("name"));

        //image.setImageResource(intent.getIntExtra("image", 0));
        // Get String data from Intent
        String locationAddress = intent.getStringExtra("image");
        Log.w("ListdataActivity",locationAddress);

        //Display image
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

    //Helper method to display image
    private void helper(String uri){
        View view1 = getLayoutInflater().inflate(R.layout.row_data,null);
        ImageView image = findViewById(R.id.imageView);

        ImageLoadAsyncTask imageLoadAsyncTask = new ImageLoadAsyncTask(uri, image);
        imageLoadAsyncTask.execute();

    }
}
