package com.example.bouncingball.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bouncingball.R;

public class GameOverActivity extends AppCompatActivity {

    TextView mostrar_user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Bundle extra = getIntent().getExtras();
        String name_user = extra.getString("id_user");
        mostrar_user = (TextView) findViewById(R.id.textView4);
        mostrar_user.setText(name_user);
    }
    public void volverAlMenuJuego(View v){
        System.out.println("*******Metodo del Evento Activity : Salir del juego GameView*********");
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //int level = preferences.getInt("level",1);
        editor.putInt("level", 1);
        editor.commit();
        editor.putString("changelevel","no");
        editor.commit();
        String user = preferences.getString("user","vacio");
        Intent menu = new Intent(GameOverActivity.this, MainActivity.class);
        menu.putExtra("id_user",user);
        startActivity(menu);

    }
}