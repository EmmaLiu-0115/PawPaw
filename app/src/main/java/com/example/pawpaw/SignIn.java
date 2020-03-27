package com.example.pawpaw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignIn extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void clickFunction (View view){
        goToActivityPhoneSignIn();
    }

    public void goToActivityPhoneSignIn (){
        Intent intent = new Intent(this, PhoneSignIn.class);
        startActivity(intent);
    }

        // REMEMBER AFTER SIGN IN TO SET ALL DEFAULT PREFERENCES TO THE DEFAULT PREFERENCES ON THE BACKEND
}
