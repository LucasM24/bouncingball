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

public class Perdedor extends AppCompatActivity {

    TextView mostrar_user ;
    private dbConexion dao ;
    private Button btnVolverMenuPrincipalPerdio;
    private TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modelo_perdedor);

        Bundle extra = getIntent().getExtras();
        String name_user = extra.getString("id_user");
        //mostrar_user = (TextView) findViewById(R.id.textView4);
        //mostrar_user.setText(name_user);
        btnVolverMenuPrincipalPerdio =findViewById(R.id.salirDelJuego);
        titulo = findViewById(R.id.tituloPerdiste);
        dao = new dbConexion(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarIdioma();
    }

    public void volverAlMenuJuego(View v){

        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);

        // Recuperar clave-valor
        String user = preferences.getString("user","vacio");
        int mypuntaje = preferences.getInt("user_puntaje",0);

        // Editar el Archivo clave-valor
        SharedPreferences.Editor editor = preferences.edit();

        // actualizar los cambios por defecto
        editor.putInt("user_puntaje",0);
        editor.putInt("level", 1);
        editor.putString("changelevel","no");
        editor.apply();

        Usuario us = dao.consultarPuntaje(user);
        if (mypuntaje > us.getPuntaje()) {
            final int i = dao.updatePuntaje(user, mypuntaje);
        }

        Intent menu = new Intent(Perdedor.this, MenuPrincipal.class);
        menu.putExtra("id_user",user);
        startActivity(menu);

    }

    private void actualizarIdioma(){

        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String idioma_user = preferences.getString("idioma","es");

        if(idioma_user.equalsIgnoreCase("es")){
            titulo.setText(R.string.TEXTO_PERDISTE);
            btnVolverMenuPrincipalPerdio.setText(R.string.TEXTO_BOTON_VOLVER_MENU_PRINCIPAL_ES);
        }else{
            titulo.setText(R.string.TEXTO_PERDISTE_EN);
            btnVolverMenuPrincipalPerdio.setText(R.string.TEXTO_BOTON_VOLVER_MENU_PRINCIPAL_EN);
        }
    }

}