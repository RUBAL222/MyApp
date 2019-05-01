package com.rubal.myapplication2.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rubal.myapplication2.HomeActivity;
import com.rubal.myapplication2.R;
import com.rubal.myapplication2.model.User;
import com.rubal.myapplication2.storage.ImageuploadActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText eTxtEmail,eTxtPassword;
    Button Btnlogin;
    User user;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    FirebaseAuth auth;

    void initViews()
    {
        eTxtEmail = findViewById(R.id.editTextEmail);
        eTxtPassword = findViewById(R.id.editTextPassword);
        Btnlogin=findViewById(R.id.buttonLogin);
        Btnlogin.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        user=new User();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    @Override
    public void onClick(View v) {
        user.email = eTxtEmail.getText().toString();
        user.password = eTxtPassword.getText().toString();
        loginUser();


    }

     void loginUser() {
        progressDialog.show();
        auth.signInWithEmailAndPassword(user.email,user.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
//                            Toast.makeText(LoginActivity.this,user.email+"login ",Toast.LENGTH_LONG).show();
//                            progressDialog.dismiss();
//                            Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
//                            startActivity(intent);
//                            finish();
                            saveUserInCloudDB();


                        }
                    }
                });
    }
    void saveUserInCloudDB()
    {
        db.collection("login").add(user)
                .addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isComplete()){
                            Toast.makeText(LoginActivity.this,user.email+"login successfully",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Intent intent =new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    public void Homebutton(View view) {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

    }
    public void buttonRegister(View view) {
        startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));

    }
}
