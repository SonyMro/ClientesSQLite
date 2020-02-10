package com.example.clientessqlite.Entidades;

import android.content.ContentValues;
import android.os.Build;
import android.provider.BaseColumns;

import androidx.annotation.RequiresApi;
import androidx.room.*;

@Entity(tableName = Cliente.TABLE_NAME, indices = {@Index(value = {"rfc"}, unique = true)})
public class Cliente {
    public static final String TABLE_NAME = "TblClientes";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private int idCliente;
    @ColumnInfo(name = "rfc")
    private String rfc;
    @ColumnInfo(name = "Nombre")
    private String Nombre;
    @ColumnInfo(name = "Direccion")
    private String direccion;
    @ColumnInfo(name = "Telefono")
    private String Telefono;
    @ColumnInfo(name = "Correo")
    private String Correo;
    @ColumnInfo(name = "Latitud")
    private String Latitud;
    @ColumnInfo(name = "Longitud")
    private String Longitud;
    @ColumnInfo(name = "iduser")
    private String iduser;
    @ColumnInfo(name = "imagen")
    private String imagen;

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }




    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public Cliente() {
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Cliente fromContentValues(ContentValues values) {
        final Cliente obj = new Cliente();
        if (values.containsKey(COLUMN_ID)) {
            obj.idCliente = Math.toIntExact(values.getAsLong(COLUMN_ID));
        }
        if (values.containsKey(COLUMN_NAME)) {
            obj.setIdCliente(1);
        }
        return obj;
    }

    public String toJsonString(){
        return "";
    }

    public Cliente(int idCliente, String rfc, String nombre, String direccion, String telefono, String correo, String latitud, String longitud, String iduser, String imagen) {
        this.idCliente = idCliente;
        this.rfc = rfc;
        Nombre = nombre;
        this.direccion = direccion;
        Telefono = telefono;
        Correo = correo;
        Latitud = latitud;
        Longitud = longitud;
        this.iduser = iduser;
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", rfc='" + rfc + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", Telefono='" + Telefono + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Latitud='" + Latitud + '\'' +
                ", Longitud='" + Longitud + '\'' +
                ", iduser='" + iduser + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
