package com.example.bouncingball.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bouncingball.R;
import android.content.Context;

public class Opciones extends AppCompatActivity {

    private TextView mostrar_user ,txtNivelDelJuego;
    private CheckBox c1,c2,c3 ;
    private Switch aSwitchE ,aSwitchS;
    private MediaPlayer mp ;
    private Button btn_regresar,btn_validar ;
    private ImageButton btn_sonido ,btn_idioma ;
    private EditText usuario ;
    private EditText clave;
    private MediaPlayer mpclic ;
    private boolean encendida  ;
    private RadioButton btn_facil ,btn_intermedio,btn_dificil ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modelo_opciones_del_juego);
        mpclic = MediaPlayer.create(this,R.raw.clic);

        recibir_date();
        btn_facil = (RadioButton) findViewById(R.id.radioButton3);
        btn_intermedio = (RadioButton) findViewById(R.id.radioButton2);
        btn_dificil = (RadioButton) findViewById(R.id.radioButton);

        txtNivelDelJuego =(TextView) findViewById(R.id.textView);
        btn_regresar = (Button) findViewById(R.id.button);
        btn_sonido = (ImageButton) findViewById(R.id.imageButton);
        btn_idioma = (ImageButton) findViewById(R.id.imageButton2);
        btn_validar =  (Button) findViewById(R.id.button14);
        encendida = false ;
        usuario = findViewById(R.id.editUser);
        clave = findViewById(R.id.editPassword);
        btn_facil.setChecked(true);
        actualizarIdioma();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    public void recibir_date(){
        Bundle extra = getIntent().getExtras();
        String name_user = extra.getString("id_user2");
        mostrar_user = (TextView) findViewById(R.id.TexViewPlayers);
        mostrar_user.setText(name_user);
    }
    public void onClick(View v){
         mpclic.start();
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

         if(btn_facil.isChecked()){
          /*
          * Seleccion de modo facil los bloques son mas fragiles
          * */
//             Toast.makeText(this, "Nivel Facil", Toast.LENGTH_SHORT).show();
             editor.putInt("level",1);
             editor.commit();
         }else{
             if(btn_intermedio.isChecked()){
                 /*
                 * Seleccion de modo Intermedio cambio de la dureza del bloque
                 * Cambia la distribucion de los bloques
                 * */
//                 Toast.makeText(this, "Nivel Intermedio", Toast.LENGTH_SHORT).show();
                 editor.putInt("level",2);
                 editor.commit();
             }
             else{
                 /*
                 * Seleccion de modo Dificil
                 * */
//                 Toast.makeText(this, "Nivel Dificil", Toast.LENGTH_SHORT).show();
                 editor.putInt("level",3);
                 editor.commit();
             }
         }
        editor.putString("changelevel","si");
         editor.commit();


    }


    public void onClickIdioma(View v){

        mpclic.start();
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);

        String idioma_user = preferences.getString("idioma","es");

        if(idioma_user.equalsIgnoreCase("es")){
            btn_idioma.setImageResource(R.drawable.estadosunidos);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("idioma","en");
                editor.commit();
                actualizarIdioma();
            }
            else{
            btn_idioma.setImageResource(R.drawable.argentina);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("idioma","es");
                editor.commit();
                actualizarIdioma();
            }
        }

    private void actualizarIdioma(){

        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);

        String idioma_user = preferences.getString("idioma","es");

        if(idioma_user.equalsIgnoreCase("es")){
            btn_idioma.setImageResource(R.drawable.argentina);
            txtNivelDelJuego.setText(R.string.ETIQUETA_NIVELES);
            btn_facil.setText(R.string.ETIQUETA_NIVEL_FACIL);
            btn_intermedio.setText(R.string.ETIQUETA_NIVEL_INTERMEDIO);
            btn_dificil.setText(R.string.ETIQUETA_NIVEL_DIFICIL);
            btn_validar.setText(R.string.TEXTO_BOTON_ACEPTAR);
            btn_regresar.setText(R.string.TEXTO_BOTON_VOLVER);
        }else{
            btn_idioma.setImageResource(R.drawable.estadosunidos);
            txtNivelDelJuego.setText(R.string.ETIQUETA_NIVELES_EN);
            btn_facil.setText(R.string.ETIQUETA_NIVEL_FACIL_EN);
            btn_intermedio.setText(R.string.ETIQUETA_NIVEL_INTERMEDIO_EN);
            btn_dificil.setText(R.string.ETIQUETA_NIVEL_DIFICIL_EN);
            btn_validar.setText(R.string.TEXTO_BOTON_ACEPTAR_EN);
            btn_regresar.setText(R.string.TEXTO_BOTON_VOLVER_EN);
        }

    }
    public void onClickSound(View v){

        if(encendida){
            apagarMusica();
        }else{
            enciendeMusica();
        }

    }
    private void enciendeMusica(){
        btn_sonido.setImageResource(R.drawable.consonido);
        Intent miReproductor = new Intent(this,ServicioMusica.class);
        this.startService(miReproductor);
        encendida =!encendida;

    }
    private void apagarMusica(){
        btn_sonido.setImageResource(R.drawable.sinsonido);
        Intent miReproductor = new Intent(this,ServicioMusica.class);
        this.stopService(miReproductor);
        encendida = !encendida;

    }
    public void volver(View v){
        mpclic.start();
        onBackPressed();
    }
}
