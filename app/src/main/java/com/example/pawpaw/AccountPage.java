package com.example.pawpaw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AccountPage extends AppCompatActivity {

    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Database database = new Database(this);
        //database.

        //Now I need to update the database
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String phone = prefs.getString("edit_text_preference_3",  "");
        SharedPreferences.Editor editor = prefs.edit();
        //TODO
        database.getUserFromDB(phone);
        editor.commit();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);
        boolean privacy = prefs.getBoolean("switch_preference_1", true);
        String name = prefs.getString("edit_text_preference_4",  "");
        String description = prefs.getString("edit_text_preference_5",  "");
        String location = prefs.getString("edit_text_preference_6",  "");
        String Img = prefs.getString("edit_text_preference_10", "");
        Uri MyImg = Uri.parse(Img);
        Bundle bundle = getIntent().getExtras();
        NameText = (TextView) findViewById(R.id.profile_name);
        PhoneText = (TextView) findViewById(R.id.phone);
        DescriptionText = (TextView) findViewById(R.id.info);
        Location = (TextView) findViewById(R.id.location);
        ProfilePic = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_image);
        NameText.setText(name);
        PhoneText.setText(phone);
        DescriptionText.setText(description);
        Location.setText(location);
        Picasso.with(this).load(MyImg).into(ProfilePic);
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



    TextView NameText;
    TextView PhoneText;
    TextView DescriptionText;
    TextView Location;
    de.hdodenhof.circleimageview.CircleImageView ProfilePic;
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
        NameText = (TextView) findViewById(R.id.profile_name);
        PhoneText = (TextView) findViewById(R.id.phone);
        DescriptionText = (TextView) findViewById(R.id.info);
        Location = (TextView) findViewById(R.id.location);
        NameText.setText(name);
        PhoneText.setText(phone);
        DescriptionText.setText(description);
        Location.setText(location);

        db.getUserFromDB("123");
        //REMEMBER TO UPDATE THE SERVER AFTER UPDATING SETTINGS
    }




    public void getUserData(User user){

    }

}
