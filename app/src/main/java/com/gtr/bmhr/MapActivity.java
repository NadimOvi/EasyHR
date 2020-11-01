package com.gtr.bmhr;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    double lat,lon;
    String Empid;
    String locationname,postLatitude,postLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent i=getIntent();
        Empid= String.valueOf(i.getIntExtra("empId",0));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                     lat = currentLocation.getLatitude();
                     lon = currentLocation.getLongitude();

                    LatLng latLng =new LatLng(lat,lon);
                    Geocoder geocoder=new Geocoder(getApplicationContext());

                    List<Address> addressList= null;
                    try {
                        addressList = geocoder.getFromLocation(lat,lon,1);
                        String str=addressList.get(0).getFeatureName();
                        str+=","+addressList.get(0).getSubLocality()+","+addressList.get(0).getLocality()+","+addressList.get(0).getCountryName();

                        locationname=str;
                        postLatitude= String.valueOf(lat);
                        postLongitude= String.valueOf(lon);


                        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
                        assert supportMapFragment != null;
                        supportMapFragment.getMapAsync(MapActivity.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
        googleMap.addMarker(markerOptions);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }

    public void prcConfirm(View view) {

        if(lat==0.00){
            Toast.makeText(getApplicationContext(), "Wait",Toast.LENGTH_SHORT).show();

        }else {

            String postEmpId=Empid;
            /*String postLat=postlatitude.toString();
            String postLong=postlongitude.toString();
            String postLocationName=locationname;*/

            Intent intent = new Intent(MapActivity.this, PicAttendanceActivity.class);
            intent.putExtra("postEmpId",postEmpId);

            intent.putExtra("postLat",postLatitude);
            intent.putExtra("postLong",postLongitude);
            intent.putExtra("postLocationName",locationname);

            startActivity(intent);
            finish();


        }

    }
}