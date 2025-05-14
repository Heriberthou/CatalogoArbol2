package com.example.catlogoarbol;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AtributosActivity extends AppCompatActivity {

    EditText etDiametro, etAltura, etHojas, etFlores, etFrutos;
    Spinner spMadurez, spEstadoHojas;
    Button btnSiguiente, btnRegresar;

    String[] madurezOpciones = {"Muy inmaduro", "Ligeramente inmaduro", "Maduro"};
    String[] estadoHojasOpciones = {"Hojas verdes", "Hojas amarillentas", "Hojas marchitas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atributos);

        // Vincular vistas
        etDiametro = findViewById(R.id.etDiametro);
        etAltura = findViewById(R.id.etAltura);
        etHojas = findViewById(R.id.etHojas);
        etFlores = findViewById(R.id.etFlores);
        etFrutos = findViewById(R.id.etFrutos);
        spMadurez = findViewById(R.id.spMadurez);
        spEstadoHojas = findViewById(R.id.spEstadoHojas);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Adaptadores para spinners
        spMadurez.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, madurezOpciones));
        spEstadoHojas.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, estadoHojasOpciones));

        // Mostrar spinners al enfocar
        etHojas.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mostrarSpinners();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        etFrutos.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mostrarSpinners();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });

        // Datos que vienen de actividad anterior
        Intent intent = getIntent();
        int numero = intent.getIntExtra("numero", -1);
        String nombreCientifico = intent.getStringExtra("nombreCientifico");
        String nombreComun = intent.getStringExtra("nombreComun");
        String coordenadas = intent.getStringExtra("coordenadas");

        btnSiguiente.setOnClickListener(v -> {
            try {
                // Validar entradas de diámetro y altura
                float diametro = Float.parseFloat(etDiametro.getText().toString().trim());
                float altura = Float.parseFloat(etAltura.getText().toString().trim());

                if (diametro < 10 || diametro > 100 || altura <= 0) {
                    Toast.makeText(this, "Valores de diámetro o altura fuera de rango", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar % de hojas, flores, frutos
                int hojas = Integer.parseInt(etHojas.getText().toString());
                int flores = Integer.parseInt(etFlores.getText().toString());
                int frutos = Integer.parseInt(etFrutos.getText().toString());

                if (!esMultiploDe5(hojas) || !esMultiploDe5(flores) || !esMultiploDe5(frutos)) {
                    Toast.makeText(this, "Los porcentajes deben ser múltiplos de 5 entre 0 y 100", Toast.LENGTH_SHORT).show();
                    return;
                }

                String madurez = (frutos > 0) ? spMadurez.getSelectedItem().toString() : "";
                String estadoHojas = (hojas > 0) ? spEstadoHojas.getSelectedItem().toString() : "";

                // Pasar a la siguiente actividad
                Intent next = new Intent(this, InteraccionActivity.class);
                next.putExtra("numero", numero);
                next.putExtra("nombreCientifico", nombreCientifico);
                next.putExtra("nombreComun", nombreComun);
                next.putExtra("coordenadas", coordenadas);
                next.putExtra("diametro", diametro);
                next.putExtra("altura", altura);
                next.putExtra("hojas", hojas);
                next.putExtra("flores", flores);
                next.putExtra("frutos", frutos);
                next.putExtra("madurez", madurez);
                next.putExtra("estadoHojas", estadoHojas);

                startActivity(next);

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Por favor completa todos los campos correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegresar.setOnClickListener(v -> finish());
    }

    private boolean esMultiploDe5(int val) {
        return val % 5 == 0 && val >= 0 && val <= 100;
    }

    private void mostrarSpinners() {
        try {
            int frutos = Integer.parseInt(etFrutos.getText().toString());
            int hojas = Integer.parseInt(etHojas.getText().toString());

            spMadurez.setVisibility((frutos > 0) ? View.VISIBLE : View.GONE);
            spEstadoHojas.setVisibility((hojas > 0) ? View.VISIBLE : View.GONE);
        } catch (NumberFormatException e) {
            // Ignorar mientras estén vacíos
        }
    }
}
