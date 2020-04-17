package br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.R;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.banco.BDSQLiteHelper;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.model.Imagem;

public class ImagemActivity extends AppCompatActivity {

    private BDSQLiteHelper bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagem);
        bd = new BDSQLiteHelper(this);
        final EditText nome = (EditText) findViewById(R.id.edNome);
        final EditText descricao = (EditText) findViewById(R.id.edDescricao);
//        final EditText ano = (EditText) findViewById(R.id.edAno);
        Button novo = (Button) findViewById(R.id.btnAdd);
        novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Imagem imagem = new Imagem();
                imagem.setNome(nome.getText().toString());
                imagem.setDescricao(descricao.getText().toString());
                imagem.setCaminho("teste");
//                livro.setAno(Integer.parseInt(ano.getText().toString()));
                bd.addImagem(imagem);
                Intent intent = new Intent(ImagemActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
