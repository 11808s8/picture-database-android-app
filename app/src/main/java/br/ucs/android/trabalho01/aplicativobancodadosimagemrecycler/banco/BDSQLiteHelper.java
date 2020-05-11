package br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;

import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.model.Imagem;

public class BDSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "imagemDB";
    private static final String TABELA_IMAGEM = "imagem";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String DESCRICAO = "descricao";
    private static final String CAMINHO = "caminho";
    private static final String[] COLUNAS = {ID, NOME, DESCRICAO, CAMINHO};

    public BDSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // criação básica de tabelão de imagens
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+TABELA_IMAGEM+" ("+
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                NOME + " TEXT,"+
                DESCRICAO + " TEXT,"+
                CAMINHO + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DELETE_TABLE ="DROP TABLE IF EXISTS " + TABELA_IMAGEM;
        db.execSQL(DELETE_TABLE);
        this.onCreate(db);
    }

    public void addImagem(Imagem imagem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME, imagem.getNome());
        values.put(DESCRICAO, imagem.getDescricao());
        values.put(CAMINHO, imagem.getCaminho());
        db.insert(TABELA_IMAGEM, null, values);
        db.close(); // fecha conexao com o banco
    }

    public Imagem getImagem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_IMAGEM, // a. tabela
                COLUNAS, // b. colunas
                " id = ?", // c. colunas para comparar
                new String[] { String.valueOf(id) }, // d. parâmetros
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor == null) {
            return null; // @REFACTOR refatorar isso aqui pra retornar algo + tratavel OU TRATAR NO RETORNO
        } else {
            cursor.moveToFirst();
            Imagem imagem = cursorToImagem(cursor);
            return imagem;
        }
    }

    private Imagem cursorToImagem(Cursor cursor) {
        Imagem imagem = new Imagem();
        imagem.setId(Integer.parseInt(cursor.getString(0)));
        imagem.setNome(cursor.getString(1));
        imagem.setDescricao(cursor.getString(2));
        imagem.setCaminho(cursor.getString(3));
        return imagem;
    }

    public ArrayList<Imagem> getAllImagens() {
        ArrayList<Imagem> listaImagem = new ArrayList<Imagem>();
        String query = "SELECT * FROM " + TABELA_IMAGEM + " ORDER BY " + NOME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Imagem imagem = cursorToImagem(cursor);
                listaImagem.add(imagem);
            } while (cursor.moveToNext());
        }
        return listaImagem;
    }

    public int updateImagem(Imagem imagem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOME, imagem.getNome());
        values.put(DESCRICAO, imagem.getDescricao());
        int i = db.update(TABELA_IMAGEM, //tabela
                values, // valores
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(imagem.getId()) }); //param P COMPARAÇÂO
        db.close(); // fecha conexao com o banco
        return i; // número de linhas modificadas
    }

    public int deleteImagem(Imagem imagem) {
        SQLiteDatabase db = this.getWritableDatabase();
        File file = new File(imagem.getCaminho());
        file.delete();
        int i = db.delete(TABELA_IMAGEM, //tabela
                ID+" = ?", // colunas para comparar
                new String[] { String.valueOf(imagem.getId()) });
        db.close(); // fecha conexao com o banco
        return i; // número TOTAL de linhas excluídas
    }
}

