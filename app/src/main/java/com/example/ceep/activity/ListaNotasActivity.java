package com.example.ceep.activity;

import static com.example.ceep.activity.Constantes.CHAVE_NOTA;
import static com.example.ceep.activity.Constantes.CHAVE_POSICAO;
import static com.example.ceep.activity.Constantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static com.example.ceep.activity.Constantes.CODIGO_REQUISICAO_NOVA_NOTA;
import static com.example.ceep.activity.Constantes.POSICAO_INVALIDA;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceep.R;
import com.example.ceep.dao.NotaDAO;
import com.example.ceep.helper.callback.NotaItemCallback;
import com.example.ceep.model.Nota;
import com.example.ceep.ui.adapter.ListaNotasAdapter;

import java.util.List;

public class ListaNotasActivity extends AppCompatActivity {

    private ListaNotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        setTitle("Notas");

        NotaDAO dao = new NotaDAO();
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
        if (ehInserenota(requestCode, data)) {
            if(resultCode == Activity.RESULT_OK) {
                Nota nota = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                new NotaDAO().insere(nota);
                adapter.adiciona(nota);
            }
        }
        if (ehAlteraNota(requestCode, data)) {
            if(resultCode == Activity.RESULT_OK) {
                Nota nota = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                int pos = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
                if (pos != POSICAO_INVALIDA) {
                    new NotaDAO().altera(pos, nota);
                    adapter.altera(pos, nota);
                } else {
                    Toast.makeText(this, "Ocorreu um erro na requisição da nota",
                            Toast.LENGTH_SHORT).show();
                    Log.e("edit note error", "onActivityResult: ");
                }
            }
        }
    }

    private boolean ehAlteraNota(int requestCode, @Nullable Intent data) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA &&
                data != null && data.hasExtra(CHAVE_NOTA);
    }

    private boolean ehInserenota(int requestCode, @Nullable Intent data) {
        return requestCode == CODIGO_REQUISICAO_NOVA_NOTA
                && data != null && data.hasExtra(CHAVE_NOTA);
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.recylerview_lista_notas);
        adapter = new ListaNotasAdapter(todasNotas);
        listaNotas.setAdapter(adapter);

        adapter.setOnItemClickListener((pos, nota) -> {
            Intent vaiParaEdição = new Intent(ListaNotasActivity.this,
                    NovaNotaActivity.class);
            vaiParaEdição.putExtra(CHAVE_NOTA, nota);
            vaiParaEdição.putExtra(CHAVE_POSICAO, pos);
            startActivityForResult(vaiParaEdição, CODIGO_REQUISICAO_ALTERA_NOTA);
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemCallback(adapter));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }
}