package com.example.catlogoarbol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class HojasFloresFrutosActivity extends AppCompatActivity {

    EditText etHojas, etFlores, etFrutos;
    Spinner spMadurez, spEstadoHojas;
    Button btnSiguiente, btnRegresar;

    String[] madurezOpciones = {"Muy inmaduro", "Ligeramente inmaduro", "Maduro"};
    String[] estadoHojasOpciones = {"Hojas verdes", "Hojas amarillentas", "Hojas marchitas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hojas_flores_frutos);

        etHojas = findViewById(R.id.etHojas);
        etFlores = findViewById(R.id.etFlores);
        etFrutos = findViewById(R.id.etFrutos);
        spMadurez = findViewById(R.id.spMadurez);
        spEstadoHojas = findViewById(R.id.spEstadoHojas);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnRegresar = findViewById(R.id.btnRegresar);

        spMadurez.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, madurezOpciones));
        spEstadoHojas.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, estadoHojasOpciones));

        etHojas.setOnFocusChangeListener((v, hasFocus) -> mostrarSpinners());
        etFrutos.setOnFocusChangeListener((v, hasFocus) -> mostrarSpinners());

        btnSiguiente.setOnClickListener(v -> {
            int hojas = Integer.parseInt(etHojas.getText().toString());
            int flores = Integer.parseInt(etFlores.getText().toString());
            int frutos = Integer.parseInt(etFrutos.getText().toString());

            if (!esMultiploDe5(hojas) || !esMultiploDe5(flores) || !esMultiploDe5(frutos)) {
                Toast.makeText(this, "Los porcentajes deben ser múltiplos de 5", Toast.LENGTH_SHORT).show();
                return;
            }

            String madurez = (frutos > 0) ? spMadurez.getSelectedItem().toString() : "";
            String estadoHojas = (hojas > 0) ? spEstadoHojas.getSelectedItem().toString() : "";

            // Recibir datos anteriores
            Intent prev = getIntent();
            Intent next = new Intent(this, InteraccionActivity.class);

            next.putExtras(prev); // Pasar todos los datos previos
            next.putExtra("hojas", hojas);
            next.putExtra("flores", flores);
            next.putExtra("frutos", frutos);
            next.putExtra("madurez", madurez);
            next.putExtra("estadoHojas", estadoHojas);

            startActivity(next);
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
            // Ignorar mientras el campo esté vacío
        }
    }
}