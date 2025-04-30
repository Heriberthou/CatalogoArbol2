package com.example.catlogoarbol;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText etNombre, etDescripcion;
    Button btnGuardar;
    ListView listaArboles;
    ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);
        listaArboles = findViewById(R.id.listaArboles);

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                boolean insertado = dbHelper.insertarArbol(nombre, descripcion);
                if (insertado) {
                    Toast.makeText(this, "Árbol guardado con éxito", Toast.LENGTH_SHORT).show();
                    etNombre.setText("");
                    etDescripcion.setText("");
                    mostrarArboles(); // ✅ actualiza la lista
                } else {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mostrarArboles(); // ✅ mostrar al iniciar
    }
    private void mostrarArboles() {
        ArrayList<String> lista = dbHelper.obtenerTodosLosArboles();
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listaArboles.setAdapter(adaptador);
    }
}