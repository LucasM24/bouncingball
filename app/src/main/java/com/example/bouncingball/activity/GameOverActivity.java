package com.example.bouncingball.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.bouncingball.R;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
    }
    public void volverAlMenuJuego(View v){
        System.out.println("*******Metodo del Evento Activity : Salir del juego GameView*********");
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String user = preferences.getString("user","vacio");
        Intent menu = new Intent(GameOverActivity.this, MainActivity.class);
        menu.putExtra("id_user",user);
        startActivity(menu);

    }
}