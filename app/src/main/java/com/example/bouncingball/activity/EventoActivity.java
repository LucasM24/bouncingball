package com.example.bouncingball.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.bouncingball.R;

public class EventoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

    }

    private void continuarJugando(View v){
        /*
        * Deberia volver al GameView
        * */
        onBackPressed();
    }
    public void subirNivel(View v){

    }
    public void salirDelJuego(View V){

    }

}