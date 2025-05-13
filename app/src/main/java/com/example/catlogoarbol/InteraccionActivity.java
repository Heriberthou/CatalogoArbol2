package com.example.catlogoarbol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class InteraccionActivity extends AppCompatActivity {

    Spinner spInteraccion;
    EditText etOrganismo;
    View viewUnderline;
    Button btnSiguiente, btnRegresar;

    String[] opciones = {"Depredación", "Mutualismo", "Parasitismo", "Comensalismo", "Ninguna"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interaccion);

        // Vistas
        spInteraccion = findViewById(R.id.spInteraccion);
        etOrganismo = findViewById(R.id.etOrganismo);
        viewUnderline = findViewById(R.id.viewUnderline); // línea bajo el campo
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Adaptador con layout personalizado
        spInteraccion.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_item, opciones));

        // Mostrar el campo organismo si la opción seleccionada es diferente de "Ninguna"
        spInteraccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean mostrar = !opciones[position].equals("Ninguna");
                etOrganismo.setVisibility(mostrar ? View.VISIBLE : View.GONE);
                viewUnderline.setVisibility(mostrar ? View.VISIBLE : View.GONE);

                if (!mostrar) {
                    etOrganismo.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Botón Siguiente
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

        // Botón Regresar
        btnRegresar.setOnClickListener(v -> finish());
    }
}
