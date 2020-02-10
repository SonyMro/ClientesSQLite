package com.example.clientessqlite.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class editarCliente extends AppCompatActivity {
    private AppDatabase db;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnTomarFoto;
    private ImageView foto;
    private Button btnEditarCliente, btGps,btnEliminar;
    private EditText txtNombre, txtRfc, txtTelefono, txtCorreo, txtDireccion, txtLatitud, txtLongitud, txtIduser;
    //Se declara una variable de tipo LocationManager encargada de proporcionar acceso al servicio de localización
    private LocationManager locManager;
    //Se declara una variable de tipo Location:
    private Location loc;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);
        final Cliente cliente = (Cliente) getIntent().getSerializableExtra("Editarcliente");
        // Toast.makeText(getApplicationContext(),""+cliente.toString(),Toast.LENGTH_SHORT).show();
        setToolbar();
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constantes.BD_NAME).allowMainThreadQueries().build();
        drawerLayout = (DrawerLayout) findViewById(R.id.drower_layout);
        navigationView = (NavigationView) findViewById(R.id.navView);
        btnTomarFoto = (ImageButton) findViewById(R.id.btnFotografiaEdit);
        foto = (ImageView) findViewById(R.id.imgfotoClienteEdit);

        txtNombre = (EditText) findViewById(R.id.txtNombreEdit);
        txtRfc = (EditText) findViewById(R.id.txtRfcEdit);
        txtCorreo = (EditText) findViewById(R.id.txtCorreoEdit);
        txtTelefono = (EditText) findViewById(R.id.txtTelefonoEdit);
        txtDireccion = (EditText) findViewById(R.id.txtDirEdit);
        txtIduser = (EditText) findViewById(R.id.txtUsuarioEdit);
        txtLatitud = (EditText) findViewById(R.id.txtLatEdit);
        txtLongitud = (EditText) findViewById(R.id.txtLongEdit);

        btnEditarCliente = (Button) findViewById(R.id.btnAEditarCliente);
        btGps = (Button) findViewById(R.id.btnGpsEdit);
        btnEliminar=(Button)findViewById(R.id.btnAEiminarCliente);

        txtNombre.setText(cliente.getNombre());
        txtRfc.setText(cliente.getRfc());
        txtCorreo.setText(cliente.getCorreo());
        txtTelefono.setText(cliente.getTelefono());
        txtDireccion.setText(cliente.getDireccion());
        txtIduser.setText(cliente.getIduser());
        txtLatitud.setText(cliente.getLatitud());
        txtLongitud.setText(cliente.getLongitud());
        foto.setImageBitmap(String64Tobitmap(cliente.getImagen()));

        btnEditarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idCliente=cliente.getIdCliente();
                String nombre= txtNombre.getText().toString();
                String rfc= txtRfc.getText().toString();
                String correo= txtCorreo.getText().toString();
                String telefono= txtTelefono.getText().toString();
                String direccion= txtDireccion.getText().toString();
                String IdUser=txtIduser.getText().toString();
                String Latitud=txtLatitud.getText().toString();
                String longitud=txtLongitud.getText().toString();
                String  imagen=ConvertirImegViewToBase64(foto);
                final  Cliente ActualizarCliente=new Cliente(idCliente,nombre,rfc,
                        direccion,telefono,correo,Latitud,longitud,IdUser,imagen);

                AlertDialog.Builder builder = new AlertDialog.Builder(editarCliente.this);
                builder.setTitle("Alerta");
                builder.setMessage("¿Deseas actualizar a:?" + nombre);

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActualizarCliente(ActualizarCliente);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(editarCliente.this, "Actualizacion cancelada", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(editarCliente.this);
                builder.setTitle("Confirma");
                builder.setMessage("¿Deseas eliminar a: ?" + txtNombre.getText().toString());

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EliminarPersona(cliente.getIdCliente());

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(editarCliente.this, "Operacion cancelada", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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
    public Bitmap String64Tobitmap(String base64String) {

        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
        return decodedByte;
    }
    public void EliminarPersona(int IdPersona) {
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constantes.BD_NAME).allowMainThreadQueries().build();

        if (IdPersona != 0) {
            db.clienteDao().deleteById(IdPersona);
            Toast.makeText(editarCliente.this, "Usuario Eliminado", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(editarCliente.this, "Este usuarios no existe", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(new Intent(editarCliente.this, editarCliente.class));
        startActivity(intent);

        // Toast.makeText(UpdatePersona.this,"Hola",Toast.LENGTH_SHORT).show();
    }
    public void ActualizarCliente(Cliente cliente) {
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constantes.BD_NAME).allowMainThreadQueries().build();
        if (cliente != null) {
            db.clienteDao().ActualizarCliente(cliente);
            Toast.makeText(editarCliente.this, "Cliente Actualizado", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(editarCliente.this, "Verifique que este cliente exista", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(new Intent(editarCliente.this, MostrarClientes.class));
        startActivity(intent);
    }

    private String ConvertirImegViewToBase64(ImageView imageView) {
        //encode image to base64 string
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image = stream.toByteArray();
        String img_str = Base64.encodeToString(image, 0);
        Toast.makeText(getApplicationContext(), "" + img_str, Toast.LENGTH_LONG).show();
        return img_str;
    }

    //Obtnemos la cordenada latitud y longitud
    private double[] CordenadasGps() {
        //Declaramos un arrglos para almacenar las cordenadas
        double cordenadas[] = new double[2];
        //preguntamos para acceder a la geolocalizacion del usuario
        ActivityCompat.requestPermissions(editarCliente.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
        }
        return cordenadas;
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
}
