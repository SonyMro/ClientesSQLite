package com.example.clientessqlite.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
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

import com.example.clientessqlite.R;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CrearCliente extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnTomarFoto;
    private ImageView imageView;
    private Button btnAgregarCliente;
    private EditText txtNombre, txtRfc, txtTelefono, txtCorreo, txtDireccion, txtLatitud, txtLongitud, txtIduser;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cliente);
        setToolbar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drower_layout);
        navigationView = (NavigationView) findViewById(R.id.navView);
        btnTomarFoto = (ImageButton) findViewById(R.id.btnFotografia);
        imageView = (ImageView) findViewById(R.id.imgfotoCliente);

        btnAgregarCliente=(Button) findViewById(R.id.btnAgregarCliente);
        btnAgregarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //encode image to base64 string
                imageView.buildDrawingCache();
                Bitmap bitmap = imageView.getDrawingCache();
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                byte[] image=stream.toByteArray();
                System.out.println("byte array:"+image);
                String img_str = Base64.encodeToString(image, 0);
                System.out.println("string:"+img_str);
                Toast.makeText(getApplicationContext(),""+img_str,Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "Hola", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public static String ConvertBitmapToString(Bitmap bitmap){
        String encodedImage = "";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        try {
            encodedImage= URLEncoder.encode(Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedImage;
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
            imageView.setImageBitmap(imageBitmap);
        } else {
            Toast.makeText(getApplicationContext(), "Error no se capturo la imagen intente de nuevo", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
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
