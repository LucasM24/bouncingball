package com.example.bouncingball.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bouncingball.R;
import com.example.bouncingball.clases.Usuario;
import com.example.bouncingball.database.dbConexion;

public class Ganador extends AppCompatActivity {
    private dbConexion dao;
    private Button btnVolverMenuPrincipalGano;
    private TextView textoGanador;
    private String mensajeGanador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modelo_ganador);

        Bundle extra = getIntent().getExtras();
        textoGanador = findViewById(R.id.tituloGanador);
        btnVolverMenuPrincipalGano=(Button) findViewById(R.id.salirDelJuego);
        mensajeGanador = "¡¡Felicitaciones superaste el máximo puntaje!!";

        dao = new dbConexion(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarIdioma();
    }

    public void volverAlMenu(View v){

        // Abrir el Archivo
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);

        // Recuperar clave-valor
        int mypuntaje = preferences.getInt("user_puntaje",0);
        String user = preferences.getString("user","vacio");

        // Editar el Archivo para realizar Modificaciones
        SharedPreferences.Editor editor = preferences.edit();

         // actualizar los cambios por defecto
        editor.putInt("level", 1);
        editor.putString("changelevel","no");

        editor.putInt("user_puntaje",0);
        editor.apply();

        /*
         * Comparacion el puntaje obtenido en el juego con el ultimo registrado en la
         * Base de Datos
         * */
        Usuario us = dao.consultarPuntaje(user);
        if (mypuntaje > us.getPuntaje()) {
            int i = dao.updatePuntaje(user, mypuntaje);
            Toast.makeText(this, mensajeGanador, Toast.LENGTH_SHORT).show();
        }


        Intent menu = new Intent(Ganador.this, MenuPrincipal.class);
        menu.putExtra("id_user",user);
        startActivity(menu);

    }
    private void actualizarIdioma(){

        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String idioma_user = preferences.getString("idioma","es");

        if(idioma_user.equalsIgnoreCase("es")){
            textoGanador.setText(R.string.TEXTO_GANASTE_JUEGO);
            btnVolverMenuPrincipalGano.setText(R.string.TEXTO_BOTON_VOLVER_MENU_PRINCIPAL_ES);
            mensajeGanador = "¡¡Felicitaciones superaste el máximo puntaje!!";
        }else{
            textoGanador.setText(R.string.TEXTO_GANASTE_JUEGO_EN);
            btnVolverMenuPrincipalGano.setText(R.string.TEXTO_BOTON_VOLVER_MENU_PRINCIPAL_EN);
            mensajeGanador = "¡¡Congratulations you beat the maximum score!!";
        }

    }
}
