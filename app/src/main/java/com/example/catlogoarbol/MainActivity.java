package com.example.catlogoarbol;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArbolAdapter arbolAdapter;
    private SearchView searchView;
    private FloatingActionButton fabAgregar;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        fabAgregar = findViewById(R.id.fabAgregar);
        dbHelper = new DatabaseHelper(this);

        // Cargar datos reales
        List<Arbol> listaArboles = dbHelper.obtenerArbolesComoObjetos();
        arbolAdapter = new ArbolAdapter(this, listaArboles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(arbolAdapter);

        // Buscar en tiempo real
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arbolAdapter.getFilter().filter(newText);
                return false;
            }
        });

        fabAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DatosGeneralesActivity.class);
            startActivity(intent);
        });
    }
}
