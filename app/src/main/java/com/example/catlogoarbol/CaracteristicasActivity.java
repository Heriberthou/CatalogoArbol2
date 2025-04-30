package com.example.catlogoarbol;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CaracteristicasActivity extends AppCompatActivity {

    EditText etDiametro, etAltura;
    Button btnSiguiente, btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caracteristicas);

        etDiametro = findViewById(R.id.etDiametro);
        etAltura = findViewById(R.id.etAltura);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Recuperar datos anteriores
        Intent intent = getIntent();
        int numero = intent.getIntExtra("numero", -1);
        String nombreCientifico = intent.getStringExtra("nombreCientifico");
        String nombreComun = intent.getStringExtra("nombreComun");
        String coordenadas = intent.getStringExtra("coordenadas");

        btnSiguiente.setOnClickListener(v -> {
            String diamStr = etDiametro.getText().toString().trim();
            String altStr = etAltura.getText().toString().trim();

            if (diamStr.isEmpty() || altStr.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            float diametro = Float.parseFloat(diamStr);
            float altura = Float.parseFloat(altStr);

            if (diametro < 10 || diametro > 100 || altura <= 0) {
                Toast.makeText(this, "Valores fuera de rango", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent next = new Intent(this, HojasFloresFrutosActivity.class);
            next.putExtra("numero", numero);
            next.putExtra("nombreCientifico", nombreCientifico);
            next.putExtra("nombreComun", nombreComun);
            next.putExtra("coordenadas", coordenadas);
            next.putExtra("diametro", diametro);
            next.putExtra("altura", altura);
            startActivity(next);
        });

        btnRegresar.setOnClickListener(v -> finish());
    }
}