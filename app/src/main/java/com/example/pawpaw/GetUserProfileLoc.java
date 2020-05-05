package com.example.pawpaw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class GetUserProfileLoc extends AppCompatActivity {
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_profile_loc);
    }
    public void goToNext (){
        Intent intent = new Intent(this, AccountPage.class);
        intent.putExtra("newUser", "1");
        startActivity(intent);
    }
    public void clickFunction (View view){
        spinner = findViewById(R.id.spinner);
        int pos = spinner.getSelectedItemPosition();
        String[] Codes = getResources().getStringArray(R.array.states);
        String State = Codes[pos];
        if (State.isEmpty()){
            Toast.makeText(GetUserProfileLoc.this, "Select a state", Toast.LENGTH_LONG).show();
            return;
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("edit_text_preference_6", State);
        editor.commit();
        String phone = prefs.getString("edit_text_preference_3",  "");
        String name = prefs.getString("edit_text_preference_4",  "");
        String location = prefs.getString("edit_text_preference_6",  "");
        String description = prefs.getString("edit_text_preference_5",  "");
        String Img = prefs.getString("edit_text_preference_10", "");
        int DefPrivacy = 1;
        Database database = new Database(this);

        User user = new User(phone, name, location, "1234", phone, description, DefPrivacy, Img);
        database.addUserToDB(user);
        goToNext();
    }
}
