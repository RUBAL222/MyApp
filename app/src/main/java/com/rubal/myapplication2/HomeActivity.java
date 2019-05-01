package com.rubal.myapplication2;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.rubal.myapplication2.ui.CameraActivity;
import com.rubal.myapplication2.ui.RegistrationActivity;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 101, 0, "logout");
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(item.getItemId()==101){
           auth.signOut();
           Intent intent=new Intent(HomeActivity.this,SplashActivity.class);
           startActivity(intent);
       }

        return super.onOptionsItemSelected(item);
    }

    public void upload(View view) {
        startActivity(new Intent(getApplicationContext(), CameraActivity.class));
    }
}
