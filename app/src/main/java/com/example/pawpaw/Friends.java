package com.example.pawpaw;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class Friends extends AppCompatActivity {
    ListView lst;
    Button back;
    String userid;
    List<String> FrinedID = new ArrayList<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();

//    String[] months={"Janaury","Feb","March","April","May","June","July","August","September","Octomber","November","December"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
        back = findViewById(R.id.button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Friends.this, MapHomePage.class));
            }
        });
        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Friends.this);
        userid = preferences.getString("edit_text_preference_3", "Eileen");
        DocumentReference docRef = database.collection("friends").document(userid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                final Friend friend = documentSnapshot.toObject(Friend.class);
                final ArrayList<Friend> result= new ArrayList<>();
                database.collection("friends")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        result.add(document.toObject(Friend.class));
                                    }
                                }
                            }
                        });
                for (int i = 0; i < result.size();i++){
                    FrinedID.add(result.get(i).getUser1ID());
                    Log.w("FriendID",result.get(i).getUser1ID());
                }

            }
        });

        lst= (ListView) findViewById(R.id.list);
        ArrayAdapter<String> arrayadapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,FrinedID);
        lst.setAdapter(arrayadapter);

    }
}