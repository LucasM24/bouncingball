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
import android.widget.TextView;
import android.widget.Toast;

import com.example.bouncingball.R;
import com.example.bouncingball.clases.Usuario;
import com.example.bouncingball.database.dbConexion;

public class LoginScreen extends AppCompatActivity {

    private EditText usuario ;
    private EditText clave;
    private dbConexion dao ;
    private Button iniciarSesion ,registrarse ;
    private MediaPlayer mp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        usuario = findViewById(R.id.editUser);
        clave = findViewById(R.id.editPassword);
        iniciarSesion = findViewById(R.id.loginButton);
        registrarse = findViewById(R.id.registerButton);
        mp = MediaPlayer.create(this,R.raw.clic);
        dao = new dbConexion(this);


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

            Intent menu = new Intent(LoginScreen.this, MainActivity.class);
            menu.putExtra("id_user",us.getUsuario());
            startActivity(menu);

      }
      else{
            Toast.makeText(this, "User/Password Incorrectos" , Toast.LENGTH_SHORT).show();
        }
    }
    public void register(View v){
        mp.start();
        Intent menu = new Intent(LoginScreen.this, Main2Activity.class);
        startActivity(menu);
    }

    private void actualizarIdioma(){
        SharedPreferences preferences = getSharedPreferences("myidiom", Context.MODE_PRIVATE);

        String idioma_user = preferences.getString("idioma","es");

        if(idioma_user.equalsIgnoreCase("es")){
            usuario.setHint("Ingrese su usuario");
            iniciarSesion.setText("Iniciar sesión");
            registrarse.setText("Registrarse");
        }else{
            usuario.setHint("Put your username");
            usuario.setText("User");
            iniciarSesion.setText("Login");
            registrarse.setText("Register");
        }
    }

    public void salirdeaplicacion(View view){
        finish();
    }

}