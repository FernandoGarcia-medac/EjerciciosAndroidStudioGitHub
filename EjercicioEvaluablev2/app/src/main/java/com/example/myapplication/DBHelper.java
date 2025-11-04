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
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "ranking";
    private static final String COL_ID = "id";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_PUNTUACION = "puntuacion";
    private static final String COL_FECHA = "fecha";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NOMBRE + " TEXT NOT NULL, " +
            COL_PUNTUACION + " INTEGER NOT NULL, " +
            COL_FECHA + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ");";

    private static final String TAG = "DBHelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creando tabla: " + CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Actualizando BBDD de la versión " + oldVersion + " a " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean guardarResultado(String nombre, int puntuacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NOMBRE, nombre);
        values.put(COL_PUNTUACION, puntuacion);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        if (result != -1) {
            Log.d(TAG, "Resultado guardado: " + nombre + " con " + puntuacion + " puntos.");
        } else {
            Log.e(TAG, "Error al guardar el resultado.");
        }
        return result != -1;
    }

    public List<String> obtenerRanking() {
        List<String> rankingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COL_NOMBRE + ", " + COL_PUNTUACION +
                " FROM " + TABLE_NAME +
                " ORDER BY " + COL_PUNTUACION + " DESC, " + COL_FECHA + " ASC" +
                " LIMIT 10"; // Solo mostramos el TOP 10

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            int posicion = 1;

            if (cursor.moveToFirst()) {
                do {
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE));
                    int puntuacion = cursor.getInt(cursor.getColumnIndexOrThrow(COL_PUNTUACION));

                    String itemRanking = posicion + ". " + nombre + " - " + puntuacion + " / 10 puntos";
                    rankingList.add(itemRanking);
                    posicion++;
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e(TAG, "Error al obtener el ranking: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();
        }
        return rankingList;
    }
}
