package com.example.testapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class LocationActivity extends AppCompatActivity {

    public static final int REQUEST_LOCATION_UPDATES = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private TextView altitudeTextView;
    private TextView bearingTextView;
    private TextView speedTextView;
    private TextView accuracyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        findViewById(R.id.buttonStart).setOnClickListener(v -> startLocation());
        findViewById(R.id.buttonStop).setOnClickListener(v -> stopLocation());

        latitudeTextView = findViewById(R.id.latitude);
        longitudeTextView = findViewById(R.id.longitude);
        altitudeTextView = findViewById(R.id.altitude);
        bearingTextView = findViewById(R.id.bearing);
        speedTextView = findViewById(R.id.speed);
        accuracyTextView = findViewById(R.id.accuracy);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (android.location.Location location : locationResult.getLocations()) {
                    latitudeTextView.setText("Latitude: " + location.getLatitude());
                    longitudeTextView.setText("Longitude: " + location.getLongitude());
                    altitudeTextView.setText("Altitude: " + location.getAltitude());
                    bearingTextView.setText("Rumo: " + location.getBearing());
                    speedTextView.setText("Velocidade: " + location.getSpeed());
                    accuracyTextView.setText("Precisão: " + location.getAccuracy());
                }
            }
        };
    }

    private boolean isTrackingLocation;
    private void startLocation() {
        Log.d("LocationActivity", "startLocation");
        if (isTrackingLocation) {
            Toast.makeText(this, "Localizacao ja esta sendo rastreada", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_UPDATES);
            return;
        }

        if (locationRequest == null) {
            locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                    .setWaitForAccurateLocation(false)
                    .setMinUpdateIntervalMillis(1000)
                    .build();
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
        isTrackingLocation = true;
    }

    private void stopLocation() {
        Log.d("LocationActivity", "stopLocation");
        if (fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_UPDATES) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocation();
            }
        }
    }
}