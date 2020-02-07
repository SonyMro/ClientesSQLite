
package com.example.clientessqlite;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.clientessqlite.ConfiguracionDB.Constantes;
import com.example.clientessqlite.DataBase.AppDatabase;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, Constantes.BD_NAME).allowMainThreadQueries().build();
        String inf= String.valueOf(db.clienteDao().getAllClientes());
        Toast.makeText(MainActivity.this,"Info: "+inf,Toast.LENGTH_LONG).show();

    }

}
