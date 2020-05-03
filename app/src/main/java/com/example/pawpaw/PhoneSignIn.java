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

public class PhoneSignIn extends AppCompatActivity {
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_sign_in);
    }
    public void clickFunction(View view){
        EditText myText = (EditText) findViewById(R.id.editText);
        String str = myText.getText().toString().trim();
        boolean Validity = validatePhoneNumber(str);
        if (Validity){

        }
        else {
            Toast.makeText(PhoneSignIn.this, "Please input a phone number in form 1234567890", Toast.LENGTH_LONG).show();
            return;
        }
        spinner = findViewById(R.id.spinner);
        int pos = spinner.getSelectedItemPosition();
        String[] Codes = getResources().getStringArray(R.array.country_code);
        String Code = Codes[pos];
        if (Code.isEmpty()){
            Toast.makeText(PhoneSignIn.this, "Select a country", Toast.LENGTH_LONG).show();
            return;
        }
        String FinalPhone = Code + str;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("edit_text_preference_3", FinalPhone);
        editor.commit();
        //startActivity(new Intent(this, AccountPage.class));
        Intent i = new Intent(this, MapHomePage.class);
        i.putExtra("newUser","0");
        startActivity(i);
    }

    private static boolean validatePhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
        else return false;
    }
}
