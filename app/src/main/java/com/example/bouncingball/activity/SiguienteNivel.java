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

public class SiguienteNivel extends AppCompatActivity {
    TextView mostrar_user ;
    private dbConexion dao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modelo_siguiente_nivel);

        Bundle extra = getIntent().getExtras();
        String name_user = extra.getString("id_user");
        mostrar_user = (TextView) findViewById(R.id.textView2);
        mostrar_user.setText(name_user);
        dao = new dbConexion(this);

    }

    public void continuarJugando(View v){
        /*
        * Deberia volver al GameView
        * */
        System.out.println("*******Metodo del Evento Activity : Deberia volver al GameView*********");

        // Extraer el Archivo con nombre myidiom
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        // Buscar clave-valor
        int mypuntaje = preferences.getInt("user_puntaje",0);
        int puntaje_Acumulativo = preferences.getInt("puntaje_total",0);
        // Editar para guardar el Puntaje Acumulado
        SharedPreferences.Editor editor = preferences.edit();
        // sumar el puntaje obtenido en el nivel jugado
        puntaje_Acumulativo += mypuntaje;
        // Mostrar por Consola
        System.out.println("Mypuntaje : "+mypuntaje);
        System.out.println("Puntaje Acumulativo : "+puntaje_Acumulativo);

        // Actualizar clave-valor
        editor.putInt("puntaje_total",puntaje_Acumulativo);
        editor.putInt("user_puntaje",0);
        editor.commit();

        Intent jugar = new Intent(SiguienteNivel.this, Principal.class);
        startActivity(jugar);



    }

    public void salirDelJuego(View V){

        System.out.println("*******Metodo del Evento Activity : Salir del juego GameView*********");
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String user = preferences.getString("user","vacio");
        // Buscar clave-valor
        int mypuntaje = preferences.getInt("user_puntaje",0);
        int puntaje_Acumulativo = preferences.getInt("puntaje_total",0);
        // Editar para guardar el Puntaje Acumulado
        SharedPreferences.Editor editor = preferences.edit();
        // sumar el puntaje obtenido en el nivel jugado
        puntaje_Acumulativo += mypuntaje;
        // Mostrar por Consola
        System.out.println("Mypuntaje : "+mypuntaje);
        System.out.println("Puntaje Acumulativo : "+puntaje_Acumulativo);

        // Actualizar clave-valor
        editor.putInt("puntaje_total",0);
        editor.putInt("user_puntaje",0);
        editor.commit();

        Usuario us = dao.consultarPuntaje(user);
        System.out.println("Pasas x aqui if(puntaje_Acumulativo>us.getPuntaje)");
        if (puntaje_Acumulativo <= us.getPuntaje()) {
            Toast.makeText(this, "Segui Participando ", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("dao.updatePuntaje(user,puntaje_Acumulativo);");
            final int i = dao.updatePuntaje(user, puntaje_Acumulativo);
            Toast.makeText(this, "Felicitaciones Superaste el Puntaje_Max Registrado", Toast.LENGTH_SHORT).show();
        }


        Intent menu = new Intent(SiguienteNivel.this, MenuPrincipal.class);
        menu.putExtra("id_user",user);
        startActivity(menu);

    }

}
