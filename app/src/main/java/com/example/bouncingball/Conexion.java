package com.example.bouncingball;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Conexion extends SQLiteOpenHelper {

    public static final String name = "banco.db";
    public static final int version = 1;


    public Conexion( Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  usuario (usuario  varchar(50) primary key  ," + "clave varchar(50),pais varchar(50),puntaje Integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS  usuario");
        onCreate(db);
    }
}
