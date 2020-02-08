
package com.example.clientessqlite.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.clientessqlite.ConfiguracionDB.Constantes;
import com.example.clientessqlite.DataBase.AppDatabase;
import com.example.clientessqlite.Fragments.AlertFragment;
import com.example.clientessqlite.Fragments.EmailFragment;
import com.example.clientessqlite.Fragments.InfoFragment;
import com.example.clientessqlite.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;
    private Button btn;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    //CircleImageView civ = (CircleImageView) findViewById(R.id.imagen_circular);
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drower_layout);
        navigationView = (NavigationView) findViewById(R.id.navView);
        setFramentByDefault();
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                boolean fragmentTrasaccion = false;
                Fragment fragment = null;
                Intent intent =null;
                switch (menuItem.getItemId()) {
                    case R.id.menu_mail:
                        fragment = new EmailFragment();
                        fragmentTrasaccion = true;
                        break;
                    case R.id.menu_alert:
                        fragment = new AlertFragment();
                        fragmentTrasaccion = true;
                        break;
                    case R.id.menu_info:
                        fragment = new InfoFragment();
                        fragmentTrasaccion = true;
                        break;
                    case R.id.menu_VerClientes:
                       intent= new Intent(getApplicationContext(),CrearCliente.class);
                       startActivity(intent);
                        break;
                        default:
                            Toast.makeText(getApplicationContext(),"Vista no disponible",Toast.LENGTH_SHORT).show();
                            break;
                }
                if (fragmentTrasaccion) {
                    changeFragment(fragment,menuItem);
                    //
                    drawerLayout.closeDrawers();
                }

                return true;
            }
        });
        //  db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, Constantes.BD_NAME).allowMainThreadQueries().build();
        // String inf= String.valueOf(db.clienteDao().getAllClientes());
        // Toast.makeText(MainActivity.this,"Info: "+inf,Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.ptoolbar);
        setSupportActionBar(toolbar); //No Problerm
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private  void setFramentByDefault(){
       changeFragment(new EmailFragment(),navigationView.getMenu().getItem(0));
    }
    private void changeFragment(Fragment fragment, MenuItem menuItem){
        getSupportFragmentManager()
                .beginTransaction().
                replace(R.id.content_frame, fragment)
                .commit();
        //Con setChecked mostramos que el item esta selecionado
        menuItem.setChecked(true);
        //Obtenemos el texto del item y lo asignamos al frament
        getSupportActionBar().setTitle(menuItem.getTitle());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Abrir el menu lateral
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
