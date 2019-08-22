package com.example.contentproviderexemplo;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

public class ContentProvider extends android.content.ContentProvider {

    private static String[] colunas = {"nome"};

    public static final String AUTHORITY = "com.example.contentproviderexemplo";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/nome");

    public static final int PESSOA = 1;
    public static final int PESSOA_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, "pessoa", PESSOA);
        uriMatcher.addURI(AUTHORITY, "pessoa_id", PESSOA_ID);
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {


        MatrixCursor cursor = new MatrixCursor(colunas);
        for (String nome : new String[]{"José", "Maria", "Pedro"}) {
            cursor.addRow(new Object[]{0, nome});
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
