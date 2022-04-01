package com.example.ceep.activity;

import static com.example.ceep.activity.Constantes.CODIGO_RESULTADO_NOTA_CRIADA;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ceep.R;
import com.example.ceep.model.Nota;

public class NovaNotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_nota);
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
        resultado.putExtra(getString(R.string.chave_nota), novaNota);
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