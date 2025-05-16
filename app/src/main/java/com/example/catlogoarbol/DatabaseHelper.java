package com.example.catlogoarbol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "catalogo_arboles.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ARBOL = "Arbol";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLA_ARBOL = "CREATE TABLE " + TABLE_ARBOL + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "numero INTEGER,"
                + "nombreCientifico TEXT,"
                + "nombreComun TEXT,"
                + "coordenadas TEXT,"
                + "diametro REAL,"
                + "altura REAL,"
                + "hojas TEXT,"
                + "flores TEXT,"
                + "frutos TEXT,"
                + "madurez TEXT,"
                + "estadoHojas TEXT,"
                + "interaccion TEXT,"
                + "organismo TEXT,"
                + "observaciones TEXT,"
                + "imagenUri TEXT"
                + ")";
        db.execSQL(CREATE_TABLA_ARBOL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARBOL);
        onCreate(db);
    }

    public void insertarArbol(Arbol arbol) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("numero", arbol.getNumero());
        values.put("nombreCientifico", arbol.getNombreCientifico());
        values.put("nombreComun", arbol.getNombreComun());
        values.put("coordenadas", arbol.getCoordenadas());
        values.put("diametro", arbol.getDiametro());
        values.put("altura", arbol.getAltura());
        values.put("hojas", arbol.getHojas());
        values.put("flores", arbol.getFlores());
        values.put("frutos", arbol.getFrutos());
        values.put("madurez", arbol.getMadurez());
        values.put("estadoHojas", arbol.getEstadoHojas());
        values.put("interaccion", arbol.getInteraccion());
        values.put("organismo", arbol.getOrganismo());
        values.put("observaciones", arbol.getObservaciones());
        values.put("imagenUri", arbol.getImagenUri());

        db.insert(TABLE_ARBOL, null, values);
        db.close();
    }

    public List<Arbol> obtenerArbolesComoObjetos() {
        List<Arbol> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ARBOL, null);

        if (cursor.moveToFirst()) {
            do {
                int numero = cursor.getInt(cursor.getColumnIndexOrThrow("numero"));
                String nombreCientifico = cursor.getString(cursor.getColumnIndexOrThrow("nombreCientifico"));
                String nombreComun = cursor.getString(cursor.getColumnIndexOrThrow("nombreComun"));
                String coordenadas = cursor.getString(cursor.getColumnIndexOrThrow("coordenadas"));
                float diametro = cursor.getFloat(cursor.getColumnIndexOrThrow("diametro"));
                float altura = cursor.getFloat(cursor.getColumnIndexOrThrow("altura"));
                String hojas = cursor.getString(cursor.getColumnIndexOrThrow("hojas"));
                String flores = cursor.getString(cursor.getColumnIndexOrThrow("flores"));
                String frutos = cursor.getString(cursor.getColumnIndexOrThrow("frutos"));
                String madurez = cursor.getString(cursor.getColumnIndexOrThrow("madurez"));
                String estadoHojas = cursor.getString(cursor.getColumnIndexOrThrow("estadoHojas"));
                String interaccion = cursor.getString(cursor.getColumnIndexOrThrow("interaccion"));
                String organismo = cursor.getString(cursor.getColumnIndexOrThrow("organismo"));
                String observaciones = cursor.getString(cursor.getColumnIndexOrThrow("observaciones"));

                String imagenUri = "";
                int imagenUriIndex = cursor.getColumnIndex("imagenUri");
                if (imagenUriIndex != -1) {
                    imagenUri = cursor.getString(imagenUriIndex);
                }

                Arbol arbol = new Arbol(numero, nombreCientifico, nombreComun, coordenadas,
                        diametro, altura, hojas, flores, frutos, madurez, estadoHojas,
                        interaccion, organismo, observaciones, imagenUri);

                lista.add(arbol);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }
}
