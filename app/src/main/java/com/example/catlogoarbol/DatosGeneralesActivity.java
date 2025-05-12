package com.example.catlogoarbol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DatosGeneralesActivity extends AppCompatActivity {

    EditText etNumero, etNombreCientifico, etNombreComun, etCoordenadas;
    Button btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_generales);

        etNumero = findViewById(R.id.etNumero);
        etNombreCientifico = findViewById(R.id.etNombreCientifico);
        etNombreComun = findViewById(R.id.etNombreComun);
        etCoordenadas = findViewById(R.id.etCoordenadas);
        btnSiguiente = findViewById(R.id.btnSiguiente);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validación básica
                String numStr = etNumero.getText().toString().trim();
                if (numStr.isEmpty() || Integer.parseInt(numStr) <= 0) {
                    Toast.makeText(DatosGeneralesActivity.this, "Número debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obtener datos
                int numero = Integer.parseInt(numStr);
                String nombreCientifico = etNombreCientifico.getText().toString().trim();
                String nombreComun = etNombreComun.getText().toString().trim();
                String coordenadas = etCoordenadas.getText().toString().trim();

                // Enviar a siguiente pantalla
                Intent intent = new Intent(DatosGeneralesActivity.this, AtributosActivity.class);
                intent.putExtra("numero", numero);
                intent.putExtra("nombreCientifico", nombreCientifico);
                intent.putExtra("nombreComun", nombreComun);
                intent.putExtra("coordenadas", coordenadas);
                startActivity(intent);
            }
        });
        Button btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(v -> {
            // Puedes cerrarla o regresar al menú principal
            finish(); // Vuelve a la activity anterior (si existe en stack)
        });
    }
}