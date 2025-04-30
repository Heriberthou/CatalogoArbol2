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

    String fotoPath = "";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_CAMERA_PERMISSION = 101;

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

        // PEDIR PERMISO SI FALTA
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }

        // BOTÓN TOMAR FOTO
        btnTomarFoto.setOnClickListener(v -> {
            Toast.makeText(this, "Botón presionado", Toast.LENGTH_SHORT).show();

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Toast.makeText(this, "Error al crear archivo de imagen", Toast.LENGTH_SHORT).show();
                }

                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            getPackageName() + ".provider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        // BOTÓN GUARDAR
        btnGuardar.setOnClickListener(v -> {
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
                    observaciones, fotoPath
            );

            if (insertado) {
                Toast.makeText(this, "Árbol guardado exitosamente 🌳", Toast.LENGTH_LONG).show();
                finishAffinity();
            } else {
                Toast.makeText(this, "Error al guardar en la base de datos", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegresar.setOnClickListener(v -> finish());
    }

    // MANEJAR RESPUESTA DE PERMISOS
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Se requiere permiso de cámara", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // CREAR ARCHIVO PARA GUARDAR LA FOTO
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        fotoPath = image.getAbsolutePath();
        return image;
    }

    // RECIBIR FOTO Y MOSTRAR PREVISUALIZACIÓN
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imagePreview.setImageURI(Uri.fromFile(new File(fotoPath)));
        }
    }
}