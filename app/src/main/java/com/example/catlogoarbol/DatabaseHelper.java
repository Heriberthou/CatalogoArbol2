package com.example.catlogoarbol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "catalogo_arbol.db";
    private static final int DATABASE_VERSION = 3; // Sube para forzar actualización

    public static final String TABLE_NAME = "arboles";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_FOTO = "foto"; // NUEVO

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "numero INTEGER, " +
                    "nombreCientifico TEXT, " +
                    "nombreComun TEXT, " +
                    "coordenadas TEXT, " +
                    "diametro REAL, " +
                    "altura REAL, " +
                    "hojas INTEGER, " +
                    "flores INTEGER, " +
                    "frutos INTEGER, " +
                    "madurez TEXT, " +
                    "estadoHojas TEXT, " +
                    "interaccion TEXT, " +
                    "organismo TEXT, " +
                    "observaciones TEXT, " +
                    COLUMN_NOMBRE + " TEXT, " +
                    COLUMN_DESCRIPCION + " TEXT, " +
                    COLUMN_FOTO + " TEXT" + // NUEVA COLUMNA
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertarArbol(String nombre, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_DESCRIPCION, descripcion);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean insertarArbolCompleto(int numero, String nombreCientifico, String nombreComun, String coordenadas,
                                         float diametro, float altura, int hojas, int flores, int frutos,
                                         String madurez, String estadoHojas, String interaccion, String organismo,
                                         String observaciones, String fotoUri) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("numero", numero);
        values.put("nombreCientifico", nombreCientifico);
        values.put("nombreComun", nombreComun);
        values.put("coordenadas", coordenadas);
        values.put("diametro", diametro);
        values.put("altura", altura);
        values.put("hojas", hojas);
        values.put("flores", flores);
        values.put("frutos", frutos);
        values.put("madurez", madurez);
        values.put("estadoHojas", estadoHojas);
        values.put("interaccion", interaccion);
        values.put("organismo", organismo);
        values.put("observaciones", observaciones);
        values.put(COLUMN_NOMBRE, nombreCientifico); // compatibilidad
        values.put(COLUMN_DESCRIPCION, nombreComun);
        values.put(COLUMN_FOTO, fotoUri); // GUARDAR RUTA DE FOTO

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public ArrayList<String> obtenerTodosLosArboles() {
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION));
                lista.add(nombre + ": " + descripcion);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    public List<Arbol> obtenerArbolesComoObjetos() {
        List<Arbol> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                int numero = cursor.getInt(cursor.getColumnIndexOrThrow("numero"));
                String nombreCientifico = cursor.getString(cursor.getColumnIndexOrThrow("nombreCientifico"));
                String nombreComun = cursor.getString(cursor.getColumnIndexOrThrow("nombreComun"));
                String fotoUri = cursor.getString(cursor.getColumnIndexOrThrow("foto")); // puede estar null

                lista.add(new Arbol(numero, nombreCientifico, nombreComun, fotoUri));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }

}