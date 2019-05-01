package com.rubal.myapplication2.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rubal.myapplication2.HomeActivity;
import com.rubal.myapplication2.R;
import com.rubal.myapplication2.model.User;

public class RegistrationActivity extends AppCompatActivity  implements View.OnClickListener {
    EditText eTxtName, eTxtEmail, eTxtPassword;
    TextView txtLogin;
    Button btnRegister;

    User user;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    FirebaseMessaging firebaseMessaging;
    FirebaseInstanceId firebaseInstanceId;

    void initViews() {
        eTxtName = findViewById(R.id.editTextName);
        eTxtEmail = findViewById(R.id.editTextEmail);
        eTxtPassword = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.buttonRegister);
        btnRegister.setOnClickListener(this);
        //txtLogin = findViewById(R.id.textViewlogin);
        txtLogin.setOnClickListener(this);
        FirebaseApp.initializeApp(this);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        user = new User();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseMessaging=FirebaseMessaging.getInstance();
        firebaseInstanceId=FirebaseInstanceId.getInstance();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonRegister) {
            user.name = eTxtName.getText().toString();
            user.email = eTxtEmail.getText().toString();
            user.password = eTxtPassword.getText().toString();

            registerUser();

        } else {
            Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
            startActivity(intent);
        }

    }
    void subscribeUserForCloudMessaging(){
        firebaseMessaging.subscribeToTopic("events")
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            getToken();
                        }
                    }
                });
    }

    private void getToken() {
        firebaseInstanceId.getInstanceId()
                .addOnCompleteListener(this, new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(task.isComplete()){
                            user.token = task.getResult().getToken();
                            saveUserInCloudDB();
                        }
                    }
                });
    }



    void registerUser() {
        progressDialog.show();
        auth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()) {
//                            Toast.makeText(RegistrationActivity.this,user.name+"Registered ",Toast.LENGTH_LONG).show();
//                            progressDialog.dismiss();
//                            Intent intent=new Intent(RegistrationActivity.this, HomeActivity.class);
//                            startActivity(intent);
//                            finish();
                            saveUserInCloudDB();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String mesage = e.getMessage();
                    }
                });
    }

    void saveUserInCloudDB() {
//        db.collection("users").add(user)
//                .addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentReference> task) {
//                        if (task.isComplete()) {
//                            Toast.makeText(RegistrationActivity.this, user.name + "Registered successfully", Toast.LENGTH_LONG).show();
//                            progressDialog.dismiss();
//
//                            Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//
//
//                    }
//                });
        firebaseUser = auth.getCurrentUser();
        db.collection("users").document(firebaseUser.getUid()).set(user)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RegistrationActivity.this,user.name+"Registered Successfully",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        Intent intent=new Intent (RegistrationActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }

}