package com.example.ceep.activity;

import static com.example.ceep.activity.Constantes.CODIGO_REQUISICAO_NOVA_NOTA;
import static com.example.ceep.activity.Constantes.CODIGO_RESULTADO_NOTA_CRIADA;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceep.R;
import com.example.ceep.dao.NotaDAO;
import com.example.ceep.model.Nota;
import com.example.ceep.ui.adapter.ListaNotasAdapter;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {


    private ListaNotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        NotaDAO dao = new NotaDAO();
        List<Nota> todasNotas = dao.todos();
        configuraRecyclerView(todasNotas);
        TextView insereNota = findViewById(R.id.lista_notas_insere_nota);
        configuraNovaNota(insereNota);
    }

    private void configuraNovaNota(TextView insereNota) {
        insereNota.setOnClickListener(view -> {
            Intent vaiParaNovaNota = new Intent(NoteListActivity.this, NovaNotaActivity.class);
            startActivityForResult(vaiParaNovaNota, CODIGO_REQUISICAO_NOVA_NOTA);
        });
    }
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_REQUISICAO_NOVA_NOTA
                && resultCode == CODIGO_RESULTADO_NOTA_CRIADA && data.hasExtra(getString(R.string.chave_nota))) {
            Nota nota = (Nota) data.getSerializableExtra(getString(R.string.chave_nota));
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