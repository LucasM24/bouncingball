package com.example.bouncingball.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bouncingball.R;
import com.example.bouncingball.clases.Usuario;
import com.example.bouncingball.database.dbConexion;

public class Ganador extends AppCompatActivity {
    TextView mostrar_user ;
    private dbConexion dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modelo_ganador);

        Bundle extra = getIntent().getExtras();
        String name_user = extra.getString("id_user");
        mostrar_user = (TextView) findViewById(R.id.textView3);
        mostrar_user.setText(name_user);
        dao = new dbConexion(this);
    }
    public void volverAlMenu(View v){

        // Abrir el Archivo
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);

        // Recuperar clave-valor
        int mypuntaje = preferences.getInt("user_puntaje",0);
        //int puntaje_Acumulativo = preferences.getInt("puntaje_total",0);
        String user = preferences.getString("user","vacio");

        // Editar el Archivo para realizar Modificaciones
        SharedPreferences.Editor editor = preferences.edit();

        // sumar el puntaje obtenido en el nivel jugado
        //puntaje_Acumulativo += mypuntaje;
        // Mostrar por consola
        //System.out.println("Mypuntaje (Ganador) : "+mypuntaje);
       // System.out.println("Puntaje Acumulativo  (Ganador): "+puntaje_Acumulativo);

         // actualizar los cambios por defecto
        editor.putInt("level", 1);
        editor.putString("changelevel","no");
       // editor.putInt("puntaje_total",0);
        editor.putInt("user_puntaje",0);
        editor.apply();

        /*
         * Comparacion el puntaje obtenido en el juego con el ultimo registrado en la
         * Base de Datos
         * */

        Usuario us = dao.consultarPuntaje(user);
        if (mypuntaje <= us.getPuntaje()) {
           // Toast.makeText(this, "Segui Participando ", Toast.LENGTH_SHORT).show();
        } else {
            int i = dao.updatePuntaje(user, mypuntaje);

          //  Toast.makeText(this, "Felicitaciones Superaste el Puntaje_Max Registrado", Toast.LENGTH_SHORT).show();
        }

        //System.out.println("*******Metodo del Evento Activity : Salir del juego GameView*********");
        Intent menu = new Intent(Ganador.this, MenuPrincipal.class);
        menu.putExtra("id_user",user);
        startActivity(menu);

    }
}
