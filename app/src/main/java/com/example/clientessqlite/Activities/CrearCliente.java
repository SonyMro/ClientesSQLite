package com.example.clientessqlite.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.clientessqlite.ConfiguracionDB.Constantes;
import com.example.clientessqlite.DataBase.AppDatabase;
import com.example.clientessqlite.Entidades.Cliente;
import com.example.clientessqlite.R;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class CrearCliente extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private AppDatabase db;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnTomarFoto;
    private ImageView foto;
    private Button btnAgregarCliente, btGps;
    private EditText txtNombre, txtRfc, txtTelefono, txtCorreo, txtDireccion, txtLatitud, txtLongitud, txtIduser;
    //Se declara una variable de tipo LocationManager encargada de proporcionar acceso al servicio de localización
    private LocationManager locManager;
    //Se declara una variable de tipo Location:
    private Location loc;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cliente);
        setToolbar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drower_layout);
        navigationView = (NavigationView) findViewById(R.id.navView);
        btnTomarFoto = (ImageButton) findViewById(R.id.btnFotografia);
        foto = (ImageView) findViewById(R.id.imgfotoCliente);

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtRfc = (EditText) findViewById(R.id.txtRfc);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtDireccion = (EditText) findViewById(R.id.txtDir);
        txtIduser = (EditText) findViewById(R.id.txtUsuario);
        txtLatitud = (EditText) findViewById(R.id.txtLat);
        txtLongitud = (EditText) findViewById(R.id.txtLong);


        btnAgregarCliente = (Button) findViewById(R.id.btnAgregar);
        btGps = (Button) findViewById(R.id.btnGps);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constantes.BD_NAME).allowMainThreadQueries().build();
        btnAgregarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cliente obj = new Cliente();
                obj.setNombre(txtNombre.getText().toString());
                obj.setRfc(txtRfc.getText().toString());
                obj.setTelefono(txtTelefono.getText().toString());
                obj.setCorreo(txtCorreo.getText().toString());
                obj.setDireccion(txtDireccion.getText().toString());
                obj.setLongitud(txtLongitud.getText().toString());
                obj.setLatitud(txtLatitud.getText().toString());
                obj.setIduser(txtIduser.getText().toString());
                obj.setImagen(ConvertirImegViewToBase64(foto));
                long resultado = db.clienteDao().insertar(obj);
                List<Cliente> lista = db.clienteDao().getAllClientes();
                if (resultado > 0) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Hay un error", Toast.LENGTH_LONG).show();
                }
            }
        });

        btGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double cordenadaGps[] = CordenadasGps();
                if (cordenadaGps != null) {
                    txtLatitud.setText(String.valueOf(cordenadaGps[0]));
                    txtLongitud.setText(String.valueOf(cordenadaGps[1]));
                } else {
                    Toast.makeText(getApplicationContext(), "No se puede obtener cordenadas GPS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Con este item vamos a llamar a la camara
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // está protegido por una condición que llama a resolveActivity(), que muestra el primer componente de actividad que puede manejar el intent.
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                //Toast.makeText(getApplicationContext(), "Hola", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String ConvertirImegViewToBase64(ImageView imageView) {
        //encode image to base64 string
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image = stream.toByteArray();
        String img_str = Base64.encodeToString(image, 0);
    //    Toast.makeText(getApplicationContext(), "" + img_str, Toast.LENGTH_LONG).show();
        return img_str;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Preguntamos si al tomar la foto se clickeo ok
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Obtenemos el resutl
            Bundle extras = data.getExtras();
            //Creamo un bitmap y obnetemos la data
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //Adignamos la fotoa a nuestra imgView
            foto.setImageBitmap(imageBitmap);
        } else {
            Toast.makeText(getApplicationContext(), "Error no se capturo la imagen intente de nuevo", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Obtnemos la cordenada latitud y longitud
    private double[] CordenadasGps() {
        //Declaramos un arrglos para almacenar las cordenadas
        double cordenadas[] = new double[2];
        //preguntamos para acceder a la geolocalizacion del usuario
        ActivityCompat.requestPermissions(CrearCliente.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        /*Se comprueba si se han concedido los permisos para mostrar los datos de latitud, longitud, altura y precisión del dispositivo*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "No se han definido los permisos necesarios.", Toast.LENGTH_LONG).show();

        } else {
            /*Se asigna a la clase LocationManager el servicio a nivel de sistema a partir del nombre.*/
            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            /*Se asigna a la variable de tipo Location que accederá a la última posición conocida proporcionada por el proveedo*/
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Toast.makeText(getApplicationContext(), "Latitud: " + loc.getLatitude() + " longitud: " + loc.getLongitude(), Toast.LENGTH_LONG).show();
            cordenadas[0] = loc.getLatitude();
            cordenadas[1] = loc.getLongitude();
           /* tvLatitud.setText(String.valueOf(loc.getLatitude()));
            tvLongitud.setText(String.valueOf());
            tvAltura.setText(String.valueOf(loc.getAltitude()));
            tvPrecision.setText(String.valueOf(loc.getAccuracy()));*/
        }
        return cordenadas;
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
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
