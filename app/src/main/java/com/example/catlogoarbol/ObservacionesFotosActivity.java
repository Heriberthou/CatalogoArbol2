package com.example.catlogoarbol;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ObservacionesFotosActivity extends AppCompatActivity {

    EditText etObservaciones;
    Button btnGuardar, btnRegresar, btnTomarFoto;
    ImageView imagePreview;
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observaciones_fotos);

        etObservaciones = findViewById(R.id.etObservaciones);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        imagePreview = findViewById(R.id.imagePreview);
        dbHelper = new DatabaseHelper(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
    }


        try {
                    getPackageName() + ".provider",
                    photoFile);
    }
    }

        String observaciones = etObservaciones.getText().toString().trim();

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Toast.makeText(this, "Error: faltan datos", Toast.LENGTH_SHORT).show();
            return;
        }

        int numero = extras.getInt("numero");
        String nombreCientifico = extras.getString("nombreCientifico");
        String nombreComun = extras.getString("nombreComun");
        String coordenadas = extras.getString("coordenadas");
        float diametro = extras.getFloat("diametro");
        float altura = extras.getFloat("altura");
        int hojas = extras.getInt("hojas");
        int flores = extras.getInt("flores");
        int frutos = extras.getInt("frutos");
        String madurez = extras.getString("madurez");
        String estadoHojas = extras.getString("estadoHojas");
        String interaccion = extras.getString("interaccion");
        String organismo = extras.getString("organismo");

        boolean insertado = dbHelper.insertarArbolCompleto(
                numero, nombreCientifico, nombreComun, coordenadas,
                diametro, altura, hojas, flores, frutos,
                madurez, estadoHojas, interaccion, organismo,
        );

        if (insertado) {
            Toast.makeText(this, "Árbol guardado exitosamente 🌳", Toast.LENGTH_LONG).show();
            finishAffinity();
        } else {
            Toast.makeText(this, "Error al guardar en la base de datos", Toast.LENGTH_SHORT).show();
        }
    }
}