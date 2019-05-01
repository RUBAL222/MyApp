package com.rubal.myapplication2.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rubal.myapplication2.R;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    EditText eTxtName, eTxtphone, eTxtotp;
    Button buttonValidate;

    PhoneAuthProvider authProvider;
    FirebaseAuth auth;

    void initViews() {
        eTxtName = findViewById(R.id.editTextName);
        eTxtphone = findViewById(R.id.editTextphone);
        eTxtotp = findViewById(R.id.editTextotp);
        buttonValidate = findViewById(R.id.buttonValidate);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        initViews();
        authProvider = PhoneAuthProvider.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String phone =eTxtphone.getText().toString().trim();

            authProvider.verifyPhoneNumber(
                    phone,
                    60,
                    TimeUnit.SECONDS,
                    OtpActivity.this,Callbacks);
          }

    };

PhoneAuthProvider.OnVerificationStateChangedCallbacks Callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){


    @Override
    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
        signInUser(phoneAuthCredential);
    }


    @Override
    public void onVerificationFailed(FirebaseException e) {

    }
};
void signInUser(PhoneAuthCredential phoneAuthCredential){
    auth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isComplete()){
                FirebaseUser user=task.getResult().getUser();
                String userId=user.getUid();
            }
        }
    });
}
}


