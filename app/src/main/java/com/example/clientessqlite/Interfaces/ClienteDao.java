package com.example.clientessqlite.Interfaces;

import com.example.clientessqlite.Entidades.Cliente;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ClienteDao {
    @Query("SELECT COUNT(*) FROM " + Cliente.TABLE_NAME)
    int CantidadClinetes();

    @Insert
    void InsertarClientes(Cliente... clientes);

    @Query("DELETE FROM " + Cliente.TABLE_NAME + " WHERE " + Cliente.COLUMN_ID + "=:idCliente")
    int deleteById(int idCliente);

    @Update
    int ActualizarCliente(Cliente cliente);

    @Insert
    long insertar(Cliente cliente);

    @Query("SELECT * FROM " + Cliente.TABLE_NAME)
    List<Cliente> getAllClientes();

    @Query("SELECT * FROM TblClientes WHERE rfc like ''%:rfc%''")
    boolean verificarRfc(String rfc);

}
