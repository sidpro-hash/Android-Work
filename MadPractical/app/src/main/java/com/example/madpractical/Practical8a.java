package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Practical8a extends AppCompatActivity {

    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical8a);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        client = LocationServices.getFusedLocationProviderClient(this);
        // get location permission using dexter library
        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        // if user denied permission, ask again when user uses service again
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        if (location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            //Log.d("location"," "+location.getLatitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are here..!!");

                            googleMap.addMarker(markerOptions);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));//10
                            // save user details in SharedPreferences
                            SharedPreferences preferences = getSharedPreferences("last_location_info", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putLong("lat", Double.doubleToRawLongBits(location.getLatitude()));
                            editor.putLong("long", Double.doubleToRawLongBits(location.getLongitude()));
                            editor.putBoolean("check", true);
                            editor.apply();
                        } else {
                            // get data from preferences
                            SharedPreferences preferences = getSharedPreferences("student_info", MODE_PRIVATE);
                            Boolean check = preferences.getBoolean("check", false);
                            if (check == true) {
                                LatLng latLng = new LatLng(Double.longBitsToDouble(preferences.getLong("lat", Double.doubleToLongBits(23))), Double.longBitsToDouble(preferences.getLong("long", Double.doubleToLongBits(23))));
                                //Log.d("location"," "+location.getLatitude());
                                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are here..!!");

                                googleMap.addMarker(markerOptions);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));//10
                            }

                        }
                    }
                });
            }
        });
    }
}