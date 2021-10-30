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

public class Perdedor extends AppCompatActivity {

    TextView mostrar_user ;
    private dbConexion dao ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modelo_perdedor);

        Bundle extra = getIntent().getExtras();
        String name_user = extra.getString("id_user");
        mostrar_user = (TextView) findViewById(R.id.textView4);
        mostrar_user.setText(name_user);
        dao = new dbConexion(this);
    }
    public void volverAlMenuJuego(View v){

        System.out.println("*******Metodo del Evento Activity : Salir del juego GameView*********");
        // Abrir el Archivo
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);

        // Recuperar clave-valor
        String user = preferences.getString("user","vacio");
        int mypuntaje = preferences.getInt("user_puntaje",0);

        // Editar el Archivo clave-valor
        SharedPreferences.Editor editor = preferences.edit();

        // Perdio en el nivel que estaba jugando por defecto puntaje 0
       // int  mypuntaje = 0 ;

        // sumar el puntaje obtenido en el nivel jugado
       // mypuntaje=mypuntaje+puntaje_Acumulativo;
       // puntaje_Acumulativo = mypuntaje;

        //Mostrar valores por consola
        System.out.println("Mypuntaje : "+mypuntaje);
        //System.out.println("Puntaje Acumulativo : "+puntaje_Acumulativo);

        // actualizar los cambios por defecto
       // editor.putInt("puntaje_total",0);
        editor.putInt("user_puntaje",0);
        editor.putInt("level", 1);
        editor.putString("changelevel","no");
        editor.apply();
        /*
         * Comparacion el puntaje obtenido en el juego con el ultimo registrado en la
         * Base de Datos
         * */
        Usuario us = dao.consultarPuntaje(user);
        System.out.println("Pasas x aqui if(puntaje_Acumulativo>us.getPuntaje)");
        if (mypuntaje <= us.getPuntaje()) {
           // Toast.makeText(this, "Segui Participando ", Toast.LENGTH_SHORT).show();
        } else {

            System.out.println("dao.updatePuntaje(user,puntaje_Acumulativo);");
            final int i = dao.updatePuntaje(user, mypuntaje);
           // Toast.makeText(this, "Felicitaciones Superaste el Puntaje_Max Registrado", Toast.LENGTH_SHORT).show();
        }

        Intent menu = new Intent(Perdedor.this, MenuPrincipal.class);
        menu.putExtra("id_user",user);
        startActivity(menu);

    }
}