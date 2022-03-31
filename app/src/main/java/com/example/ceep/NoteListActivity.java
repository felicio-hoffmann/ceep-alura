package com.example.ceep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceep.dao.NotaDAO;
import com.example.ceep.model.Nota;
import com.example.ceep.ui.adapter.ListaNotasAdapter;

import java.io.Serializable;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;
    private List<Nota> todasNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        NotaDAO dao = new NotaDAO();
        dao.insere(new Nota("primeira nota", "descrição da primeira nota"),
                new Nota("compras", "banana aveia iogurte macarrao carne e lima"));
        todasNotas = dao.todos();
        configuraRecyclerView(todasNotas);

        TextView insereNota = findViewById(R.id.lista_notas_insere_nota);
        configuraNovaNota(insereNota);
    }

    private void configuraNovaNota(TextView insereNota) {
        insereNota.setOnClickListener(view -> {
            Intent intent = new Intent(NoteListActivity.this, NovaNotaActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotaDAO dao = new NotaDAO();
        todasNotas.clear();
        todasNotas.addAll(dao.todos());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode ==2 && data.hasExtra("nota")){
            Nota nota = (Nota) data.getSerializableExtra("nota");
            new NotaDAO().insere(nota);
            adapter.adiciona(nota);
        }
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.recylerview_lista_notas);
        adapter = new ListaNotasAdapter(todasNotas);
        listaNotas.setAdapter(adapter);
    }
}