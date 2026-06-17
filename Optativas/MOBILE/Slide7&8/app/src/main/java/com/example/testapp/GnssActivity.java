package com.example.testapp;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class GnssActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private LocationManager locationManager;
    private TextView gnssTextView;

    private String locationInfo = "";
    private String gnssStatusInfo = "";

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            updateLocationInfo(location);
        }
    };

    private final GnssStatus.Callback gnssCallback = new GnssStatus.Callback() {
        @Override
        public void onSatelliteStatusChanged(@NonNull GnssStatus status) {
            updateGnssStatus(status);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gnss);

        gnssTextView = findViewById(R.id.textViewGNSS);
        Button startButton = findViewById(R.id.buttonStartGNSS);
        Button stopButton = findViewById(R.id.buttonStopGNSS);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        startButton.setOnClickListener(v -> startLocationUpdates());
        stopButton.setOnClickListener(v -> stopLocationUpdates());
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }
        gnssTextView.setText("Aguardando informações...");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        locationManager.registerGnssStatusCallback(gnssCallback, new Handler(Looper.getMainLooper()));
    }

    private void stopLocationUpdates() {
        locationManager.removeUpdates(locationListener);
        locationManager.unregisterGnssStatusCallback(gnssCallback);
        locationInfo = "";
        gnssStatusInfo = "";
        gnssTextView.setText("Informacoes do Sistema de Satelites nao Disponiveis");
    }

    private void updateLocationInfo(Location location) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dados da Última Localização:\n");
        sb.append("Latitude: ").append(location.getLatitude()).append("\n");
        sb.append("Longitude: ").append(location.getLongitude()).append("\n");
        sb.append("Altitude: ").append(location.getAltitude()).append("\n");
        sb.append("Rumo: (radianos)").append(location.getBearing()).append("\n");
        sb.append("Velocidade (m/s): ").append(location.getSpeed()).append("\n");
        sb.append("Precisão: (m)").append(location.getAccuracy()).append("\n\n");
        locationInfo = sb.toString();
        updateUi();
    }

    private void updateGnssStatus(GnssStatus status) {
        StringBuilder sb = new StringBuilder();
        sb.append("Satélites visíveis: ").append(status.getSatelliteCount()).append("\n");
        for (int i = 0; i < status.getSatelliteCount(); i++) {
            sb.append("SVID: ").append(status.getSvid(i));
            sb.append(" | Azimute: ").append(status.getAzimuthDegrees(i)).append("°");
            sb.append(" | Elevação: ").append(status.getElevationDegrees(i)).append("°");
            sb.append(" | Usado no fix: ").append(status.usedInFix(i));
            sb.append("\n");
        }
        gnssStatusInfo = sb.toString();
        updateUi();
    }

    private void updateUi() {
        gnssTextView.setText(locationInfo + gnssStatusInfo);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Permissão de localização negada.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
