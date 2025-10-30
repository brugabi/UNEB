package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class TrilhasDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "trilhas.db";
    private static final int DATABASE_VERSION = 1;

    public TrilhasDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void registrarWaypoint(Waypoint waypoint) {}
    public ArrayList<Waypoint> recuperarWaypoints() {
        return null;
    }
    public void apagaTrilha() {getWritableDatabase().execSQL("DELETE FROM waypoints");}

}
