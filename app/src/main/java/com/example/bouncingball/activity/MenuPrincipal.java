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

public class MenuPrincipal extends AppCompatActivity {


    private MediaPlayer mp ;
    private Button botonJugar,botonRanking ,botonOpciones,botonSalir;
    private TextView mostrar_user ;
    private dbConexion dao ;
    private String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modelo_menu_principal);
        mp = MediaPlayer.create(this,R.raw.clic);
        // Base de Datos
        dao = new dbConexion(this);
        nombreUsuario =  getIntent().getExtras().getString("id_user");
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

        }else{
            // reinicio cambio de nivel a no
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("changelevel","no");
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
        System.out.println("Cambio de nivel:    ******* "+cambioNivel+" *********");
        if(!cambioNivel.equalsIgnoreCase("si")){

            SharedPreferences.Editor editor = preferences.edit();
           // int level = preferences.getInt("level",1);
            editor.putInt("level", 1);
            editor.commit();

        }
        //Carga Activity.
        actualizarIdioma();
       // actualizarPuntaje();

    }
 /*   private void actualizarPuntaje(){

        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String user = preferences.getString("user","vacio");
        int mypuntaje = preferences.getInt("user_puntaje",0);
        dao.updatePuntaje(user,mypuntaje);

    }*/
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
        Intent jugar = new Intent(MenuPrincipal.this, Principal.class);
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", nombreUsuario);
        editor.commit();

        startActivity(jugar);
    }
    public void mostrar(View v){
     mp.start();
     Intent mostrarActivity = new Intent(MenuPrincipal.this, PuntajeJugador.class);
        mostrarActivity.putExtra("id_user2", nombreUsuario);
     startActivity(mostrarActivity);


    }
    public void opciones(View v){
        mp.start();
        Intent k = new Intent(MenuPrincipal.this, Opciones.class);
        k.putExtra("id_user2", nombreUsuario);
        startActivity(k);

    }

    public void anterior(View v) {
        onBackPressed();
        System.out.println("*******Metodo del Evento Activity : Salir del juego GameView*********");
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String user = preferences.getString("user","vacio");
        Intent menu = new Intent(MenuPrincipal.this, IniciarSesion.class);
        menu.putExtra("id_user",user);
        startActivity(menu);
    }



    private void actualizarIdioma(){

        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);

        String idioma_user = preferences.getString("idioma","es");

        if(idioma_user.equalsIgnoreCase("es")){
          botonJugar.setText(R.string.TEXTO_BOTON_JUGAR);
          botonRanking.setText(R.string.TEXTO_BOTON_RANKING);
          botonOpciones.setText(R.string.TEXTO_BOTON_OPCIONES);
          botonSalir.setText(R.string.TEXTO_BOTON_CERRAR_SESION);
          mostrar_user.setText("Hola: " + this.nombreUsuario );

        }else{
            botonJugar.setText(R.string.TEXTO_BOTON_JUGAR_EN);
            botonRanking.setText(R.string.TEXTO_BOTON_RANKING_EN);
            botonOpciones.setText(R.string.TEXTO_BOTON_OPCIONES_EN);
            botonSalir.setText(R.string.TEXTO_BOTON_CERRAR_SESION_EN);
            mostrar_user.setText("Hello: " + this.nombreUsuario );
        }

    }

}
