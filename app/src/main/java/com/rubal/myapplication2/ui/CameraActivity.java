package com.rubal.myapplication2.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.rubal.myapplication2.R;

import java.util.jar.Attributes;

public class CameraActivity extends AppCompatActivity {

    EditText etxtName,etxtTitle,etxtDescription,etxtAddress;
    Button upload;
    Button Captureimage;
    ImageView imageViewdisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        etxtName=findViewById(R.id.name);
        etxtTitle=findViewById(R.id.title);
        etxtDescription=findViewById(R.id.description);
        etxtAddress=findViewById(R.id.Address);

        upload=findViewById(R.id.register);
        Captureimage=findViewById(R.id.btnCapture);

        imageViewdisplay=findViewById(R.id.imagecapture);
        getSupportActionBar().setTitle("Upload");

        Captureimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap=(Bitmap)data.getExtras().get("data");
        imageViewdisplay.setImageBitmap(bitmap);
    }
}
