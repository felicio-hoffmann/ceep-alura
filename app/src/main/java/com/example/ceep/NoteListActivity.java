package com.example.ceep;

import android.os.Bundle;

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

        RecyclerView listaNotas = findViewById(R.id.recylerview_lista_notas);

        NotaDAO dao = new NotaDAO();
        for (int i = 1; i < 10001; i++){
            dao.insere(new Nota("Nota " + i,
                    "Descrição " + i));
        }

        List<Nota> todasNotas = dao.todos();

        listaNotas.setAdapter(new ListaNotasAdapter(todasNotas));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listaNotas.setLayoutManager(linearLayoutManager);
    }
}