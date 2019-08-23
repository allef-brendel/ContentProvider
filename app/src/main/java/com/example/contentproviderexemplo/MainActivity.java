package com.example.contentproviderexemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button botaoAdd;
    private Button botaoVer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoAdd = findViewById(R.id.botaoadd);
        botaoVer = findViewById(R.id.botaover);

        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(ContentProvider.NAME, "ALLEF");

                values.put(ContentProvider.GRADE, "helooo");

                Uri uri = getContentResolver().insert(
                        ContentProvider.CONTENT_URI, values);

                Toast.makeText(getBaseContext(),
                        uri.toString(), Toast.LENGTH_LONG).show();
            }
        });

        botaoVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URL = "com.example.contentproviderexemplo";

                Uri pessoas = Uri.parse(URL);
                Cursor c = getContentResolver().query(pessoas, null, null, null, "nome");

                if (c.moveToFirst()) {
                    do {
                        Toast.makeText(MainActivity.this, "TAG> "+ c.getString(c.getColumnIndex(ContentProvider.NAME)), Toast.LENGTH_SHORT).show();
                    } while (c.moveToNext());
                }
            }
        });



    }
}
