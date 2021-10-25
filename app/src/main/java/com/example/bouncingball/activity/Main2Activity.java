package com.example.bouncingball.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bouncingball.R;
import com.example.bouncingball.clases.Usuario;
import com.example.bouncingball.database.dbConexion;

public class Main2Activity extends AppCompatActivity {

    private EditText campoUsuario;
    private EditText campoClave;
    private EditText campoEmail ;
    private dbConexion dao ;
    private Button botonAceptar, botonVolver ;
    private MediaPlayer mp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        campoUsuario = findViewById(R.id.editUsuario);
        campoClave = findViewById(R.id.editClave);
        campoEmail = findViewById(R.id.editEmail);
        botonAceptar = findViewById(R.id.button4);
        botonVolver = findViewById(R.id.botonVolver);
        mp = MediaPlayer.create(this,R.raw.clic);
        dao = new dbConexion(this);

    }
    protected void onResume() {
        super.onResume();

        //Carga Activity.
        actualizarIdioma();

    }
    private void actualizarIdioma(){
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        String idioma_user = preferences.getString("idioma","es");

        if(idioma_user.equalsIgnoreCase("es")){
            campoUsuario.setHint(R.string.TEXTO_PISTA_CAMPO_USUARIO);
            campoClave.setHint(R.string.TEXTO_PISTA_CAMPO_CONTRASENIA);
            campoEmail.setHint(R.string.TEXTO_PISTA_CAMPO_CORREO);
            botonAceptar.setText(R.string.TEXTO_BOTON_ACEPTAR);
            botonVolver.setText(R.string.TEXTO_BOTON_VOLVER);
        }else{
            campoUsuario.setHint(R.string.TEXTO_PISTA_CAMPO_USUARIO_EN);
            campoClave.setHint(R.string.TEXTO_PISTA_CAMPO_CONTRASENIA_EN);
            campoEmail.setHint(R.string.TEXTO_PISTA_CAMPO_CORREO_EN);
            botonAceptar.setText(R.string.TEXTO_BOTON_ACEPTAR_EN);
            botonVolver.setText(R.string.TEXTO_BOTON_VOLVER_EN);
        }
    }

    public void guardar(View view){
       mp.start();
       Usuario a = new Usuario (campoUsuario.getText().toString(),campoClave.getText().toString(),campoEmail.getText().toString(),0);

        if(!campoUsuario.getText().toString().isEmpty() && !campoClave.getText().toString().isEmpty() && !campoEmail.getText().toString().isEmpty())
        {

           long  id = dao.insertarUser(campoUsuario.getText().toString(),campoClave.getText().toString(),campoEmail.getText().toString());
            if(id>0) {
                Toast.makeText(this, "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show();


                Intent menu = new Intent(Main2Activity.this, MainActivity.class);
                menu.putExtra("id_user",a.getUsuario());
                startActivity(menu);
                campoUsuario.setText("");
                campoClave.setText("");
                campoEmail.setText("");
            }else{
                Toast.makeText(this, "Error al GUARDAR REGISTRO", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debe llenar todos los campos " , Toast.LENGTH_SHORT).show();
        }
    }

    public void exit(View view){
        finish();
    }

    public void anterior(View v) {
        onBackPressed();
    }

    }
