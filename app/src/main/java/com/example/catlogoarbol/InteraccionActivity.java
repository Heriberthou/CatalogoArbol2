package com.example.catlogoarbol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class InteraccionActivity extends AppCompatActivity {

    Spinner spInteraccion;
    EditText etOrganismo;
    Button btnSiguiente, btnRegresar;

    String[] opciones = {"Depredación", "Mutualismo", "Parasitismo", "Comensalismo", "Ninguna"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interaccion);

        spInteraccion = findViewById(R.id.spInteraccion);
        etOrganismo = findViewById(R.id.etOrganismo);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnRegresar = findViewById(R.id.btnRegresar);

        spInteraccion.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opciones));

        // Mostrar el campo organismo si la opción seleccionada es diferente de "Ninguna"
        spInteraccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!opciones[position].equals("Ninguna")) {
                    etOrganismo.setVisibility(View.VISIBLE);
                } else {
                    etOrganismo.setVisibility(View.GONE);
                    etOrganismo.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnSiguiente.setOnClickListener(v -> {
            String interaccion = spInteraccion.getSelectedItem().toString();
            String organismo = etOrganismo.getText().toString().trim();

            if (!interaccion.equals("Ninguna") && organismo.isEmpty()) {
                Toast.makeText(this, "Indica el organismo de interacción", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent prev = getIntent();
            Intent next = new Intent(this, ObservacionesFotosActivity.class);

            next.putExtras(prev); // Traer los anteriores
            next.putExtra("interaccion", interaccion);
            next.putExtra("organismo", organismo);

            startActivity(next);
        });

        btnRegresar.setOnClickListener(v -> finish());
    }
}