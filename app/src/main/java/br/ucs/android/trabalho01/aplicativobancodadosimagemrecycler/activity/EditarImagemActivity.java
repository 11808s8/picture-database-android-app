package br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.activity;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.R;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.banco.BDSQLiteHelper;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.model.Imagem;

public class EditarImagemActivity extends AppCompatActivity {

    private BDSQLiteHelper bd;
    private ImageView imagemView;

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
        imagemView = (ImageView) findViewById(R.id.pv_image);
        mostraFoto(imagem.getCaminho());
        nome.setText(imagem.getNome());
        descricao.setText(imagem.getDescricao());

        final Button alterar = (Button) findViewById(R.id.btnAlterar);
        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imagem imagem = new Imagem();
                imagem.setId(id);
                imagem.setNome(nome.getText().toString());
                imagem.setDescricao(descricao.getText().toString());
                bd.updateImagem(imagem);
                Toast.makeText(EditarImagemActivity.this, "Imagem alterada com sucesso!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditarImagemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mostraFoto(String caminho) {
        Bitmap bitmap = BitmapFactory.decodeFile(caminho);
        imagemView.setImageBitmap(bitmap);
    }
}
