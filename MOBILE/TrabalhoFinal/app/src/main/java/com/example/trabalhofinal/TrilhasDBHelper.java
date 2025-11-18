package com.example.trabalhofinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TrilhasDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trilhas.db";
    private static final int DATABASE_VERSION = 2; // VERSÃO INCREMENTADA

    public static final String TABLE_TRILHAS = "trilhas";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_DATA_HORA_INICIO = "data_hora_inicio";
    public static final String COLUMN_DATA_HORA_FIM = "data_hora_fim";
    public static final String COLUMN_GASTO_CALORICO = "gasto_calorico";
    public static final String COLUMN_VELOCIDADE_MEDIA = "velocidade_media";
    public static final String COLUMN_VELOCIDADE_MAXIMA = "velocidade_maxima";
    public static final String COLUMN_DISTANCIA_TOTAL = "distancia_total";
    public static final String COLUMN_PERCURSO = "percurso";
    public static final String COLUMN_MAP_TYPE = "map_type"; // NOVA COLUNA

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TRILHAS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOME + " TEXT, " +
                    COLUMN_DATA_HORA_INICIO + " TEXT, " +
                    COLUMN_DATA_HORA_FIM + " TEXT, " +
                    COLUMN_GASTO_CALORICO + " REAL, " +
                    COLUMN_VELOCIDADE_MEDIA + " REAL, " +
                    COLUMN_VELOCIDADE_MAXIMA + " REAL, " +
                    COLUMN_DISTANCIA_TOTAL + " REAL, " +
                    COLUMN_PERCURSO + " TEXT, " +
                    COLUMN_MAP_TYPE + " INTEGER" +
                    ");";

    public TrilhasDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRILHAS);
        onCreate(db);
    }
}
