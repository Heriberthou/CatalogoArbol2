package com.example.catlogoarbol;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class DatosGeneralesActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    EditText etNumero, etNombreCientifico, etNombreComun, etCoordenadas;
    Button btnSiguiente, btnObtenerCoordenadas, btnRegresar;

    FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_generales);

        // Referencias
        etNumero = findViewById(R.id.etNumero);
        etNombreCientifico = findViewById(R.id.etNombreCientifico);
        etNombreComun = findViewById(R.id.etNombreComun);
        etCoordenadas = findViewById(R.id.etCoordenadas);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnObtenerCoordenadas = findViewById(R.id.btnObtenerCoordenadas);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Inicializar servicio de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Botón para obtener ubicación
        btnObtenerCoordenadas.setOnClickListener(v -> obtenerUbicacion());

        // Botón siguiente
        btnSiguiente.setOnClickListener(v -> {
            String numStr = etNumero.getText().toString().trim();
            if (numStr.isEmpty() || Integer.parseInt(numStr) <= 0) {
                Toast.makeText(this, "Número debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                return;
            }

            int numero = Integer.parseInt(numStr);
            String nombreCientifico = etNombreCientifico.getText().toString().trim();
            String nombreComun = etNombreComun.getText().toString().trim();
            String coordenadas = etCoordenadas.getText().toString().trim();

            Intent intent = new Intent(this, AtributosActivity.class);
            intent.putExtra("numero", numero);
            intent.putExtra("nombreCientifico", nombreCientifico);
            intent.putExtra("nombreComun", nombreComun);
            intent.putExtra("coordenadas", coordenadas);
            startActivity(intent);
        });

        // Botón regresar
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void obtenerUbicacion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    etCoordenadas.setText(lat + ", " + lon);
                } else {
                    Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacion();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
