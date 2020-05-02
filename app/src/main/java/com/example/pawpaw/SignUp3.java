package com.example.pawpaw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUp3 extends AppCompatActivity {
    private String Verification;
    private FirebaseAuth mAuth;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);
        mAuth = FirebaseAuth.getInstance();
        String Code = getIntent().getStringExtra("code");
        String phone = getIntent().getStringExtra("phone");
        String FinalPhone = getIntent().getStringExtra("FinalPhone");
        String name = getIntent().getStringExtra("name");
        textView = (TextView) findViewById(R.id.test);
        textView.setText(FinalPhone);
        sendVerification(FinalPhone);
    }

    private void sendVerification(String Phone){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(Phone, 90, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, mCallback);
    }

    private void VerifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(Verification, code);
        SignInCredential(credential);
    }

    private void SignInCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Bundle bundle = getIntent().getExtras();
                    String Name = bundle.getString("name");
                    String FinalPhone = bundle.getString("FinalPhone");
                    Intent intent = new Intent(SignUp3.this, Description.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("name", Name);
                    intent.putExtra("FinalPhone", FinalPhone);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUp3.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Verification = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                VerifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(SignUp3.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    public void clickFunction (View view){
        Bundle bundle = getIntent().getExtras();
        String Input = bundle.getString("name");
        EditText myText = (EditText) findViewById(R.id.editText);
        String code = myText.getText().toString().trim();
        if(code.isEmpty() || code.length() < 6){
            myText.setError("Enter Code");
            myText.requestFocus();
            return;
        }
        VerifyCode(code);
    }

    public void goToNext (String phone, String name){
        Intent intent = new Intent(this, Description.class);
        intent.putExtra("phone", phone);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}
