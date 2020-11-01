package com.gtr.bmhr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.gtr.bmhr.Model.AttendancePostClass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOError;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PicAttendanceActivity extends AppCompatActivity {

    private int CAMERA_PERMISSION_CODE = 24;

    ImageView imageViewback,imageViewfront;
    byte[] byteArray;

    String encodedbackImage;
    String encodedfrontImage;

    Integer Empid;
    private GoogleMap mMap;
    LocationManager locationManager;
    Location tmpLocation;
    private final int FINE_LOCATION_PERMISSION=9999;
    String postlatitude= String.valueOf(0.00);
    String postlongitude= String.valueOf(0.00);

    String locationname="";
    String deviceNo;
    Bitmap bitmap;

    private String currentPhotoPath,currentPhotoPathFront;
    ProgressDialog progressDialog;
    AttendancePostClass attendancePostClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_attendance);


        //Get data from another activity
        Intent i=getIntent();
        Empid= Integer.valueOf(i.getStringExtra("postEmpId"));
        postlatitude= String.valueOf(Double.valueOf(i.getStringExtra("postLat")));
        postlongitude= String.valueOf(Double.valueOf(i.getStringExtra("postLong")));
        locationname=i.getStringExtra("postLocationName");




        Button btnCamera=(Button)findViewById(R.id.btnCamera);
        imageViewback=(ImageView)findViewById(R.id.imageView);
        imageViewfront=(ImageView)findViewById(R.id.imageViewfront);

        progressDialog = new ProgressDialog(PicAttendanceActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        /*if (canOpenCamera()) {

            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},FINE_LOCATION_PERMISSION);
            }
            else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED )
            {
                return;
            }
            else if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                return  ;
            }



            if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double latitude=location.getLatitude();
                        double longitude=location.getLongitude();
                        LatLng latLng=new LatLng(latitude,longitude);
                        Geocoder geocoder=new Geocoder(getApplicationContext());
                        try {
                            List<Address> addressList=geocoder.getFromLocation(latitude,longitude,1);
                            String str=addressList.get(0).getFeatureName();
                            str+=","+addressList.get(0).getSubLocality()+","+addressList.get(0).getLocality()+","+addressList.get(0).getCountryName();

                     *//*   mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16.3f));
*//*

                            locationname=str;
                            postlatitude= String.valueOf(latitude);
                            postlongitude= String.valueOf(longitude);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
            }
            else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double latitude=location.getLatitude();
                        double longitude=location.getLongitude();
                        LatLng latLng=new LatLng(latitude,longitude);
                        Geocoder geocoder=new Geocoder(getApplicationContext());
                        try {
                            List<Address> addressList=geocoder.getFromLocation(latitude,longitude,1);
                            String str=addressList.get(0).getFeatureName();
                            str+=","+addressList.get(0).getSubLocality()+","+addressList.get(0).getLocality()+","+addressList.get(0).getCountryName();

                     *//*   mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16.3f));
*//*

                            locationname=str;
                            postlatitude= String.valueOf(latitude);
                            postlongitude= String.valueOf(longitude);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });

            }
            else{
                Toast.makeText(getApplicationContext(), "Location Access Failed!!",Toast.LENGTH_SHORT).show();

            }

        } else {
            //If the app has not the permission then asking for the permission
            requestCameraPermission();
        }*/

    }
    public void btnCamera(View view) {
        /*Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 0);
        startActivityForResult(intent,0);*/

        try {
            String fileName="photo";
            File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);
            currentPhotoPath = imageFile.getAbsolutePath();

            Uri imageUri= FileProvider.getUriForFile(PicAttendanceActivity.this,
                    "com.gtr.bmhr.fileprovider",imageFile);

            Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            intent.putExtra("android.intent.extras.CAMERA_FACING",0);
            startActivityForResult(intent,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnCamerafront(View view) {

        /*Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING",1);
        startActivityForResult(intent,1);*/


        try {
            String fileName="photo";
            File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile= File.createTempFile(fileName,".jpg",storageDirectory);
            currentPhotoPathFront = imageFile.getAbsolutePath();

            Uri imageUri= FileProvider.getUriForFile(PicAttendanceActivity.this,
                    "com.gtr.bmhr.fileprovider",imageFile);

            Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
            intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            intent.putExtra("android.intent.extras.CAMERA_FACING", 1);

           /* launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
            launchIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            launchIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);*/

            /*intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);*/
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(intent,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*bitmap = (Bitmap) data.getExtras().get("data");*/
        if (resultCode == RESULT_OK) {
            try {
                if (requestCode == 0) {
                    Bitmap bOutput;
                    bOutput = BitmapFactory.decodeFile(currentPhotoPath);

                    float degrees = 0;//rotation degree
                    Matrix matrix = new Matrix();
                    matrix.setRotate(degrees);
                    final float densityMultiplier = getResources().getDisplayMetrics().density;
                    int h = (int) (100 * densityMultiplier);
                    int w = (int) (h * bOutput.getWidth() / ((double) bOutput.getHeight()));

                    bOutput = Bitmap.createScaledBitmap(bOutput, w, h, true);
                    bitmap = Bitmap.createBitmap(bOutput, 0, 0, w, h, matrix, true);
                    imageViewback.setImageBitmap(bitmap);

                    if (bitmap != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        byteArray = stream.toByteArray();

                        encodedbackImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    } else {
                        Toast.makeText(PicAttendanceActivity.this, "Image not working !",
                                Toast.LENGTH_SHORT).show();
                    }

                } else if (requestCode == 1) {
                    Bitmap bOutput;
                    bOutput = BitmapFactory.decodeFile(currentPhotoPathFront);


                    float degrees = 0;//rotation degree
                    Matrix matrix = new Matrix();
                    matrix.setRotate(degrees);
                    final float densityMultiplier = getResources().getDisplayMetrics().density;
                    int h = (int) (100 * densityMultiplier);
                    int w = (int) (h * bOutput.getWidth() / ((double) bOutput.getHeight()));

                    bOutput = Bitmap.createScaledBitmap(bOutput, w, h, true);
                    bitmap = Bitmap.createBitmap(bOutput, 0, 0, w, h, matrix, true);

                    imageViewfront.setImageBitmap(bitmap);

                    if (bitmap != null) {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        byteArray = stream.toByteArray();

                        encodedfrontImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    } else {
                        Toast.makeText(PicAttendanceActivity.this, "Image not working !",
                                Toast.LENGTH_SHORT).show();
                    }


                }
            } catch (IOError error) {
                error.printStackTrace();
            }
        }

    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else
            return false;

    }
    //CameraPermission
    private boolean canOpenCamera() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;

            //If permission is not granted returning false
        }else{
            return false;
        }
    }

    private void requestCameraPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Toast.makeText(getApplicationContext(), "Permission Required to Open Camera", Toast.LENGTH_SHORT).show();
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == CAMERA_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(getApplicationContext(), MessageViewActivity.class));
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the Camera permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void btnPost(View view) {

        if(isConnected())
        {
            if(encodedbackImage != null && !encodedbackImage.isEmpty() && encodedfrontImage != null && !encodedfrontImage.isEmpty() )
            {
                if(postlatitude==null){
                    Toast.makeText(getApplicationContext(), "Wait",Toast.LENGTH_SHORT).show();

                }else {
                    progressDialog.show();
                    /*prcValidateUser("");*/
                    postAttendance();
                    //onBackPressed();
                    //  finish();
                    /*Intent intent = new Intent(PicattActivity.this, MessageViewActivity.class);
                    startActivity(intent);*/


                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Please take picture correctly.",Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(getApplicationContext(), "No Internet Connection ", Toast.LENGTH_SHORT).show();
        }


    }

    private void postAttendance() {
        deviceNo= "99999";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://101.2.167.142/BMEnergyAPI/api/attendance/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);

       /* JsonObject jsonObjectFinal = new JsonObject();

        JSONObject jsonObjectName = new JSONObject();
        try {
            jsonObjectName.put("empId", Empid);
            jsonObjectName.put("Latitude", postlatitude);
            jsonObjectName.put("Longitude", postlongitude);
            jsonObjectName.put("LocationName", locationname);
            jsonObjectName.put("PicBack", encodedbackImage);
            jsonObjectName.put("PicFront", encodedfrontImage);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonParser jsonParser = new JsonParser();
        jsonObjectFinal = (JsonObject) jsonParser.parse(jsonObjectName.toString());
        Call<String> call = api.postAttendance(jsonObjectFinal);*/

        attendancePostClass = new AttendancePostClass(Empid,postlatitude,postlongitude,locationname,encodedbackImage,encodedfrontImage,deviceNo);

        Call<String> call = api.postAttendance(attendancePostClass);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    String isSuccessful=response.body();
                    if (isSuccessful != null) {
                        if (isSuccessful.equals("true")) {
                            Toast.makeText(PicAttendanceActivity.this,"Attendance Successful", Toast.LENGTH_LONG).show();
                            /*Toast.makeText(PicAttendanceActivity.this, Empid, Toast.LENGTH_SHORT).show();*/
                            startActivity(new Intent(PicAttendanceActivity.this, MessageViewActivity.class));
                           finish();
                        } else {
                            Toast.makeText(PicAttendanceActivity.this,"Something is Wrong", Toast.LENGTH_LONG).show();

                        }
                    }

                } else {
                    Log.d("", "onResponse: ");
                    Toast.makeText(PicAttendanceActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(PicAttendanceActivity.this, "Failed to Connect", Toast.LENGTH_SHORT).show();
            }
        });


    }
}