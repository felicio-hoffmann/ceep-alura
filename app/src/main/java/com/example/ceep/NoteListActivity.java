package com.example.ceep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceep.dao.NotaDAO;
import com.example.ceep.model.Nota;
import com.example.ceep.ui.adapter.ListaNotasAdapter;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        NotaDAO dao = new NotaDAO();
        dao.insere(new Nota("primeira nota", "descrição da primeira nota"),
                new Nota("compras", "banana aveia iogurte macarrao carne e lima"));
        List<Nota> todasNotas = dao.todos();
        configuraRecyclerView(todasNotas);

        TextView insereNota = findViewById(R.id.lista_notas_insere_nota);
        configuraNovaNota(insereNota);
    }

    private void configuraNovaNota(TextView insereNota) {
        insereNota.setOnClickListener(view -> {
            Intent intent = new Intent(NoteListActivity.this, NovaNotaActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotaDAO dao = new NotaDAO();
        List<Nota> todasNotas = dao.todos();
        configuraRecyclerView(todasNotas);
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.recylerview_lista_notas);
        listaNotas.setAdapter(new ListaNotasAdapter(todasNotas));
    }
}