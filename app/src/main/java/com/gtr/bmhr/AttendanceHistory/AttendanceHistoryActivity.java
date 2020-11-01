package com.gtr.bmhr.AttendanceHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.gtr.bmhr.Api;
import com.gtr.bmhr.Model.AttendanceDataShow;
import com.gtr.bmhr.R;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AttendanceHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    Integer empId;
    String currentDate;
    TextView text_date;
    String showDate;

    DataAdapter dataAdapter;
    ProgressDialog progressDialog;
    DatePickerDialog picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_history);

        Intent i=getIntent();
        empId=i.getIntExtra("empId",0);
        currentDate=i.getStringExtra("currentDate");
        text_date = findViewById(R.id.text_date);

        progressDialog = new ProgressDialog(AttendanceHistoryActivity.this);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        currenTimeShow();

    }

    private void currenTimeShow() {
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://101.2.167.142/BMEnergyAPI/api/attendance/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Api api = retrofit.create(Api.class);
        Call<List<AttendanceDataShow>> listCall= api.getAttendanceDataSet(empId,currentDate);
        listCall.enqueue(new Callback<List<AttendanceDataShow>>() {
            @Override
            public void onResponse(Call<List<AttendanceDataShow>> call, Response<List<AttendanceDataShow>> response) {
                progressDialog.dismiss();
                if (!response.isSuccessful()){
                    Toast.makeText(AttendanceHistoryActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<AttendanceDataShow> attendanceDataShows = response.body();

                    for (AttendanceDataShow ourDataSet : attendanceDataShows) {

                        showIt(response.body());
                    }
            }

            @Override
            public void onFailure(Call<List<AttendanceDataShow>> call, Throwable t) {

            }
        });
    }

    private void showIt(List<AttendanceDataShow> response) {


        DataAdapter dataAdapter = new DataAdapter(response,getApplicationContext());

        recyclerView.setAdapter(dataAdapter);
    }


    public void pressDateShow(View view) {

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(AttendanceHistoryActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        showDate= year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        text_date.setText(showDate);
                    }
                }, year, month, day);
        picker.show();

    }

    public void pressResultShow(View view) {

        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://101.2.167.142/BMEnergyAPI/api/attendance/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Api api = retrofit.create(Api.class);
        Call<List<AttendanceDataShow>> listCall= api.getAttendanceDataSet(empId,showDate);
        listCall.enqueue(new Callback<List<AttendanceDataShow>>() {
            @Override
            public void onResponse(Call<List<AttendanceDataShow>> call, Response<List<AttendanceDataShow>> response) {
                progressDialog.dismiss();
                if (!response.isSuccessful()){
                    Toast.makeText(AttendanceHistoryActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<AttendanceDataShow> attendanceDataShows = response.body();


                    for (AttendanceDataShow ourDataSet : attendanceDataShows) {
                        progressDialog.dismiss();
                        showIt(response.body());
                    }

            }

            @Override
            public void onFailure(Call<List<AttendanceDataShow>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}