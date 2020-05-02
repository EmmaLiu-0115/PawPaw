package com.example.pawpaw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;

public class AddFriend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
    }

    public void clickFunction (View view){
        EditText myText = (EditText) findViewById(R.id.editText);
        String str = myText.getText().toString();
        String phoneFriend = str.trim();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String phone = prefs.getString("edit_text_preference_3",  "");
        Database database = new Database(this);
        if (phone.equals(phoneFriend)){
            Toast.makeText(AddFriend.this, "Don't input your phone number", Toast.LENGTH_LONG).show();
        }
        if (phoneFriend.length() > 9) {
            database.addFriendToDB(phone, phoneFriend);
            Toast.makeText(AddFriend.this, "Added Friend", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, AccountPage.class);
            startActivity(intent);
        }
    }
}
