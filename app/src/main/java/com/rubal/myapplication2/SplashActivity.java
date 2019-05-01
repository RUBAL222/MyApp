package com.rubal.myapplication2;

import android.R;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rubal.myapplication2.storage.ImageuploadActivity;
import com.rubal.myapplication2.ui.LoginActivity;
import com.rubal.myapplication2.ui.Main2Activity;
import com.rubal.myapplication2.ui.OtpActivity;
import com.rubal.myapplication2.ui.RegistrationActivity;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        FirebaseApp.initializeApp(this);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        handler.sendEmptyMessageDelayed(101, 3000);

    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 101) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        }
    };
}