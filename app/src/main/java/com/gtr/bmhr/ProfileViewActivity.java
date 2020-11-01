package com.gtr.bmhr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileViewActivity extends AppCompatActivity {

    private TextView tvEmpName, tvDegisName, tvEmpCode;
    private CircleImageView profileImage;
    private String empName,empCode,empDesignation,empImage;
    private byte[] viewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        prcInitialization();
    }

    private void prcInitialization() {
        profileImage = findViewById(R.id.profile_image);
        tvEmpName=(TextView)findViewById(R.id.tvEmpName);
        tvEmpCode=(TextView)findViewById(R.id.tvEmpCode);
        tvDegisName=(TextView)findViewById(R.id.tvDegisName);

        Intent i=getIntent();
        empName= i.getStringExtra("empName");
        empCode= i.getStringExtra("empCode");
        empDesignation= i.getStringExtra("empDesignation");
        empImage= i.getStringExtra("empImage");

        tvEmpName.setText(empName);
        tvEmpCode.setText(empCode);
        tvDegisName.setText(empDesignation);

        try {
            viewPhoto = Base64.decode(empImage,Base64.DEFAULT);
            Bitmap decoded= BitmapFactory.decodeByteArray(viewPhoto,0,viewPhoto.length);
            profileImage.setImageBitmap(decoded);

        } catch(Exception e) {
            e.getMessage();
        }


    }
}