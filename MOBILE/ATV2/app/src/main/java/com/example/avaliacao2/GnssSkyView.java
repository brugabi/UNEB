package com.example.avaliacao2;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.location.GnssStatus;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class GnssSkyView extends View {

    private static final String PREFS_NAME = "GnssSkyViewPrefs";
    private static final String KEY_SHOW_GPS = "showGps";
    private static final String KEY_SHOW_GLONASS = "showGlonass";
    private static final String KEY_SHOW_GALILEO = "showGalileo";
    private static final String KEY_SHOW_BEIDOU = "showBeidou";
    private static final String KEY_SHOW_NOT_IN_FIX = "showNotInFix";

    private int skyColor;
    private Paint paint;
    private GnssStatus gnssStatus;
    private SharedPreferences sharedPreferences;

    private boolean showGps, showGlonass, showGalileo, showBeidou, showNotInFix;

    private Bitmap flagUsa, flagRussia, flagEu, flagChina;

    public GnssSkyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        sharedPreferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loadSettings();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.GnssSkyView, 0, 0);
            try {
                skyColor = a.getColor(R.styleable.GnssSkyView_skyColor, Color.parseColor("#191970"));
            } finally {
                a.recycle();
            }
        } else {
            skyColor = Color.parseColor("#191970");
        }

        try {
            flagUsa = BitmapFactory.decodeResource(getResources(), R.drawable.flag_usa);
            flagRussia = BitmapFactory.decodeResource(getResources(), R.drawable.flag_russia);
            flagEu = BitmapFactory.decodeResource(getResources(), R.drawable.flag_eu);
            flagChina = BitmapFactory.decodeResource(getResources(), R.drawable.flag_china);
        } catch (Exception e) {
            Log.e("GnssSkyView", "Error loading flag resources. Make sure the flag images are in the drawable folder.", e);
        }

        setOnClickListener(v -> showSettingsDialog());
    }

    public void setGnssStatus(@NonNull GnssStatus status) {
        this.gnssStatus = status;
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(centerX, centerY);

        paint.setColor(skyColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius, paint);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawCircle(centerX, centerY, radius, paint);
        canvas.drawCircle(centerX, centerY, radius * 2 / 3f, paint);
        canvas.drawCircle(centerX, centerY, radius / 3f, paint);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("N", centerX, centerY - radius + 50, paint);

        if (gnssStatus == null) {
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("Aguardando sinal...", centerX, centerY, paint);
            return;
        }

        int visibleSatellites = 0, usedInFixCount = 0;
        for (int i = 0; i < gnssStatus.getSatelliteCount(); i++) {
            if (shouldDrawSatellite(gnssStatus.getConstellationType(i), gnssStatus.usedInFix(i))) {
                visibleSatellites++;
                if (gnssStatus.usedInFix(i)) usedInFixCount++;

                float azimuth = gnssStatus.getAzimuthDegrees(i);
                float elevation = gnssStatus.getElevationDegrees(i);
                float satRadius = (90 - elevation) / 90 * radius;
                double angle = Math.toRadians(azimuth - 90);
                float satX = (float) (centerX + satRadius * Math.cos(angle));
                float satY = (float) (centerY + satRadius * Math.sin(angle));
                drawSatellite(canvas, satX, satY, gnssStatus.getConstellationType(i), gnssStatus.usedInFix(i), gnssStatus.getSvid(i));
            }
        }

        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Visible: " + visibleSatellites, 10, getHeight() - 40, paint);
        canvas.drawText("In Fix: " + usedInFixCount, 10, getHeight() - 10, paint);
    }

    private void drawSatellite(@NonNull Canvas canvas, float x, float y, int constellation, boolean used, int svid) {
        paint.setStyle(used ? Paint.Style.FILL : Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        Bitmap flag = null;
        switch (constellation) {
            case GnssStatus.CONSTELLATION_GPS:
                paint.setColor(Color.GREEN);
                canvas.drawRect(x - 10, y - 10, x + 10, y + 10, paint);
                flag = flagUsa;
                break;
            case GnssStatus.CONSTELLATION_GLONASS:
                paint.setColor(Color.RED);
                canvas.drawCircle(x, y, 10, paint);
                flag = flagRussia;
                break;
            case GnssStatus.CONSTELLATION_GALILEO:
                paint.setColor(Color.CYAN);
                Path galileoPath = new Path();
                galileoPath.moveTo(x, y - 12); galileoPath.lineTo(x - 12, y + 6); galileoPath.lineTo(x + 12, y + 6); galileoPath.close();
                canvas.drawPath(galileoPath, paint);
                flag = flagEu;
                break;
            case GnssStatus.CONSTELLATION_BEIDOU:
                paint.setColor(Color.YELLOW);
                Path beidouPath = new Path();
                for (int j = 0; j < 5; j++) {
                    float angle = (float) Math.toRadians(72 * j - 90);
                    float px = (float) (x + 12 * Math.cos(angle));
                    float py = (float) (y + 12 * Math.sin(angle));
                    if (j == 0) beidouPath.moveTo(px, py); else beidouPath.lineTo(px, py);
                }
                beidouPath.close();
                canvas.drawPath(beidouPath, paint);
                flag = flagChina;
                break;
        }

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(20);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.valueOf(svid), x, y + 30, paint);

        if (flag != null) {
            float textWidth = paint.measureText(String.valueOf(svid));
            float flagX = x + textWidth / 2 + 5; // Padding
            float flagY = y + 15;
            RectF dstRect = new RectF(flagX, flagY, flagX + 40, flagY + 30);
            canvas.drawBitmap(flag, null, dstRect, null);
        }
    }

    private boolean shouldDrawSatellite(int constellation, boolean usedInFix) {
        if (usedInFix) {
            // For satellites in fix, only show if their constellation is enabled
            switch (constellation) {
                case GnssStatus.CONSTELLATION_GPS: return showGps;
                case GnssStatus.CONSTELLATION_GLONASS: return showGlonass;
                case GnssStatus.CONSTELLATION_GALILEO: return showGalileo;
                case GnssStatus.CONSTELLATION_BEIDOU: return showBeidou;
                default: return false;
            }
        } else {
            // For satellites NOT in fix, only depend on the global toggle
            return showNotInFix;
        }
    }

    private void showSettingsDialog() {
        String[] items = {"GPS", "GLONASS", "Galileo", "Beidou", "Show Not-in-Fix Satellites"};
        boolean[] checkedItems = {showGps, showGlonass, showGalileo, showBeidou, showNotInFix};
        new AlertDialog.Builder(getContext())
                .setTitle("Display Settings")
                .setMultiChoiceItems(items, checkedItems, (dialog, which, isChecked) -> {
                    if (which == 0) showGps = isChecked;
                    else if (which == 1) showGlonass = isChecked;
                    else if (which == 2) showGalileo = isChecked;
                    else if (which == 3) showBeidou = isChecked;
                    else if (which == 4) showNotInFix = isChecked;
                })
                .setPositiveButton("OK", (dialog, which) -> {
                    saveSettings();
                    invalidate();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void saveSettings() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_SHOW_GPS, showGps);
        editor.putBoolean(KEY_SHOW_GLONASS, showGlonass);
        editor.putBoolean(KEY_SHOW_GALILEO, showGalileo);
        editor.putBoolean(KEY_SHOW_BEIDOU, showBeidou);
        editor.putBoolean(KEY_SHOW_NOT_IN_FIX, showNotInFix);
        editor.apply();
    }

    private void loadSettings() {
        showGps = sharedPreferences.getBoolean(KEY_SHOW_GPS, true);
        showGlonass = sharedPreferences.getBoolean(KEY_SHOW_GLONASS, true);
        showGalileo = sharedPreferences.getBoolean(KEY_SHOW_GALILEO, true);
        showBeidou = sharedPreferences.getBoolean(KEY_SHOW_BEIDOU, true);
        showNotInFix = sharedPreferences.getBoolean(KEY_SHOW_NOT_IN_FIX, true);
    }
}
