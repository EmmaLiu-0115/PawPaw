package com.example.pawpaw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp2 extends AppCompatActivity {
    TextView textView;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        textView = (TextView) findViewById(R.id.TextView);
        Bundle bundle = getIntent().getExtras();
        String Input = bundle.getString("name");
        textView.setText("Hi " + Input + "!");
    }

    public void clickFunction (View view){
        spinner = findViewById(R.id.spinner);
        Bundle bundle = getIntent().getExtras();
        String Input = bundle.getString("name");
        EditText myText = (EditText) findViewById(R.id.editText);
        String str = myText.getText().toString().trim();
        boolean Validity = validatePhoneNumber(str);
        if (Validity){

        }
        else {
            Toast.makeText(SignUp2.this, "Please input a phone number in form 1234567890", Toast.LENGTH_LONG).show();
            return;
        }
        int pos = spinner.getSelectedItemPosition();
        String[] Codes = getResources().getStringArray(R.array.country_code);
        String Code = Codes[pos];
        if (Code.isEmpty()){
            Toast.makeText(SignUp2.this, "Select a country", Toast.LENGTH_LONG).show();
            return;
        }
        String FinalPhone = Code + str;
        goToNext(FinalPhone, Input, Code, str);
    }

    public void goToNext (String FinalPhone, String name, String code, String Phone){
        Intent intent = new Intent(this, SignUp3.class);
        intent.putExtra("FinalPhone", FinalPhone);
        intent.putExtra("name", name);
        intent.putExtra("code", code);
        intent.putExtra("phone", Phone);
        startActivity(intent);
    }

    private static boolean validatePhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;

        else return false;
    }
}
