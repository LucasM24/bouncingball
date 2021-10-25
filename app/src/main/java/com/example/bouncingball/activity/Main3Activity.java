package com.example.bouncingball.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bouncingball.R;
import com.example.bouncingball.clases.Usuario;
import com.example.bouncingball.database.dbConexion;
import com.example.bouncingball.listas.ListaUsuarioAdapter;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    private ArrayList<Usuario> listaArrayUsuarios;
    private ListView lista;
    private dbConexion db;
    private RecyclerView listaplayers;
    private TextView mostrar_user ,puntajemax;
    private MediaPlayer mp ;
    private Button btn_regresar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        listaplayers = findViewById(R.id.listaplayers);
        listaplayers.setLayoutManager(new LinearLayoutManager(this));
        puntajemax = (TextView)findViewById(R.id.idJugadorPuntajeMax);
        btn_regresar = (Button)findViewById(R.id.button);
        mp = MediaPlayer.create(this,R.raw.clic);
        recibir_date();
        db = new dbConexion(this);
        listaArrayUsuarios = new ArrayList<>();
        ListaUsuarioAdapter adapter = new ListaUsuarioAdapter(db.mostrarUsuario());
        if (adapter.getItemCount() > 0) {
            System.out.println("Tamanio de de la lista   " + adapter.getItemCount());
        }
        listaplayers.setAdapter(adapter);
        // llenarLista();

    }
    @Override
    protected void onResume() {
        super.onResume();

        //Carga Activity.
        actualizarIdioma();

    }

    public void recibir_date() {
        Bundle extra = getIntent().getExtras();
        String name_user = extra.getString("id_user2");
        mostrar_user = (TextView) findViewById(R.id.TextPlayers);
        mostrar_user.setText(name_user);
    }
    private void actualizarIdioma(){
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);

        String idioma_user = preferences.getString("idioma","es");

        if(idioma_user.equalsIgnoreCase("es")){

         puntajemax.setText("Jugador Puntaje Max");
         btn_regresar.setText("Regresar");

        }
        else{
            puntajemax.setText("Players  Score Max");
            btn_regresar.setText("Previous");
        }

    }

    public void anterior(View v) {
       onBackPressed();
    }

}

