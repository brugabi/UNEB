
package com.example.trabalhofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class TrilhasDAO {

    private SQLiteDatabase db;
    private TrilhasDBHelper dbHelper;

    public TrilhasDAO(Context context) {
        dbHelper = new TrilhasDBHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long inserirTrilha(Trilha trilha) {
        ContentValues values = new ContentValues();
        values.put(TrilhasDBHelper.COLUMN_NOME, trilha.getNome());
        values.put(TrilhasDBHelper.COLUMN_DATA_HORA_INICIO, trilha.getDataHoraInicio());
        values.put(TrilhasDBHelper.COLUMN_DATA_HORA_FIM, trilha.getDataHoraFim());
        values.put(TrilhasDBHelper.COLUMN_GASTO_CALORICO, trilha.getGastoCalorico());
        values.put(TrilhasDBHelper.COLUMN_VELOCIDADE_MEDIA, trilha.getVelocidadeMedia());
        values.put(TrilhasDBHelper.COLUMN_VELOCIDADE_MAXIMA, trilha.getVelocidadeMaxima());
        values.put(TrilhasDBHelper.COLUMN_DISTANCIA_TOTAL, trilha.getDistanciaTotal());
        values.put(TrilhasDBHelper.COLUMN_PERCURSO, trilha.getPercurso());

        return db.insert(TrilhasDBHelper.TABLE_TRILHAS, null, values);
    }

    public int atualizarTrilha(Trilha trilha) {
        ContentValues values = new ContentValues();
        values.put(TrilhasDBHelper.COLUMN_NOME, trilha.getNome());
        // ... (adicione outros campos que podem ser atualizados)

        return db.update(TrilhasDBHelper.TABLE_TRILHAS, values, TrilhasDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(trilha.getId())});
    }

    public void apagarTrilha(long trilhaId) {
        db.delete(TrilhasDBHelper.TABLE_TRILHAS, TrilhasDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(trilhaId)});
    }

    public List<Trilha> getAllTrilhas() {
        List<Trilha> trilhas = new ArrayList<>();
        Cursor cursor = db.query(TrilhasDBHelper.TABLE_TRILHAS, null, null, null, null, null, TrilhasDBHelper.COLUMN_DATA_HORA_INICIO + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Trilha trilha = cursorToTrilha(cursor);
            trilhas.add(trilha);
            cursor.moveToNext();
        }
        cursor.close();
        return trilhas;
    }

    public Trilha getTrilhaById(long id) {
        Cursor cursor = db.query(TrilhasDBHelper.TABLE_TRILHAS, null,
                TrilhasDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.isFirst()) {
                Trilha trilha = cursorToTrilha(cursor);
                cursor.close();
                return trilha;
            }
        }
        return null;
    }

    private Trilha cursorToTrilha(Cursor cursor) {
        Trilha trilha = new Trilha();
        trilha.setId(cursor.getLong(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_ID)));
        trilha.setNome(cursor.getString(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_NOME)));
        trilha.setDataHoraInicio(cursor.getString(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_DATA_HORA_INICIO)));
        trilha.setDataHoraFim(cursor.getString(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_DATA_HORA_FIM)));
        trilha.setGastoCalorico(cursor.getFloat(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_GASTO_CALORICO)));
        trilha.setVelocidadeMedia(cursor.getFloat(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_VELOCIDADE_MEDIA)));
        trilha.setVelocidadeMaxima(cursor.getFloat(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_VELOCIDADE_MAXIMA)));
        trilha.setDistanciaTotal(cursor.getFloat(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_DISTANCIA_TOTAL)));
        trilha.setPercurso(cursor.getString(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_PERCURSO)));
        return trilha;
    }
}
