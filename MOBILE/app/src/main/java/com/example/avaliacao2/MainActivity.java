package com.example.avaliacao2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "GnssStatusApp";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GnssSkyView gnssSkyView;
    private LocationManager locationManager;
    private GnssStatus.Callback gnssStatusCallback;
    private Handler mainThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gnssSkyView = findViewById(R.id.gnssSkyView);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mainThreadHandler = new Handler(Looper.getMainLooper());

        gnssStatusCallback = new GnssStatus.Callback() {
            @Override
            public void onStarted() {
                super.onStarted();
                Log.d(TAG, "GNSS provider started");
            }

            @Override
            public void onStopped() {
                super.onStopped();
                Log.d(TAG, "GNSS provider stopped");
            }

            @Override
            public void onSatelliteStatusChanged(@NonNull GnssStatus status) {
                super.onSatelliteStatusChanged(status);
                Log.d(TAG, "onSatelliteStatusChanged: " + status.getSatelliteCount() + " satellites");
                gnssSkyView.setGnssStatus(status);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Checking permissions and starting updates.");
        checkPermissionsAndStartUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Stopping updates.");
        if (locationManager != null) {
            locationManager.unregisterGnssStatusCallback(gnssStatusCallback);
            locationManager.removeUpdates(this);
        }
    }

    private void checkPermissionsAndStartUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Location permission not granted. Requesting permission.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            Log.d(TAG, "Location permission already granted. Starting updates.");
            startGnssUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: Permission GRANTED.");
                startGnssUpdates();
            } else {
                Log.d(TAG, "onRequestPermissionsResult: Permission DENIED.");
                Toast.makeText(this, "A permissão de localização é necessária para exibir os satélites.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startGnssUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return; // Safeguard
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Por favor, ative o GPS para obter os dados dos satélites.", Toast.LENGTH_LONG).show();
            Log.d(TAG, "GPS provider is disabled.");
        }

        Log.d(TAG, "Registering for GNSS and Location updates.");
        locationManager.registerGnssStatusCallback(gnssStatusCallback, mainThreadHandler);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this, mainThreadHandler.getLooper());
    }

    // LocationListener methods
    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d(TAG, "onLocationChanged: " + location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged: provider=" + provider + " status=" + status);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Log.d(TAG, "onProviderEnabled: " + provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Log.d(TAG, "onProviderDisabled: " + provider);
    }
}
