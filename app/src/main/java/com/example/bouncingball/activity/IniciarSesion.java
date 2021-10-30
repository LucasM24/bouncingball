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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modelo_iniciar_sesion);

        usuario = findViewById(R.id.editUser);
        clave = findViewById(R.id.editPassword);
        iniciarSesion = findViewById(R.id.loginButton);
        registrarse = findViewById(R.id.registerButton);
        salir = findViewById(R.id.exitButton);
        mp = MediaPlayer.create(this,R.raw.clic);
        dao = new dbConexion(this);
        // Actualizo algunos datos del puntaje
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("puntaje_total",0);
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

      }
      else{
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
            salir.setText(R.string.TEXTO_BOTON_SALIR);
        }else{
            usuario.setHint(R.string.TEXTO_PISTA_CAMPO_USUARIO_EN);
            clave.setHint(R.string.TEXTO_PISTA_CAMPO_CONTRASENIA_EN);
            iniciarSesion.setText(R.string.TEXTO_BOTON_INICIAR_SESION_EN);
            registrarse.setText(R.string.TEXTO_BOTON_REGISTRARSE_EN);
            salir.setText(R.string.TEXTO_BOTON_SALIR_EN);
        }
    }

    public void salirDeAplicacion(View view){
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



}
