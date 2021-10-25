package com.example.bouncingball.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bouncingball.R;
import com.example.bouncingball.database.dbConexion;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp ;
    Button botonJugar,botonRanking ,botonOpciones,botonSalir;
    TextView mostrar_user ;
    private dbConexion dao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(this,R.raw.clic);
        // Base de Datos
        dao = new dbConexion(this);
       recibirDatos();


    }
    @Override
    protected void onResume() {
        super.onResume();
        //Carga Activity.
        actualizarIdioma();
        actualizarPuntaje();

    }
    private void actualizarPuntaje(){

        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String user = preferences.getString("user","vacio");
        int mypuntaje = preferences.getInt("user_puntaje",0);
        dao.updatePuntaje(user,mypuntaje);

    }
    public void recibirDatos(){

        Bundle extra = getIntent().getExtras();
        String name_user = extra.getString("id_user");
        mostrar_user = (TextView) findViewById(R.id.textV);
        mostrar_user.setText(name_user);
        botonJugar = (Button) findViewById(R.id.button5);
        botonRanking = (Button) findViewById(R.id.button6);
        botonOpciones = (Button) findViewById(R.id.button7);
        botonSalir = (Button) findViewById(R.id.button8);

    }
    public void play(View v){
        mp.start();
        Intent botonJugar = new Intent(MainActivity.this, Main4Activity.class);
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user",mostrar_user.getText().toString());
        editor.commit();

        startActivity(botonJugar);
    }
    public void mostrar(View v){
     mp.start();
     Intent mostrarActivity = new Intent(MainActivity.this, Main3Activity.class);
        mostrarActivity.putExtra("id_user2",mostrar_user.getText().toString());
     startActivity(mostrarActivity);


    }
    public void botonOpciones(View v){
        mp.start();
        Intent k = new Intent(MainActivity.this, Opciones.class);
        k.putExtra("id_user2",mostrar_user.getText().toString());
        startActivity(k);

    }
    public void regresar(View v){
        mp.start();
        onBackPressed();

    }
    public void continuarJugando(View v){
        //mp.start();
        //onBackPressed();
        System.out.println("Continuar jugando *********** desde el Main Activity");
        onBackPressed();
    }
    private void actualizarIdioma(){
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String idioma_user = preferences.getString("idioma","es");

        if(idioma_user.equalsIgnoreCase("es")){
          botonJugar.setText(R.string.TEXTO_BOTON_JUGAR);
          botonRanking.setText(R.string.TEXTO_BOTON_RANKING);
          botonOpciones.setText(R.string.TEXTO_BOTON_OPCIONES);
          botonSalir.setText(R.string.TEXTO_BOTON_SALIR);
        }else{
            botonJugar.setText(R.string.TEXTO_BOTON_JUGAR_EN);
            botonRanking.setText(R.string.TEXTO_BOTON_RANKING_EN);
            botonOpciones.setText(R.string.TEXTO_BOTON_OPCIONES_EN);
            botonSalir.setText(R.string.TEXTO_BOTON_SALIR_EN);
        }

    }


}
