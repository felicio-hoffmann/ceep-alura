package com.example.ceep.activity;

import static com.example.ceep.activity.Constantes.CHAVE_NOTA;
import static com.example.ceep.activity.Constantes.CODIGO_REQUISICAO_NOVA_NOTA;
import static com.example.ceep.activity.Constantes.CODIGO_RESULTADO_NOTA_CRIADA;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceep.R;
import com.example.ceep.dao.NotaDAO;
import com.example.ceep.model.Nota;
import com.example.ceep.ui.adapter.ListaNotasAdapter;
import com.example.ceep.ui.adapter.listener.OnItemClickListener;

import java.util.List;

public class ListaNotasActivity extends AppCompatActivity {
    private ListaNotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        NotaDAO dao = new NotaDAO();
        for (int i = 0; i < 10; i++) {
            dao.insere(new Nota("Titulo " + (i+1), "Descrição " + (i+1)));
        }
        List<Nota> todasNotas = dao.todos();
        configuraRecyclerView(todasNotas);
        TextView insereNota = findViewById(R.id.lista_notas_insere_nota);
        configuraNovaNota(insereNota);
    }

    private void configuraNovaNota(TextView insereNota) {
        insereNota.setOnClickListener(view -> {
            Intent vaiParaNovaNota = new Intent(ListaNotasActivity.this, NovaNotaActivity.class);
            startActivityForResult(vaiParaNovaNota, CODIGO_REQUISICAO_NOVA_NOTA);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_REQUISICAO_NOVA_NOTA
                && resultCode == CODIGO_RESULTADO_NOTA_CRIADA && data.hasExtra(CHAVE_NOTA)) {
            Nota nota = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            new NotaDAO().insere(nota);
            adapter.adiciona(nota);
        }
        if (requestCode == 2 && resultCode == CODIGO_RESULTADO_NOTA_CRIADA &&
                data.hasExtra(CHAVE_NOTA) && data.hasExtra("posicao")) {
            Nota nota = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            int pos = data.getIntExtra("posicao", -1);
            new NotaDAO().altera(pos, nota);
            adapter.altera(pos, nota);
        }
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.recylerview_lista_notas);
        adapter = new ListaNotasAdapter(todasNotas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos, Nota nota) {
                Intent vaiParaEdição = new Intent(ListaNotasActivity.this,
                        NovaNotaActivity.class);
                vaiParaEdição.putExtra(CHAVE_NOTA, nota);
                vaiParaEdição.putExtra("posicao", pos);
                startActivityForResult(vaiParaEdição, 2);
            }
        });
    }
}