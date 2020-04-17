package br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.activity;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.R;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.banco.BDSQLiteHelper;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.model.Imagem;

public class EditarImagemActivity extends AppCompatActivity {

    private BDSQLiteHelper bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_imagem);
        Intent intent = getIntent();
        final int id = intent.getIntExtra("ID", 0);
        bd = new BDSQLiteHelper(this);
        Imagem imagem = bd.getImagem(id);
        final EditText nome = (EditText) findViewById(R.id.etNome);
        final EditText descricao = (EditText) findViewById(R.id.etDescricao);
//        final EditText ano = (EditText) findViewById(R.id.etAno);
        nome.setText(imagem.getNome());
        descricao.setText(imagem.getDescricao());
//        caminho.setText(String.valueOf(imagem.getCaminho()));

        final Button alterar = (Button) findViewById(R.id.btnAlterar);
        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imagem imagem = new Imagem();
                imagem.setId(id);
                imagem.setNome(nome.getText().toString());
                imagem.setDescricao(descricao.getText().toString());
//                imagem.setAno(Integer.parseInt(ano.getText().toString()));
                bd.updateImagem(imagem);
                Intent intent = new Intent(EditarImagemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final Button remover = (Button) findViewById(R.id.btnRemover);
        remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(EditarImagemActivity.this)
                        .setTitle(R.string.confirmar_exclusao)
                        .setMessage(R.string.quer_mesmo_apagar)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Imagem imagem = new Imagem();
                                imagem.setId(id);
                                bd.deleteImagem(imagem);
                                Intent intent = new Intent(EditarImagemActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
    }
}