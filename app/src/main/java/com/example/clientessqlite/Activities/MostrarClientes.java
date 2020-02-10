package com.example.clientessqlite.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.clientessqlite.Adaptadores.AdpCliente;
import com.example.clientessqlite.ConfiguracionDB.Constantes;
import com.example.clientessqlite.DataBase.AppDatabase;
import com.example.clientessqlite.Entidades.Cliente;
import com.example.clientessqlite.R;

import java.util.List;

public class MostrarClientes extends AppCompatActivity {
    private ListView listView;
    private AppDatabase db;
    private List<Cliente> ListCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_clientes);
        setToolbar();
       listView=(ListView) findViewById(R.id.ListView);
        db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class, Constantes.BD_NAME).allowMainThreadQueries().build();
        ListCliente=db.clienteDao().getAllClientes();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           //   Toast.makeText(getBaseContext(),""+ListCliente.get(position),Toast.LENGTH_LONG).show();
                Intent intent= new Intent(MostrarClientes.this,editarCliente.class);
                intent.putExtra("Editarcliente",ListCliente.get(position));
                startActivity(intent);
            }
        });
        AdpCliente adpCliente= new AdpCliente(this,R.layout.itemc_cliente,ListCliente);
        listView.setAdapter(adpCliente);

    }
    //Meotodo para el boton flotante
    public  void  AgregarCliente(View view){
        Intent intent = new Intent(getApplicationContext(), CrearCliente.class);
        startActivity(intent);
       // Toast.makeText(getBaseContext(),"Hddjdjdjdjdj dj dj jd",Toast.LENGTH_LONG).show();
    }
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.pmostrarToolvar);
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
