package com.gtr.bmhr;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gtr.bmhr.AttendanceHistory.AttendanceHistoryActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static maes.tech.intentanim.CustomIntent.customType;

public class UserDashboardActivity extends AppCompatActivity {

    TextView tvEmpCode, tvEmpName, tvEmpDate,empDesig;
    SharedPreferences sp;
    SharedPreferences.Editor sped;
    String UserName,UserPassword, empDesignation,empCode,empName,empImage,strRemember;
    Integer empId,IsMobileUser;

    LocationManager locationManager;
    Location tmpLocation;
    private final int FINE_LOCATION_PERMISSION=9999;
    Double postlatitude=0.00;
    Double postlongitude=0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        prcInitialization();
        showInfo();

    }

    @SuppressLint("CommitPrefEdits")
    private void prcInitialization(){

        tvEmpCode= findViewById(R.id.empCode);
        tvEmpName= findViewById(R.id.empName);
        tvEmpDate =findViewById(R.id.tvEmpDate);
        empDesig =findViewById(R.id.empDesig);

        Intent i=getIntent();
        UserName=i.getStringExtra("UserName");
        UserPassword=i.getStringExtra("Password");
        strRemember=i.getStringExtra("Remember");



        empName=i.getStringExtra("empName");
        empCode=i.getStringExtra("empCode");
        empId=i.getIntExtra("empId",0);
        empDesignation=i.getStringExtra("empDesignation");
        empImage =i.getStringExtra("empImage");

        sp = getApplicationContext().getSharedPreferences("GTR", MODE_PRIVATE);
        sped = sp.edit();
    }

    private void showInfo() {
        tvEmpCode.setText(empCode);
        tvEmpName.setText(empName);
        empDesig.setText(empDesignation);

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MMM-dd");
        String currentTime = dateformat.format(Calendar.getInstance().getTime());
        String text=currentTime;
        tvEmpDate.setText(text);
    }

    public void prcAtt(View view) {

        Integer employeeID= Integer.valueOf(empId.toString());

        Intent intent = new Intent(UserDashboardActivity.this, MapActivity.class);
        intent.putExtra("empId",employeeID);
        startActivity(intent);
        customType(UserDashboardActivity.this,"left-to-right");
    }
    public void web(View view) {

        Integer employeeID= Integer.valueOf(empId.toString());

        Intent intent = new Intent(UserDashboardActivity.this, WebviewJobCardActivity.class);
        intent.putExtra("empId",employeeID);
        intent.putExtra("userName",UserName);
        intent.putExtra("userPassword",UserPassword);
        startActivity(intent);
        customType(UserDashboardActivity.this,"left-to-right");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.myprofile:
                myprofile();
                break;
           /* case R.id.item_rate_app:
                openRate();
                break ;*/
            case R.id.signout:
                signout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signout() {
        sped.putString("UserName", "");
        sped.putString("Password", "");
        sped.putString("Remember", "");
        sped.commit();

        Toast.makeText(UserDashboardActivity.this,"Logout Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(UserDashboardActivity.this, LoginActivity.class));
        finish();
    }

    private void myprofile() {

        Intent intent = new Intent(UserDashboardActivity.this, ProfileViewActivity.class);
        intent.putExtra("empName",empName);
        intent.putExtra("empCode",empCode);
        intent.putExtra("empDesignation",empDesignation);
        intent.putExtra("empImage",empImage);

        startActivity(intent);
        customType(UserDashboardActivity.this,"left-to-right");
    }

    public void attendanceHistory(View view) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MMM-dd");
        String currentTime = dateformat.format(Calendar.getInstance().getTime());
        String text=currentTime;


        Integer employeeID= Integer.valueOf(empId.toString());

        Intent intent = new Intent(UserDashboardActivity.this, AttendanceHistoryActivity.class);
        intent.putExtra("empId",employeeID);
        intent.putExtra("currentDate",text);
        startActivity(intent);
        customType(UserDashboardActivity.this,"left-to-right");
    }
}