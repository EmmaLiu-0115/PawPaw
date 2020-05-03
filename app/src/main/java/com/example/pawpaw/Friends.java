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
    Button myAccount;

    //    String[] months={"Janaury","Feb","March","April","May","June","July","August","September","Octomber","November","December"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
        back = findViewById(R.id.button);
        myAccount = findViewById(R.id.account_page_button);
        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Friends.this, AccountPage.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Friends.this, MapHomePage.class));
            }
        });
        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Friends.this);
        userid = preferences.getString("edit_text_preference_3", "Eileen");
        Log.d("Friends", userid);
        DocumentReference docRef = database.collection("friends").document(userid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Friend friend = documentSnapshot.toObject(Friend.class);
                Log.d("Friends", friend.getUser1ID());
                //TODO: Call the function which uses friend info from the other class
                lst= (ListView) findViewById(R.id.list);
                ArrayAdapter<String> arrayadapter=new ArrayAdapter<String>(Friends.this,android.R.layout.simple_list_item_1,friend.getUser2IDs());
                lst.setAdapter(arrayadapter);

                Log.d("Friends", "Successfully get friends from database");
            }
        });

    }

    public void gotoAccountPage(View view){
        startActivity(new Intent(Friends.this,AccountPage.class));
    }
}