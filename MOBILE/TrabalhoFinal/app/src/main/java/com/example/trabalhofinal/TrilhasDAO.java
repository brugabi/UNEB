package com.example.trabalhofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade {@link Trilha}.
 *
 * <p>Esta classe serve como uma camada de abstração entre a lógica de negócios da aplicação e
 * o banco de dados SQLite. Ela encapsula todas as operações de banco de dados (CRUD - Create,
 * Read, Update, Delete) relacionadas às trilhas e seus pontos de coordenadas, garantindo que a
 * complexidade do SQL e do gerenciamento de cursores seja isolada do resto do aplicativo.</p>
 *
 * <p>Utiliza a classe {@link TrilhasDBHelper} para criar e gerenciar o banco de dados e suas tabelas.</p>
 */
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
        db.beginTransaction();
        long trilhaId = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(TrilhasDBHelper.COLUMN_NOME, trilha.getNome());
            values.put(TrilhasDBHelper.COLUMN_DATA_HORA_INICIO, trilha.getDataHoraInicio());
            values.put(TrilhasDBHelper.COLUMN_DATA_HORA_FIM, trilha.getDataHoraFim());
            values.put(TrilhasDBHelper.COLUMN_DURACAO, trilha.getDuracao());
            values.put(TrilhasDBHelper.COLUMN_GASTO_CALORICO, trilha.getGastoCalorico());
            values.put(TrilhasDBHelper.COLUMN_VELOCIDADE_MEDIA, trilha.getVelocidadeMedia());
            values.put(TrilhasDBHelper.COLUMN_VELOCIDADE_MAXIMA, trilha.getVelocidadeMaxima());
            values.put(TrilhasDBHelper.COLUMN_DISTANCIA_TOTAL, trilha.getDistanciaTotal());
            values.put(TrilhasDBHelper.COLUMN_MAP_TYPE, trilha.getMapType());

            trilhaId = db.insert(TrilhasDBHelper.TABLE_TRILHAS, null, values);

            if (trilhaId != -1 && trilha.getCoordenadas() != null) {
                List<LatLng> coordenadas = trilha.getCoordenadas();
                for (int i = 0; i < coordenadas.size(); i++) {
                    LatLng ponto = coordenadas.get(i);
                    ContentValues pontoValues = new ContentValues();
                    pontoValues.put(TrilhasDBHelper.COLUMN_TRILHA_ID, trilhaId);
                    pontoValues.put(TrilhasDBHelper.COLUMN_LATITUDE, ponto.latitude);
                    pontoValues.put(TrilhasDBHelper.COLUMN_LONGITUDE, ponto.longitude);
                    pontoValues.put(TrilhasDBHelper.COLUMN_ORDEM, i);
                    db.insert(TrilhasDBHelper.TABLE_PONTOS, null, pontoValues);
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return trilhaId;
    }

    public int atualizarTrilha(Trilha trilha) {
        ContentValues values = new ContentValues();
        values.put(TrilhasDBHelper.COLUMN_NOME, trilha.getNome());
        return db.update(TrilhasDBHelper.TABLE_TRILHAS, values, TrilhasDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(trilha.getId())});
    }

    public void apagarTrilha(long trilhaId) {
        db.delete(TrilhasDBHelper.TABLE_TRILHAS, TrilhasDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(trilhaId)});
    }

    public void apagarTrilhas(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        String idsString = TextUtils.join(",", ids);
        db.delete(TrilhasDBHelper.TABLE_TRILHAS,
                TrilhasDBHelper.COLUMN_ID + " IN (" + idsString + ")",
                null);
    }

    public void apagarTodasAsTrilhas() {
        db.delete(TrilhasDBHelper.TABLE_TRILHAS, null, null);
    }


    /**
     * Recupera todas as trilhas salvas no banco de dados.
     * As trilhas são ordenadas pela data de início, da mais recente para a mais antiga.
     * <p>
     * <b>Nota de performance:</b> Este método não carrega a lista de coordenadas de cada trilha
     * para otimizar o carregamento da lista principal. As coordenadas são carregadas sob demanda.
     *
     * @return Uma {@link List} de objetos {@link Trilha}.
     */
    public List<Trilha> getAllTrilhas() {
        List<Trilha> trilhas = new ArrayList<>();
        Cursor cursor = db.query(TrilhasDBHelper.TABLE_TRILHAS, null, null, null, null, null, TrilhasDBHelper.COLUMN_DATA_HORA_INICIO + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Trilha trilha = cursorToTrilha(cursor, false);
            trilhas.add(trilha);
            cursor.moveToNext();
        }
        cursor.close();
        return trilhas;
    }

    /**
     * Recupera uma única trilha pelo seu ID, incluindo todos os seus pontos de coordenadas.
     *
     * @param id O ID da trilha a ser buscada.
     * @return Um objeto {@link Trilha} completo com seus dados e coordenadas, ou {@code null} se não for encontrada.
     */
    public Trilha getTrilhaById(long id) {
        Cursor cursor = db.query(TrilhasDBHelper.TABLE_TRILHAS, null,
                TrilhasDBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    return cursorToTrilha(cursor, true);
                }
            } finally {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * Método auxiliar para mapear uma linha do {@link Cursor} para um objeto {@link Trilha}.
     *
     * @param cursor O Cursor posicionado na linha correta.
     * @param carregarPontos Se {@code true}, o método também buscará e associará a lista de coordenadas da trilha.
     * @return Um objeto {@link Trilha} populado com os dados do cursor.
     */
    private Trilha cursorToTrilha(Cursor cursor, boolean carregarPontos) {
        Trilha trilha = new Trilha();
        long trilhaId = cursor.getLong(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_ID));
        trilha.setId(trilhaId);
        trilha.setNome(cursor.getString(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_NOME)));
        trilha.setDataHoraInicio(cursor.getString(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_DATA_HORA_INICIO)));
        trilha.setDataHoraFim(cursor.getString(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_DATA_HORA_FIM)));
        trilha.setDuracao(cursor.getString(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_DURACAO))); // CARREGANDO DURAÇÃO
        trilha.setGastoCalorico(cursor.getFloat(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_GASTO_CALORICO)));
        trilha.setVelocidadeMedia(cursor.getFloat(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_VELOCIDADE_MEDIA)));
        trilha.setVelocidadeMaxima(cursor.getFloat(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_VELOCIDADE_MAXIMA)));
        trilha.setDistanciaTotal(cursor.getFloat(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_DISTANCIA_TOTAL)));
        trilha.setMapType(cursor.getInt(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_MAP_TYPE)));

        if (carregarPontos) {
            trilha.setCoordenadas(getPontosDaTrilha(trilhaId));
        }

        return trilha;
    }

    /**
     * Método auxiliar para buscar todos os pontos de coordenadas associados a uma trilha específica.
     *
     * @param trilhaId O ID da trilha cujos pontos devem ser recuperados.
     * @return Uma {@link List} de objetos {@link LatLng}, ordenada pela coluna 'ordem'.
     */
    private List<LatLng> getPontosDaTrilha(long trilhaId) {
        List<LatLng> pontos = new ArrayList<>();
        Cursor cursor = db.query(TrilhasDBHelper.TABLE_PONTOS,
                new String[]{TrilhasDBHelper.COLUMN_LATITUDE, TrilhasDBHelper.COLUMN_LONGITUDE},
                TrilhasDBHelper.COLUMN_TRILHA_ID + " = ?",
                new String[]{String.valueOf(trilhaId)},
                null, null, TrilhasDBHelper.COLUMN_ORDEM + " ASC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            double lat = cursor.getDouble(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_LATITUDE));
            double lng = cursor.getDouble(cursor.getColumnIndexOrThrow(TrilhasDBHelper.COLUMN_LONGITUDE));
            pontos.add(new LatLng(lat, lng));
            cursor.moveToNext();
        }
        cursor.close();
        return pontos;
    }
}
