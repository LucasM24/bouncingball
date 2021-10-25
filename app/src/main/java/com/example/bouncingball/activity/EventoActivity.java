package com.example.bouncingball.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bouncingball.R;
import com.example.bouncingball.database.dbConexion;
import com.example.bouncingball.logica.GameView;

public class EventoActivity extends AppCompatActivity {
    TextView mostrar_user ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        Bundle extra = getIntent().getExtras();
        String name_user = extra.getString("id_user");
        mostrar_user = (TextView) findViewById(R.id.textView2);
        mostrar_user.setText(name_user);

    }

    public void continuarJugando(View v){
        /*
        * Deberia volver al GameView
        * */
        System.out.println("*******Metodo del Evento Activity : Deberia volver al GameView*********");

        Intent jugar = new Intent(EventoActivity.this, Main4Activity.class);
        startActivity(jugar);

        //Funciona
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        //setContentView(new GameView(getBaseContext()));
    }

    public void salirDelJuego(View V){

        System.out.println("*******Metodo del Evento Activity : Salir del juego GameView*********");
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String user = preferences.getString("user","vacio");
        Intent menu = new Intent(EventoActivity.this, MainActivity.class);
        menu.putExtra("id_user",user);
        startActivity(menu);

    }

}