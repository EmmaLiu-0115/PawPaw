package com.example.pawpaw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void clickFunction (View view){
        EditText myText = (EditText) findViewById(R.id.editText);
        String str = myText.getText().toString();
        //boolean Validity = str.matches("^.*[^a-zA-Z ].*$");
        Pattern p = Pattern.compile("^[ A-Za-z]+$");
        Matcher m = p.matcher(str);
        boolean Validity = m.matches();
        boolean Validity2 = CheckLength(str);
        if (Validity && Validity2){
            goToNext(str);
        }
        else {
            Toast.makeText(SignUp.this, "Invalid input, please only input between 2 and 20 valid characters", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public boolean CheckLength (String s){
        if(s.length() <= 20 || s.length() > 1){
            return true;
        }
        else{
            return false;
        }
    }

    public void goToNext (String s){
        Intent intent = new Intent(this, SignUp2.class);
        intent.putExtra("name", s);
        startActivity(intent);
    }
}
