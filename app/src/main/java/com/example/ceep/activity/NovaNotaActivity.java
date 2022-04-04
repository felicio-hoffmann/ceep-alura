package com.example.ceep.activity;

import static com.example.ceep.activity.Constantes.CHAVE_NOTA;
import static com.example.ceep.activity.Constantes.CHAVE_POSICAO;
import static com.example.ceep.activity.Constantes.POSICAO_INVALIDA;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ceep.R;
import com.example.ceep.model.Nota;

public class NovaNotaActivity extends AppCompatActivity {

    private int posicaoRecebida = POSICAO_INVALIDA;
    private TextView titulo;
    private TextView descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_nota);
        buscaViews();
        Intent intent = getIntent();
        if (intent.hasExtra(CHAVE_NOTA)) {
            Nota notaRecebida = (Nota) intent.getSerializableExtra(CHAVE_NOTA);
            posicaoRecebida = intent.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            preencheCampos(notaRecebida);
        }
    }

    private void preencheCampos(Nota notaRecebida) {
        titulo.setText(notaRecebida.getTitulo());
        descricao.setText(notaRecebida.getDescricao());
    }

    private void buscaViews() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nova_nota_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (menuSalvaNota(item)) {
            Nota novaNota = criaNotaComOsCamposPreenchidos();
            retornaNota(novaNota);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Nota novaNota) {
        Intent resultado = new Intent();
        resultado.putExtra(CHAVE_NOTA, novaNota);
        resultado.putExtra("posicao", posicaoRecebida);
        setResult(Activity.RESULT_OK, resultado);
    }

    @NonNull
    private Nota criaNotaComOsCamposPreenchidos() {
        return new Nota(titulo.getText().toString(), descricao.getText().toString());
    }

    private boolean menuSalvaNota(@NonNull MenuItem item) {
        return item.getItemId() == R.id.botao_salvar_nota;
    }
}