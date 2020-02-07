package com.example.clientessqlite.DataBase;


import com.example.clientessqlite.Entidades.Cliente;
import com.example.clientessqlite.Interfaces.ClienteDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Cliente.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClienteDao clienteDao();

    private static AppDatabase appDatabaseInstance;
}
