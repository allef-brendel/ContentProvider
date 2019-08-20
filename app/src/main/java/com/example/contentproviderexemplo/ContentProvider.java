package com.example.contentproviderexemplo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

public class ContentProvider extends android.content.ContentProvider {

    private static String[] matrixColumns = {"nome", "coluna1"};

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        MatrixCursor cursor = new MatrixCursor(matrixColumns);
        for (String name : new String[]{"José", "Maria", "Pedro"}) {
            cursor.addRow(new Object[]{0, name});
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
