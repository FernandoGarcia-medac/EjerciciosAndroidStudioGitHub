package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QuizRanking.db";
    private static final int DATABASE_VERSION = 9;

    private static final String TABLE_RANKING = "ranking";
    private static final String COL_ID = "id";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_PUNTUACION = "puntuacion";
    private static final String COL_TIMESTAMP = "timestamp";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_RANKING + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_NOMBRE + " TEXT NOT NULL," +
                    COL_PUNTUACION + " INTEGER NOT NULL," +
                    COL_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_RANKING;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.i("DBHelper", "Tabla de Ranking creada.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    public void guardarResultado(String nombre, int puntuacion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NOMBRE, nombre);
        values.put(COL_PUNTUACION, puntuacion);

        long newRowId = db.insert(TABLE_RANKING, null, values);
        Log.d("DBHelper", "Resultado guardado con ID: " + newRowId);

        // db.close();
    }


    public static class RankingEntry {
        public String nombre;
        public int puntuacion;
        public RankingEntry(String nombre, int puntuacion) {
            this.nombre = nombre;
            this.puntuacion = puntuacion;
        }
    }


    public List<RankingEntry> obtenerRanking(int limit) {
        List<RankingEntry> rankingList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT " + COL_NOMBRE + ", " + COL_PUNTUACION +
                " FROM " + TABLE_RANKING +
                " ORDER BY " + COL_PUNTUACION + " DESC, " + COL_TIMESTAMP + " ASC" +
                " LIMIT " + limit;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE));
                int puntuacion = cursor.getInt(cursor.getColumnIndexOrThrow(COL_PUNTUACION));

                rankingList.add(new RankingEntry(nombre, puntuacion));
            } while (cursor.moveToNext());
        }

        cursor.close();
        // db.close();

        return rankingList;
    }
}
