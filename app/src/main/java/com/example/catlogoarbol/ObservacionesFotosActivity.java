package com.example.catlogoarbol;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ObservacionesFotosActivity extends AppCompatActivity {

    EditText etObservaciones;
    Button btnGuardar, btnRegresar, btnTomarFoto;
    ImageView imagePreview;
    DatabaseHelper dbHelper;

    private Uri photoURI;
    private String currentPhotoPath;
    private ActivityResultLauncher<Uri> takePictureLauncher;
    private static final int REQUEST_CAMERA_PERMISSION = 101;

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

        initCameraLauncher();

        btnTomarFoto.setOnClickListener(v -> checkCameraPermission());

        btnGuardar.setOnClickListener(v -> guardarArbol());

        btnRegresar.setOnClickListener(v -> finish());
    }

    private void initCameraLauncher() {
        takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                result -> {
                    if (result) {
                        imagePreview.setScaleType(ImageView.ScaleType.CENTER_CROP); // cambia estilo
                        imagePreview.setImageURI(photoURI); // muestra la imagen real
                        pedirNombreParaFoto();
                    } else {
                        Toast.makeText(this, "No se capturó imagen", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            launchCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchCamera() {
        try {
            File photoFile = createImageFile();
            photoURI = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".provider",
                    photoFile);
            takePictureLauncher.launch(photoURI);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al crear archivo de imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void pedirNombreParaFoto() {
        final EditText editText = new EditText(this);
        editText.setHint("Nombre para la foto");

        new AlertDialog.Builder(this)
                .setTitle("¿Guardar la foto?")
                .setMessage("Si te gusta la foto, ingresa un nombre para guardarla.\nSi no, presiona 'Descartar'.")
                .setView(editText)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String nuevoNombre = editText.getText().toString().trim();
                    if (!nuevoNombre.isEmpty()) {
                        renombrarFoto(nuevoNombre);
                    } else {
                        Toast.makeText(this, "Nombre vacío, no se guardó", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Descartar", (dialog, which) -> descartarFoto())
                .setCancelable(false)
                .show();
    }

    private void descartarFoto() {
        File file = new File(currentPhotoPath);
        if (file.exists() && file.delete()) {
            Toast.makeText(this, "Foto descartada", Toast.LENGTH_SHORT).show();
            imagePreview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imagePreview.setImageResource(R.drawable.ic_camera); // vuelve a mostrar el ícono
            currentPhotoPath = "";
        } else {
            Toast.makeText(this, "No se pudo eliminar la foto", Toast.LENGTH_SHORT).show();
        }
    }


    private void renombrarFoto(String nuevoNombre) {
        File originalFile = new File(currentPhotoPath);
        File nuevoArchivo = new File(originalFile.getParent(), nuevoNombre + ".jpg");

        if (originalFile.renameTo(nuevoArchivo)) {
            currentPhotoPath = nuevoArchivo.getAbsolutePath();
            Toast.makeText(this, "Foto guardada como: " + nuevoNombre + ".jpg", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al renombrar la foto", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarArbol() {
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
                observaciones, currentPhotoPath
        );

        if (insertado) {
            Toast.makeText(this, "Árbol guardado exitosamente 🌳", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error al guardar en la base de datos", Toast.LENGTH_SHORT).show();
        }

    }
}