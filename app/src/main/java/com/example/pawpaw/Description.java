package com.example.pawpaw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Description extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        textView = (TextView) findViewById(R.id.TextView);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        textView.setText(name + ", we still need some basic information");
    }
    public void clickFunction (View view){
        EditText myText = (EditText) findViewById(R.id.editText);
        String str = myText.getText().toString();
        if (str.length() <= 4){
            Toast.makeText(Description.this, "Input must be at least length 5", Toast.LENGTH_LONG).show();
            return;
        }
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String Phone = bundle.getString("FinalPhone");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("edit_text_preference_4", name);
        editor.putString("edit_text_preference_5", str);
        editor.putString("edit_text_preference_3", Phone);
        editor.commit();
        goToNext();
    }
    public void goToNext (){
        Intent intent = new Intent(this, ProfileImage.class);
        startActivity(intent);
    }
}
