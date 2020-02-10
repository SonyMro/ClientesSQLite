package com.example.clientessqlite.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clientessqlite.ConfiguracionDB.Constantes;
import com.example.clientessqlite.DataBase.AppDatabase;
import com.example.clientessqlite.Entidades.Cliente;
import com.example.clientessqlite.R;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class editarCliente extends AppCompatActivity {
    private AppDatabase db;
    private  Cliente cli = new Cliente(1,"Hola","dddddd","dddddd","dddddd","dddddd","dddddd","dddddd","dddddd","dddddd");
    EditText et1;
    private Button btnEditarCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);
        setToolbar();
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constantes.BD_NAME)
                .allowMainThreadQueries().build();
        et1=(EditText) findViewById(R.id.txtNombreEdit);
        btnEditarCliente=(Button) findViewById(R.id.btnAEditarCliente);

        btnEditarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
       writeToFile(this,"Respaldo.txt",cli.toString());

    }

    public static void writeToFile(Context context, String fileName, String str) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(str.getBytes(), 0, str.length());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.pEditartoolbar);
        setSupportActionBar(toolbar); //No Problerm
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Abrir el main
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
