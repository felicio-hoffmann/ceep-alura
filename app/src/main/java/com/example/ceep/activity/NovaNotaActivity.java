package com.example.ceep.activity;

import static com.example.ceep.activity.Constantes.CHAVE_NOTA;
import static com.example.ceep.activity.Constantes.CODIGO_RESULTADO_NOTA_CRIADA;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ceep.R;
import com.example.ceep.model.Nota;

import java.io.Serializable;

public class NovaNotaActivity extends AppCompatActivity {

    private int posicaoRecebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_nota);
        Intent intent = getIntent();
        if (intent.hasExtra(CHAVE_NOTA) && intent.hasExtra("posicao")){
            Nota notaRecebida = (Nota) intent.getSerializableExtra(CHAVE_NOTA);
            TextView titulo = findViewById(R.id.formulario_nota_titulo);
            titulo.setText(notaRecebida.getTitulo());
            TextView descricao = findViewById(R.id.formulario_nota_descricao);
            posicaoRecebida = intent.getIntExtra("posicao", -1);
            descricao.setText(notaRecebida.getDescricao());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nova_nota_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (menuSalvaNota(item)){
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
        setResult(CODIGO_RESULTADO_NOTA_CRIADA, resultado);
    }

    @NonNull
    private Nota criaNotaComOsCamposPreenchidos() {
        EditText descricao = findViewById(R.id.formulario_nota_descricao);
        EditText titulo = findViewById(R.id.formulario_nota_titulo);
        Nota novaNota = new Nota(titulo.getText().toString(), descricao.getText().toString());
        return novaNota;
    }

    private boolean menuSalvaNota(@NonNull MenuItem item) {
        return item.getItemId() == R.id.botao_salvar_nota;
    }
}