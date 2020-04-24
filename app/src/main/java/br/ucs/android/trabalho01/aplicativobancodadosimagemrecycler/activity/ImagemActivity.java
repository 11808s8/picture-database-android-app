package br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.activity;

import android.Manifest;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.R;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.banco.BDSQLiteHelper;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.model.Imagem;

public class ImagemActivity extends AppCompatActivity {

    private BDSQLiteHelper bd;
    private static final int PERMISSAO_REQUEST = 2;

    public ImageView imagemView;
    private File arquivoFoto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagem);
        bd = new BDSQLiteHelper(this);
        final EditText nome = (EditText) findViewById(R.id.edNome);
        final EditText descricao = (EditText) findViewById(R.id.edDescricao);
        imagemView = (ImageView) findViewById(R.id.pv_image);
        Button novo = (Button) findViewById(R.id.btnAdd);

        novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Imagem imagem = new Imagem();
                imagem.setNome(nome.getText().toString());
                imagem.setDescricao(descricao.getText().toString());
                imagem.setCaminho(arquivoFoto.getAbsolutePath());
                bd.addImagem(imagem);
                Intent intent = new Intent(ImagemActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        // Pede permissão para escrever arquivos no dispositivo
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                     Uri.fromFile(arquivoFoto)));
            mostraFoto(arquivoFoto.getAbsolutePath());
        }
    }

    private void mostraFoto(String caminho) {
        Bitmap bitmap = BitmapFactory.decodeFile(caminho);
        imagemView.setImageBitmap(bitmap);
    }

    public void tirarFoto(View view) {
        Intent takePictureIntent = new
                Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                arquivoFoto = criaArquivo();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (arquivoFoto != null) {
                Uri photoURI = FileProvider.getUriForFile(getBaseContext(),
                        getBaseContext().getApplicationContext().getPackageName() +
                                ".provider", arquivoFoto);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private File criaArquivo() throws IOException {
        String timeStamp = new
                SimpleDateFormat("yyyyMMdd_Hhmmss").format(
                new Date());
        File pasta = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imagem = new File(pasta.getPath() + File.separator
                + "JPG_" + timeStamp + ".jpg");
        return imagem;
    }
}
