package com.example.contentresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText nomeID;
    EditText nomeID2;
    EditText nomeID3;
    EditText nomeID4;
    Button botao;
    ArrayList<String> list;
    ListView listView;

    String URL = "content://com.example.contentproviderexemplo/pessoas/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao = findViewById(R.id.botao2);

        dadosListView();

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nomeID2 = findViewById(R.id.editText2);
                String id = nomeID2.getText().toString();

                Uri uri = Uri.parse(URL + id);

                Cursor cursor = getContentResolver().query(uri, null, null, null, "nome");

                getContentResolver().delete(uri, "_ID = ?", new String[]{id});

                if (cursor.moveToFirst()){
                    do {
                        String nome = cursor.getString(cursor.getColumnIndex("nome"));
                        Toast.makeText(MainActivity.this, "Nome: "+ nome +" deletado", Toast.LENGTH_SHORT).show();
                    }while (cursor.moveToNext());
                }
            }
        });
    }

    public void receberDadosContent(View view){
        nomeID = findViewById(R.id.editText);

                Uri pessoas = Uri.parse(URL + nomeID.getText().toString());
                Cursor cursor = getContentResolver().query(pessoas, null, null, null, "nome");

                if (cursor.moveToFirst()){
                    do {String nome = cursor.getString(cursor.getColumnIndex("nome"));

                        Toast.makeText(MainActivity.this, "Nome: " + nome, Toast.LENGTH_SHORT).show();
                    }while (cursor.moveToNext());
                }
         }

    public void dadosListView(){

        listView = findViewById(R.id.listView);

        list = new ArrayList<>();

        Uri uri = Uri.parse(URL);
        Cursor cursor = getContentResolver().query(uri, null, null, null, "nome");

        if(cursor.moveToFirst()){
            do {
                String nome = cursor.getString(cursor.getColumnIndex("nome"));
                list.add(nome);
            }while (cursor.moveToNext());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
        }
    }

    public void atualizarNome(View view){
        ContentValues values = new ContentValues();

        nomeID3 = findViewById(R.id.editText3);
        nomeID4 = findViewById(R.id.editText4);

        String id = nomeID3.getText().toString();
        String valor = nomeID4.getText().toString();

        values.put("nome", valor);
        Log.i("TAG", values.toString() );

        Uri uri = Uri.parse(URL + nomeID3.getText().toString());

        getContentResolver().update(uri, values , "_ID = ?", new String[]{id} );

        Toast.makeText(this, "Atualizado", Toast.LENGTH_SHORT).show();
    }

}
