package br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.activity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.R;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.adapter.Adapter;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.banco.BDSQLiteHelper;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.model.Imagem;
//import br.ucs.android.trabalho01.bancodedadoslocal.model.Livro;

public class MainActivity extends AppCompatActivity {

    private RecyclerView lista;
    private BDSQLiteHelper bd;
    ArrayList<Imagem> listaImagens;

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

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            lista.setNestedScrollingEnabled(true);
//        }
    }
}
