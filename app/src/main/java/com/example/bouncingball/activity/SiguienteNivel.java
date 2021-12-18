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

public class SiguienteNivel extends AppCompatActivity {
    TextView mostrar_user ;
    private dbConexion dao;
    private Button siguienteNivel;
    private Button volverMenuPrincipal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modelo_siguiente_nivel);

        Bundle extra = getIntent().getExtras();
        String name_user = extra.getString("id_user");
        mostrar_user = (TextView) findViewById(R.id.textView2);
        mostrar_user.setText(name_user);
        siguienteNivel=(Button) findViewById(R.id.idcontinuar);
        volverMenuPrincipal=(Button) findViewById(R.id.idsubirnivel);
        dao = new dbConexion(this);

    }
    @Override
    protected void onResume() {
        super.onResume();
        actualizarIdioma();
    }

    public void continuarJugando(View v){
        Intent jugar = new Intent(SiguienteNivel.this, Principal.class);
        startActivity(jugar);
    }

    public void salirDelJuego(View V){

        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String user = preferences.getString("user","vacio");
        // Buscar clave-valor
        int mypuntaje = preferences.getInt("user_puntaje",0);

        // Editar para guardar el Puntaje Acumulado
        SharedPreferences.Editor editor = preferences.edit();


        // Actualizar clave-valor
        editor.putInt("user_puntaje",0);
        editor.commit();
        Usuario us = dao.consultarPuntaje(user);

        if (mypuntaje > us.getPuntaje()) {
            final int i = dao.updatePuntaje(user, mypuntaje);
        }

        Intent menu = new Intent(SiguienteNivel.this, MenuPrincipal.class);
        menu.putExtra("id_user",user);
        startActivity(menu);

    }

    private void actualizarIdioma(){

        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);

        String idioma_user = preferences.getString("idioma","es");

        if(idioma_user.equalsIgnoreCase("es")){
            siguienteNivel.setText(R.string.TEXTO_BOTON_SIGUIENTE_NIVEL_ES);
            volverMenuPrincipal.setText(R.string.TEXTO_BOTON_VOLVER_MENU_PRINCIPAL_ES);
        }else{
            siguienteNivel.setText(R.string.TEXTO_BOTON_SIGUIENTE_NIVEL_EN);
            volverMenuPrincipal.setText(R.string.TEXTO_BOTON_VOLVER_MENU_PRINCIPAL_EN);

        }

    }

}
