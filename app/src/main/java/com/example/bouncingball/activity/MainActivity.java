package com.example.bouncingball.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
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

    private MediaPlayer mp ;
    private Button jugar,ranking ,opciones,salir;
    private TextView mostrar_user ;
    private dbConexion dao ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(this,R.raw.clic);
        // Base de Datos
        dao = new dbConexion(this);
       recibirDatos();
        /*
         * Actualizar el nivel
         * si viene de Activity Opciones no realizar ningun cambio en el nivel
         * caso contrario iniciar el juego por default en easy
         * */
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String cambioNivel = preferences.getString("changelevel","no");
        if(!cambioNivel.equalsIgnoreCase("si")){

            SharedPreferences.Editor editor = preferences.edit();
            // int level = preferences.getInt("level",1);
            editor.putInt("level", 1);
            editor.commit();

        }



    }
    @Override
    protected void onResume() {
        super.onResume();
        /*
        * Actualizar el nivel
        * si viene de Activity Opciones no realizar ningun cambio en el nivel
        * caso contrario iniciar el juego por default en easy
        * */
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String cambioNivel = preferences.getString("changelevel","no");
        if(!cambioNivel.equalsIgnoreCase("si")){

            SharedPreferences.Editor editor = preferences.edit();
           // int level = preferences.getInt("level",1);
            editor.putInt("level", 1);
            editor.commit();

        }
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
        jugar = (Button) findViewById(R.id.button5);
        ranking = (Button) findViewById(R.id.button6);
        opciones = (Button) findViewById(R.id.button7);
        salir = (Button) findViewById(R.id.exitButton);

    }
    public void play(View v){
        mp.start();
        Intent jugar = new Intent(MainActivity.this, Main4Activity.class);
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user",mostrar_user.getText().toString());
        editor.commit();

        startActivity(jugar);
    }
    public void mostrar(View v){
     mp.start();
     Intent mostrarActivity = new Intent(MainActivity.this, Main3Activity.class);
     mostrarActivity.putExtra("id_user2",mostrar_user.getText().toString());
     startActivity(mostrarActivity);


    }
    public void opciones(View v){
        mp.start();
        Intent k = new Intent(MainActivity.this, Opciones.class);
        k.putExtra("id_user2",mostrar_user.getText().toString());
        startActivity(k);

    }
    public void salirdelapp(View v){
        mp.start();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Desea Salir del Juego?");
        alertDialogBuilder
                .setMessage("Presione SI para Salir!")
                .setCancelable(false)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void actualizarIdioma(){

        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String idioma_user = preferences.getString("idioma","es");

        if(idioma_user.equalsIgnoreCase("es")){

          jugar.setText("Jugar");
          ranking.setText("Ranking");
          opciones.setText("Opciones");
          salir.setText("Salir");

        }
        else{

            jugar.setText("Play");
            ranking.setText("Ranking");
            opciones.setText("Option");
            salir.setText("Previuos");

        }

    }


}
