package com.example.bouncingball.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.bouncingball.R;
import com.example.bouncingball.clases.Usuario;
import com.example.bouncingball.database.dbConexion;
import com.example.bouncingball.listas.ListaUsuarioAdapter;

import java.util.ArrayList;

public class PuntajeJugador extends AppCompatActivity {

    private ArrayList<Usuario> listaArrayUsuarios;
    private ListView lista;
    private dbConexion db;
    private TableLayout listaplayers;
    private TextView mostrar_user ,textoPuntajeMax;
    private MediaPlayer mp ;
    private Button btn_regresar ;
    private TextView user_text ,puntaje_text,indice_text;
    private String name_user ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modelo_puntaje_jugador);
        listaplayers = findViewById(R.id.listaplayers);

      //  textoPuntajeMax = (TextView)findViewById(R.id.idJugadorPuntajeMax);
        btn_regresar = (Button)findViewById(R.id.button);
        mp = MediaPlayer.create(this,R.raw.clic);
        recibir_date();
        db = new dbConexion(this);

        // ancho de la pantalla
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels; // ancho absoluto en pixels
        int height = metrics.heightPixels; // alto absoluto en pixels
        int anchoRow = width/3;

        /*
        * Encabezado de TableLayout
        * */
        String []encabezado = {"Ranking ","Usuario","Puntaje"};

        TableRow row = new TableRow(this.getBaseContext());

        TextView textViewEncabezado ;
        for (int k=0;k<3;k++){

            textViewEncabezado = new TextView(this.getBaseContext());
            textViewEncabezado.setGravity(Gravity.CENTER);
            textViewEncabezado.setPadding(40,15,40,15);
            textViewEncabezado.setBackgroundResource(R.color.colorPrimary);
            textViewEncabezado.setText(encabezado[k]);
            textViewEncabezado.setTextColor(Color.WHITE);
            textViewEncabezado.setWidth(anchoRow);
            row.addView(textViewEncabezado);
        }
        listaplayers.addView(row);

        // recibiendo los datos de la base de datos
        listaArrayUsuarios = new ArrayList<>();
        listaArrayUsuarios = db.mostrarUsuario();
        for(int i=0 ; i<listaArrayUsuarios.size();i++) {
            Usuario us = listaArrayUsuarios.get(i);
            String []cadena = {" "+(i+1) ,us.getUsuario(),""+us.getPuntaje()};
            row = new TableRow(this.getBaseContext());
            TextView textView;
            if(cadena[1].equalsIgnoreCase(name_user)){
                for (int j = 0; j < 3; j++) {

                    textView = new TextView(this.getBaseContext());
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(40, 15, 40, 15);
                    textView.setBackgroundResource(R.color.colorAccent);
                    textView.setText(cadena[j]);
                    textView.setTextColor(Color.BLACK);
                    textView.setWidth(anchoRow);
                    row.addView(textView);

                }
            }else {

                for (int j = 0; j < 3; j++) {

                    textView = new TextView(this.getBaseContext());
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(40, 15, 40, 15);
                    textView.setBackgroundResource(R.color.colorPrimary);
                    textView.setText(cadena[j]);
                    textView.setTextColor(Color.WHITE);
                    textView.setWidth(anchoRow);
                    row.addView(textView);

                }
            }
            listaplayers.addView(row);
        }
        listaplayers.setGravity(Gravity.CENTER_VERTICAL);

    }
    @Override
    protected void onResume() {
        super.onResume();

        //Carga Activity.
        actualizarIdioma();

    }

    public void recibir_date() {
        Bundle extra = getIntent().getExtras();
        name_user = extra.getString("id_user2");
        mostrar_user = (TextView) findViewById(R.id.TextPlayers);
        mostrar_user.setText(name_user);
    }
    private void actualizarIdioma(){
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String idioma_user = preferences.getString("idioma","es");

        if(idioma_user.equalsIgnoreCase("es")){
            // textoPuntajeMax.setText(R.string.ETIQUETA_PUNTAJE_JUGADOR);
            btn_regresar.setText(R.string.TEXTO_BOTON_VOLVER);
        }else{
         //   textoPuntajeMax.setText(R.string.ETIQUETA_PUNTAJE_JUGADOR_EN);
            btn_regresar.setText(R.string.TEXTO_BOTON_VOLVER_EN);
        }

    }

    public void anterior(View v) {
       onBackPressed();
    }

}

