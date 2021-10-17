package com.example.bouncingball;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class UsuarioDB {


    private Conexion conexion ;
    private SQLiteDatabase banco ;

    public UsuarioDB(Context context){

        conexion = new Conexion(context);
        banco = conexion.getWritableDatabase();

    }
    public void  insertar (Usuario usuario){

        ContentValues values = new ContentValues();
        values.put("usuario",usuario.getUsuario());
        values.put("clave",usuario.getClave());
        values.put("pais",usuario.getPais());
        String us = usuario.getUsuario();

        Cursor c = banco.rawQuery("SELECT usuario from usuario  where usuario='us' ",null);

        /* Toast.makeText(this, "Usuario Repetido " , Toast.LENGTH_SHORT).show();*/
        /*significa que esta repetido */
        //Correct
        if(c.moveToFirst()) {
           /* System.out.println("Error de Usuario Repetido ");*/
        }
        else{
            banco.insert("usuario", null, values);
        }


    }
    public SQLiteDatabase getBaseDatos(){

       return  this.banco = conexion.getReadableDatabase();
    }
    public void mostrarListado(){


        Cursor c = banco.rawQuery("SELECT usuario ,puntaje from usuario  ",null);

        // Nos aseguramos que al menos exista un registro

        if(c.moveToFirst()){

            // Recorremos el cursor hasta que no haya ningun registro

            do{

                String usuario = c.getString(0);
                String puntaje = c.getString(3);

            }while(c.moveToNext());


        }
    }
}
