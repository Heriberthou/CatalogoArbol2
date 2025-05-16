package com.example.catlogoarbol;

import android.content.Context;
import android.net.Uri;
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

    private List<Arbol> listaArboles;
    private List<Arbol> listaArbolesFull;
    private Context context;

    public ArbolAdapter(Context context, List<Arbol> listaArboles) {
        this.context = context;
        this.listaArboles = listaArboles;
        this.listaArbolesFull = new ArrayList<>(listaArboles);
    }

    @NonNull
    @Override
    public ArbolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_arbol, parent, false);
        return new ArbolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArbolViewHolder holder, int position) {
        Arbol arbol = listaArboles.get(position);
        holder.tvNumero.setText("Árbol #" + arbol.getNumero());
        holder.tvNombreCientifico.setText(arbol.getNombreCientifico());
        holder.tvNombreComun.setText(arbol.getNombreComun());

        if (arbol.getImagenUri() != null && !arbol.getImagenUri().isEmpty()) {
            holder.imgArbol.setImageURI(Uri.parse(arbol.getImagenUri()));
        } else {
            holder.imgArbol.setImageResource(android.R.drawable.ic_menu_gallery); // ícono genérico si no hay imagen
        }
    }

    @Override
    public int getItemCount() {
        return listaArboles.size();
    }

    @Override
    public Filter getFilter() {
        return filtroArboles;
    }

    private final Filter filtroArboles = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Arbol> listaFiltrada = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                listaFiltrada.addAll(listaArbolesFull);
            } else {
                String filtro = constraint.toString().toLowerCase().trim();
                for (Arbol arbol : listaArbolesFull) {
                    if (arbol.getNombreCientifico().toLowerCase().contains(filtro) ||
                            arbol.getNombreComun().toLowerCase().contains(filtro)) {
                        listaFiltrada.add(arbol);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = listaFiltrada;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaArboles.clear();
            listaArboles.addAll((List<Arbol>) results.values);
            notifyDataSetChanged();
        }
    };

    public void filtrar(String texto) {
        getFilter().filter(texto);
    }

    public void actualizarLista(List<Arbol> nuevaLista) {
        this.listaArboles = nuevaLista;
        this.listaArbolesFull = new ArrayList<>(nuevaLista);
        notifyDataSetChanged();
    }

    class ArbolViewHolder extends RecyclerView.ViewHolder {
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
