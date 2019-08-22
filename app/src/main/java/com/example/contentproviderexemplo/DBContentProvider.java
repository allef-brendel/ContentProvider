package com.example.contentproviderexemplo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBContentProvider extends SQLiteOpenHelper {

    private static final String DATABASE_NOME = "nomes.db";
    private static final int VERSAO_DB = 1;

    public static final String TABELA_NOME = "tabela";
    public static final String NOMES = "nomes";

    public static final String[] COLUNAS = {NOMES};

    public static final String CREATE_DB_TABLE =
            "CREATE TABLE " + TABELA_NOME + " (" +
                    NOMES + ")";

    public DBContentProvider(Context context) {
        super(context, DATABASE_NOME, null, VERSAO_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABELA_NOME);
        onCreate(sqLiteDatabase);
    }
}
