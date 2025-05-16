package com.example.catlogoarbol;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArbolAdapter arbolAdapter;
    private DatabaseHelper dbHelper;
    private EditText etBuscar;
    private List<Arbol> listaArboles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBuscar = findViewById(R.id.etBuscar);
        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fab = findViewById(R.id.fabAgregar);

        dbHelper = new DatabaseHelper(this);
        listaArboles = dbHelper.obtenerArbolesComoObjetos();

        arbolAdapter = new ArbolAdapter(this, listaArboles);
        recyclerView.setAdapter(arbolAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Filtro de búsqueda
        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arbolAdapter.filtrar(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DatosGeneralesActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaArboles = dbHelper.obtenerArbolesComoObjetos();
        arbolAdapter.actualizarLista(listaArboles);
    }
}
