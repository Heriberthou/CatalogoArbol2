// Paso 1: Adaptador personalizado para mostrar los árboles

package com.example.catlogoarbol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ArbolAdapter extends RecyclerView.Adapter<ArbolAdapter.ArbolViewHolder> implements Filterable {

    private List<Arbol> listaCompleta;
    private List<Arbol> listaFiltrada;
    private Context context;

    public ArbolAdapter(Context context, List<Arbol> listaArboles) {
        this.context = context;
        this.listaCompleta = listaArboles;
        this.listaFiltrada = new ArrayList<>(listaArboles);
    }

    @NonNull
    @Override
    public ArbolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_arbol, parent, false);
        return new ArbolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArbolViewHolder holder, int position) {
        Arbol arbol = listaFiltrada.get(position);

        holder.tvNumero.setText("Árbol #" + arbol.getNumero());
        holder.tvNombreCientifico.setText(arbol.getNombreCientifico());
        holder.tvNombreComun.setText(arbol.getNombreComun());

        // Aquí deberías cargar la imagen desde URI o ruta local si tienes
        holder.imgArbol.setImageResource(R.drawable.ic_logo); // Por ahora, un ícono fijo
    }

    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }

    @Override
    public Filter getFilter() {
        return filtro;
    }

    private final Filter filtro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Arbol> filtrada = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filtrada.addAll(listaCompleta);
            } else {
                String filtroPatron = constraint.toString().toLowerCase().trim();
                for (Arbol a : listaCompleta) {
                    if (a.getNombreCientifico().toLowerCase().contains(filtroPatron) ||
                            a.getNombreComun().toLowerCase().contains(filtroPatron)) {
                        filtrada.add(a);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtrada;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaFiltrada.clear();
            listaFiltrada.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class ArbolViewHolder extends RecyclerView.ViewHolder {
        ImageView imgArbol;
        TextView tvNumero, tvNombreCientifico, tvNombreComun;

        public ArbolViewHolder(@NonNull View itemView) {
            super(itemView);
            imgArbol = itemView.findViewById(R.id.imgArbol);
            tvNumero = itemView.findViewById(R.id.tvNumero);
            tvNombreCientifico = itemView.findViewById(R.id.tvNombreCientifico);
            tvNombreComun = itemView.findViewById(R.id.tvNombreComun);
        }
    }
}
