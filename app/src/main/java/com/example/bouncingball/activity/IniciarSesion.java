package com.example.bouncingball.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bouncingball.R;
import com.example.bouncingball.clases.Usuario;
import com.example.bouncingball.database.dbConexion;

public class IniciarSesion extends AppCompatActivity {

    private EditText usuario ;
    private EditText clave;
    private dbConexion dao ;
    private Button iniciarSesion , registrarse, salir ;
    private MediaPlayer mp ;
    private String tituloAlertaSalir;
    private String textoAlertaSalir;
    private String textoSiAlertaSalir;
    private String textoNoAlertaSalir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modelo_iniciar_sesion);
        tituloAlertaSalir ="¿Desea salir del juego?";
        this.textoSiAlertaSalir = "Sí";
        textoNoAlertaSalir = "No";
        usuario = findViewById(R.id.editUser);
        clave = (EditText)findViewById(R.id.editPassword);
        clave.setTransformationMethod(new PasswordTransformationMethod());
        iniciarSesion = findViewById(R.id.loginButton);
        registrarse = findViewById(R.id.registerButton);
        salir = findViewById(R.id.exitButton);
        mp = MediaPlayer.create(this,R.raw.clic);
        dao = new dbConexion(this);
        // Actualizo algunos datos del puntaje
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Nivel", "Facil");
        editor.putInt("user_puntaje",0);
        editor.putInt("vidas",3);
        editor.commit();



    }

    protected void onResume() {
        super.onResume();

        //Carga Activity.
        actualizarIdioma();

    }

    public void logins(View v){
        mp.start();
        Usuario us = new Usuario(usuario.getText().toString(),clave.getText().toString());

       if(dao.consultarUsuario(us)!=null) {

            Intent menu = new Intent(IniciarSesion.this, MenuPrincipal.class);
            menu.putExtra("id_user",us.getUsuario());
            startActivity(menu);

      }else{
            Toast.makeText(this, "User/Password Incorrectos" , Toast.LENGTH_SHORT).show();
        }
    }
    public void register(View v){
        mp.start();
        Intent menu = new Intent(IniciarSesion.this, RegistroUsuario.class);
        startActivity(menu);
    }

    private void actualizarIdioma(){
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);

        String idioma_user = preferences.getString("idioma","es");

        if(idioma_user.equalsIgnoreCase("es")){
            usuario.setHint(R.string.TEXTO_PISTA_CAMPO_USUARIO);
            clave.setHint(R.string.TEXTO_PISTA_CAMPO_CONTRASENIA);
            iniciarSesion.setText(R.string.TEXTO_BOTON_INICIAR_SESION);
            registrarse.setText(R.string.TEXTO_BOTON_REGISTRARSE);
            tituloAlertaSalir ="¿Desea salir del juego?";
            textoSiAlertaSalir = "Sí";
            textoNoAlertaSalir = "No";
            salir.setText(R.string.TEXTO_BOTON_SALIR);
        }else{
            usuario.setHint(R.string.TEXTO_PISTA_CAMPO_USUARIO_EN);
            clave.setHint(R.string.TEXTO_PISTA_CAMPO_CONTRASENIA_EN);
            iniciarSesion.setText(R.string.TEXTO_BOTON_INICIAR_SESION_EN);
            registrarse.setText(R.string.TEXTO_BOTON_REGISTRARSE_EN);
            tituloAlertaSalir ="¿Do you want to quit the game?";
            this.textoSiAlertaSalir = "Yes";
            textoNoAlertaSalir = "No";
            salir.setText(R.string.TEXTO_BOTON_SALIR_EN);
        }
    }

    public void salirDeAplicacion(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(this.tituloAlertaSalir);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(this.textoSiAlertaSalir,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton(this.textoNoAlertaSalir, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



}
