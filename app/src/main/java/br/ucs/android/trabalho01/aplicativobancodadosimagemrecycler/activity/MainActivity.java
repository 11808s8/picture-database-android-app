package br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.R;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.adapter.Adapter;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.banco.BDSQLiteHelper;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.model.Imagem;

public class MainActivity extends AppCompatActivity {

    private RecyclerView lista;
    private BDSQLiteHelper bd;
    ArrayList<Imagem> listaImagens;
    private static final int PERMISSAO_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bd = new BDSQLiteHelper(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImagemActivity.class);
                startActivity(intent);
            }
        });

        // Pede permissão para acessar as mídias gravadas no dispositivo
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        listaImagens = bd.getAllImagens();

        lista = findViewById(R.id.lvImagens); // REFACTOR THIS
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lista.setLayoutManager(layoutManager);

        final Adapter adapter = new Adapter(listaImagens);
        lista.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lista.addOnItemTouchListener(
                new RecyclerItemClickListener(this, lista ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                            Intent intent = new Intent(MainActivity.this, EditarImagemActivity.class);
                            intent.putExtra("ID", listaImagens.get(position).getId());
                            startActivity(intent);
                    }
                    @Override public void onLongItemClick(View view, final int position) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(R.string.confirmar_exclusao)
                                .setMessage(R.string.quer_mesmo_apagar)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Imagem imagem = new Imagem();
                                        imagem.setId(listaImagens.get(position).getId());
                                        imagem.setCaminho((listaImagens.get(position).getCaminho()));
                                        bd.deleteImagem(imagem);
                                        Toast.makeText(MainActivity.this, "Imagem deletada com sucesso!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();
                    }
                })
        );
    }
}
