package com.example.pawpaw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ListMainActivity extends AppCompatActivity {

    ListView listView;
    List<String> locationNames = new ArrayList<>();
    List<String> locationImages = new ArrayList<>();
    Button back;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);

        //How to use get method sample:
        //Set id to search in Database
        String locationId ="ab";
        Log.w("lma","count1");

        listView = findViewById(R.id.listview);

        //To get data from database
        //A list to store all the results from database since a location can have several reviews
        final List<Reviews> result = new ArrayList<>();

        //Initiate the real firestore database
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        //Call the get method
        database.collection("reviews")
                .whereEqualTo("locationID", locationId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Put all results that we get from database in result list
                                result.add(document.toObject(Reviews.class));

                                Log.d("ListMainActivity", document.getId() + " => " + document.getData());
                            }

                            //Write logics of how to use the result list
                            for (int i = 0; i<result.size(); i++){
                                String name = result.get(i).getLocationName();
                                locationNames.add(name);
                                Log.w("lma", name);
                                Log.w("size of locationImages", String.valueOf(locationNames.size()));
                                locationImages.add(result.get(i).getPhoto());
                                Log.w("lma", result.get(i).getPhoto());
                                Log.w("size of locationImages", String.valueOf(locationImages.size()));
                            }

                            back= findViewById(R.id.button4);
                            back.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ListMainActivity.this, MapHomePage.class));
                                }
                            });
                            ListMainActivity.CursorAdapter cursorAdapter = new ListMainActivity.CursorAdapter();

                            listView.setAdapter(cursorAdapter);

                        } else {
                            Log.d("ListMainActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),ListdataActivity.class);
                intent.putExtra("name",locationNames.get(i));
                intent.putExtra("image", locationImages.get(i));
                startActivity(intent);

            }
        });

        Log.w("lma","count3");

    }




    private class CursorAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return locationNames.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.row_data,null);
            TextView name = view1.findViewById(R.id.fruits);
            ImageView image = view1.findViewById(R.id.images);

            name.setText(locationNames.get(i));

            // Get String data from Intent
            String locationAddress = "images/"+locationImages.get(i);

            //Display image
            StorageReference storageRef = storage.getReference();

            storageRef.child(locationAddress).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    helper(uri.toString());
                    // Got the download URL for the image
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });



            return view1;
        }

        //Helper method to display image
        private void helper(String uri){
            View view1 = getLayoutInflater().inflate(R.layout.row_data,null);
            ImageView image = view1.findViewById(R.id.images);

            ImageLoadAsyncTask imageLoadAsyncTask = new ImageLoadAsyncTask(uri, image);
            imageLoadAsyncTask.execute();

        }
    }



}
