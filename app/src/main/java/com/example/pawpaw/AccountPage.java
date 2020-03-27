package com.example.pawpaw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class AccountPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);
    }

    public void clickFunction (View view){
        goToActivityEdit();
    }
    public void goToActivityEdit (){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void clickFunction2 (View view){

    }
    TextView NameText;
    TextView PhoneText;
    TextView DescriptionText;
    TextView Location;
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean privacy = prefs.getBoolean("switch_preference_1", true);
        String name = prefs.getString("edit_text_preference_4",  "");
        String phone = prefs.getString("edit_text_preference_3",  "");
        String description = prefs.getString("edit_text_preference_5",  "");
        String location = prefs.getString("edit_text_preference_6",  "");
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
}
