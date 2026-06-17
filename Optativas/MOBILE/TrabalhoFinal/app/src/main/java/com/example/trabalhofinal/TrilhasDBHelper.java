package com.example.trabalhofinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TrilhasDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trilhas.db";
    private static final int DATABASE_VERSION = 4; // VERSÃO INCREMENTADA PARA 4

    // Tabela Trilhas
    public static final String TABLE_TRILHAS = "trilhas";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_DATA_HORA_INICIO = "data_hora_inicio";
    public static final String COLUMN_DATA_HORA_FIM = "data_hora_fim";
    public static final String COLUMN_DURACAO = "duracao"; // NOVA COLUNA
    public static final String COLUMN_GASTO_CALORICO = "gasto_calorico";
    public static final String COLUMN_VELOCIDADE_MEDIA = "velocidade_media";
    public static final String COLUMN_VELOCIDADE_MAXIMA = "velocidade_maxima";
    public static final String COLUMN_DISTANCIA_TOTAL = "distancia_total";
    public static final String COLUMN_MAP_TYPE = "map_type";

    private static final String TABLE_TRILHAS_CREATE =
            "CREATE TABLE " + TABLE_TRILHAS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOME + " TEXT, " +
                    COLUMN_DATA_HORA_INICIO + " TEXT, " +
                    COLUMN_DATA_HORA_FIM + " TEXT, " +
                    COLUMN_DURACAO + " TEXT, " +
                    COLUMN_GASTO_CALORICO + " REAL, " +
                    COLUMN_VELOCIDADE_MEDIA + " REAL, " +
                    COLUMN_VELOCIDADE_MAXIMA + " REAL, " +
                    COLUMN_DISTANCIA_TOTAL + " REAL, " +
                    COLUMN_MAP_TYPE + " INTEGER" +
                    ");";

    public static final String TABLE_PONTOS = "pontos";
    public static final String COLUMN_PONTO_ID = "_id";
    public static final String COLUMN_TRILHA_ID = "trilha_id"; 
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_ORDEM = "ordem"; 

    private static final String TABLE_PONTOS_CREATE =
            "CREATE TABLE " + TABLE_PONTOS + " (" +
                    COLUMN_PONTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TRILHA_ID + " INTEGER, " +
                    COLUMN_LATITUDE + " REAL, " +
                    COLUMN_LONGITUDE + " REAL, " +
                    COLUMN_ORDEM + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_TRILHA_ID + ") REFERENCES " + TABLE_TRILHAS + "(" + COLUMN_ID + ") ON DELETE CASCADE" +
                    ");";

    public TrilhasDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_TRILHAS_CREATE);
        db.execSQL(TABLE_PONTOS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PONTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRILHAS);
        onCreate(db);
    }
    
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
