package com.example.ejercicio8;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Config basica
    private static final String DATABSE_NAME = "DBProfesores.db";
    private static final int DATABASE_VERSION = 1;


    // Esquema

    private static final String TBL_PROFESORES = "Profesores";
    private static final String COL_ID = "id";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_APELLIDO = "apellido";


    private static final String SQL_CREATE =
            "CREATE TABLE " + TBL_PROFESORES + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NOMBRE + " TEXT, " +
                    COL_APELLIDO + " TEXT" +
                    ")";

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
    }


    //Metodo obligatorio: se ejecuta la primera vez que se crea la BD
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);

        //Inserta 2 profesores de ejemplo
        insertarProfesor(db, "Pilar", "Garcia");
        insertarProfesor(db, "Jose", "Lopez");
    }

    //Metodo obligatorio: no necesario en este ejemplo porque no habra cambio de version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TBL_PROFESORES");
        onCreate(db);
    }

    // ==== Métodos específicos ====
// Inserta profesor de ejemplo (usado en onCreate)
    private void insertarProfesor(SQLiteDatabase db, String nombre, String apellido) {
        ContentValues valores = new ContentValues();
        valores.put(COL_NOMBRE, nombre);
        valores.put(COL_APELLIDO, apellido);
        db.insert(TBL_PROFESORES, null, valores);
    }

    public boolean existeProfesor(String nombre, String apellido) {
        SQLiteDatabase db = getReadableDatabase();

        //Construimos manualmente la condicion WHERE
        String where = COL_NOMBRE + " = '" + nombre + "' AND " +COL_APELLIDO + " = '" + apellido + "'";

        try (Cursor c = db.query(
                TBL_PROFESORES,
                new String[]{COL_ID}, //Solo pedimos el id
                where,                // Usamos la condicion directamente
                null, null, null, null,
                "1"             //LIMITE 1
        )){
            return c.moveToFirst(); // true si hay al menos un registro

        }
    }







}
