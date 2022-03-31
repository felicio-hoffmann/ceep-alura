package com.example.ceep.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceep.R;
import com.example.ceep.model.Nota;

import java.util.List;

public class ListaNotasAdapter extends RecyclerView.Adapter {
    private final List<Nota> notas;

    public ListaNotasAdapter(List<Nota> notas) {
        this.notas = notas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Nota nota = notas.get(position);
        TextView tituloTextView = holder.itemView.findViewById(R.id.item_nota_titulo);
        TextView descricaoTextView = holder.itemView.findViewById(R.id.item_nota_descricao);
        tituloTextView.setText(nota.getTitulo());
        descricaoTextView.setText(nota.getDescricao());
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public class NotaViewHolder extends RecyclerView.ViewHolder {
        public NotaViewHolder(View itemView) {
            super(itemView);
        }

    }
}
