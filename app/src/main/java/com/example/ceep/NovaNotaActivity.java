package com.example.ceep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.ceep.dao.NotaDAO;
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
        int itemId = item.getItemId();
        if (itemId == R.id.botao_salvar_nota){
            EditText descricao = findViewById(R.id.formulario_nota_descricao);
            EditText titulo = findViewById(R.id.formulario_nota_titulo);
            Nota novaNota = new Nota(titulo.getText().toString(), descricao.getText().toString());
            Intent resultado = new Intent();
            resultado.putExtra("nota", novaNota);
            setResult(2, resultado);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}