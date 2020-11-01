package com.gtr.bmhr;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.gtr.bmhr.Model.EmployeeInfo;
import com.gtr.bmhr.Model.TblCat_Desig;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static maes.tech.intentanim.CustomIntent.customType;

public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText empUserName,empUserPassword;
    private CheckBox chkEmpRemember;
    private Integer userId;
    private ProgressDialog progressDialog;
    private SharedPreferences sp;
    private SharedPreferences.Editor sped;
    private String strRemember;

    private String userName,userPassword;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (isConnected()) {
            preInitialize();
            prcGetRemember();
        } else {
            Toast.makeText(getApplicationContext(), "No internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;

    }

/*    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).getState() == NetworkInfo.State.CONNECTED ||
                Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;
    }*/

    private void preInitialize(){

        empUserName = findViewById(R.id.empUserName);
        empUserPassword = findViewById(R.id.empSecurity);

        userId = getIntent().getIntExtra("userId",0);


        findViewById(R.id.btnLogin).setOnClickListener(this);


        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        chkEmpRemember = (CheckBox) findViewById(R.id.chkEmpRemember);
        sp = getApplicationContext().getSharedPreferences("GTR", MODE_PRIVATE);
        sped = sp.edit();

    }

    private void prcSetRemember() {
         strRemember = "";
        if (chkEmpRemember.isChecked()) {
            strRemember = "Remember";
            sped.putString("UserName", empUserName.getText().toString().trim());
            sped.putString("Password", empUserPassword.getText().toString().trim());
            sped.putString("Remember", strRemember);
            sped.commit();
        } else {
            sped.putString("UserName", "");
            sped.putString("Password", "");
            sped.putString("Remember", "");
            sped.commit();
        }
    }
    private void prcGetRemember() {
        progressDialog.dismiss();
        if (sp.contains("UserName")) {
            String strPassword = "";
            empUserName.setText(sp.getString("UserName", ""));
            empUserPassword.setText(sp.getString("Password", ""));
            chkEmpRemember.setChecked(false);
            strPassword = sp.getString("Remember", "");
            if (strPassword.length() != 0) {
                chkEmpRemember.setChecked(true);
            }
            prcValidateUser("Auto");
        }
    }
    private void prcValidateUser(String Flag) {
        /* progressDialog.show();*/
        //Validating User :: Using Async Task
        try {
            validationUser();

        } catch (Exception ex) {
            Log.d("ValUser", ex.getMessage());
        }
    }
    private void validationUser(){

        userName = empUserName.getText().toString().trim();
        userPassword = empUserPassword.getText().toString().trim();
      /*  beforeOtpPhone="";
        password="";*/

        if (userName.isEmpty()) {
            empUserName.setError("Please provide user name");
            empUserName.requestFocus();
            return;
        }

        if (userPassword.isEmpty()) {
            empUserPassword.setError("Please provide security code");
            empUserPassword.requestFocus();
            return;
        }

        userLogin();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            validationUser();
        }
    }

    private void userLogin() {

        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://101.2.167.142/BMEnergyAPI/api/attendance/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Api api = retrofit.create(Api.class);
        Call<EmployeeInfo> call =api.getEmployeeDataSet(userName,userPassword);
        call.enqueue(new Callback<EmployeeInfo>() {
            @Override
            public void onResponse(Call<EmployeeInfo> call, Response<EmployeeInfo> response) {
                EmployeeInfo employeeInfo= response.body();
                if (response.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"",Toast.LENGTH_SHORT).show();

                    if (employeeInfo!= null) {
                        TblCat_Desig tblCat_desig = employeeInfo.getTblCat_Desig();
                        String empName= employeeInfo.getEmpName();
                        String empCode= employeeInfo.getEmpCode();
                        String empImage =employeeInfo.getImage();

                        Integer empId= employeeInfo.getEmpId();
                        Integer isMobileUser = employeeInfo.getIsMobileUser();
                        String empDesignation= tblCat_desig.getDesigName();
                        if (isMobileUser == 0){
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "This User didn't Register", Toast.LENGTH_SHORT).show();
                        }else if(isMobileUser==1){
                            prcSetRemember();

                            Intent intent = new Intent(LoginActivity.this, UserDashboardActivity.class);
                            intent.putExtra("empName", empName);
                            intent.putExtra("empCode", empCode);
                            intent.putExtra("empId", empId);
                            intent.putExtra("empDesignation", empDesignation);
                            intent.putExtra("empImage", empImage);

                            intent.putExtra("UserName", userName);
                            intent.putExtra("Password", userPassword);
                            intent.putExtra("Remember", strRemember);

                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this,"Login Successful", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            customType(LoginActivity.this,"left-to-right");
                        }

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"UserName or Password Failed", Toast.LENGTH_LONG).show();
                    }


                }else {
                    progressDialog.dismiss();
                    Log.d("", "onResponse: ");
                    Toast.makeText(LoginActivity.this,"Response Failed", Toast.LENGTH_SHORT).show();
                }
                    /*else{
                        TblCat_Desig tblCat_desig = employeeInfo.getTblCat_Desig();
                        String empName= employeeInfo.getEmpName();
                        Integer empId= employeeInfo.getEmpId();
                        String empDesignation= tblCat_desig.getDesigName();

                        Toast.makeText(LoginActivity.this, empName+"\n"+empId+"\n"+empDesignation, Toast.LENGTH_SHORT).show();
                    }*/
                }

            @Override
            public void onFailure(Call<EmployeeInfo> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.alert_dark_frame)
                .setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }

                })
                .setNegativeButton("No",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }

                } )

                .show();
    }
}