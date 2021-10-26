package com.example.bouncingball.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bouncingball.R;

public class Ganador extends AppCompatActivity {
    TextView mostrar_user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modelo_ganador);

        Bundle extra = getIntent().getExtras();
        String name_user = extra.getString("id_user");
        mostrar_user = (TextView) findViewById(R.id.textView3);
        mostrar_user.setText(name_user);
    }
    public void volverAlMenu(View v){

        /*
        * Actualizar el nivel dejando en el nivel Easy
        * */
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //int level = preferences.getInt("level",1);
        editor.putInt("level", 1);
        editor.commit();
        editor.putString("changelevel","no");
        editor.commit();

        System.out.println("*******Metodo del Evento Activity : Salir del juego GameView*********");
        //SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String user = preferences.getString("user","vacio");
        Intent menu = new Intent(Ganador.this, MenuPrincipal.class);
        menu.putExtra("id_user",user);
        startActivity(menu);

    }
}
