package com.example.contentproviderexemplo;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import java.util.HashMap;

public class ContentProvider extends android.content.ContentProvider {

    public static final String AUTHORITY = "com.example.contentproviderexemplo";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/pessoas");

    static final String NAME = "nome";
    static final String GRADE = "grade";
    static final String _ID = "_id";

    public static final int PESSOA = 1;
    public static final int PESSOA_ID = 2;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "pessoas", PESSOA);
        uriMatcher.addURI(AUTHORITY, "pessoas/#", PESSOA_ID);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        if(db != null){
            return true;
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PESSOAS_TABLE_NAME);

       switch (uriMatcher.match(uri)){
            case PESSOA:
                break;
            case PESSOA_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;
        }
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {

//            switch (uriMatcher.match(uri)){
//            case PESSOA:
//                return "vnd.android.cursor.dir/pessoas";
//            case PESSOA_ID:
//                String id = uri.getLastPathSegment();
//                return "vnd.android.cursor.item/pessoas/"+ id;
//        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long rowID = db.insert(	PESSOAS_TABLE_NAME, "", contentValues);

        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Falha  " + uri);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {

        int linha = 0;

        switch (uriMatcher.match(uri)){
            case PESSOA:
                break;
            case PESSOA_ID:
                String id = uri.getLastPathSegment();
                linha = db.delete(PESSOAS_TABLE_NAME, _ID + "=" + id, strings);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return linha;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {

        int linha = 0;

        switch (uriMatcher.match(uri)){
            case PESSOA:
                break;
            case PESSOA_ID:
                String id = uri.getLastPathSegment();
                linha = db.update(PESSOAS_TABLE_NAME, contentValues, _ID + "=" + id ,null);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return linha;
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "bancoDeDados";
    static final String PESSOAS_TABLE_NAME = "pessoas";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE pessoas (_id INTEGER PRIMARY KEY AUTOINCREMENT,  nome TEXT NOT NULL,  grade TEXT NOT NULL);";


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  PESSOAS_TABLE_NAME);
            onCreate(db);
        }
    }

}
