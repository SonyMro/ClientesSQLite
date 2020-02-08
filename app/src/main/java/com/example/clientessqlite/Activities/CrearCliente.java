package com.example.clientessqlite.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.clientessqlite.R;
import com.google.android.material.navigation.NavigationView;

public class CrearCliente extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cliente);
        setToolbar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drower_layout);
        navigationView = (NavigationView) findViewById(R.id.navView);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.pCreartoolbar);
        setSupportActionBar(toolbar); //No Problerm
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Abrir el main
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
