package com.example.bouncingball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    MediaPlayer mp ;
    Button siguiente ;
    Button ranking ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        siguiente = (Button) findViewById(R.id.btnRegistrarse);
        siguiente.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent siguiente = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(siguiente);


            }
        });
       /* ranking =(Button) findViewById(R.id.btnRanking);
        ranking.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent rank = new Intent(MainActivity.this,Main3Activity.class);
                startActivity(rank);


            }
        });*/


    }
    public void login(View v){

        Intent jugar = new Intent(MainActivity.this,Main4Activity.class);
        startActivity(jugar);
    }
    public void mostrar(View v){

     Intent mostrarActivity = new Intent(MainActivity.this,Main3Activity.class);
     startActivity(mostrarActivity);


    }
    public void sonido(View v){

        if(mp!=null){

            mp.release();
        }
        mp = MediaPlayer.create(this,R.raw.play);
        mp.seekTo(0);
        mp.start();
        mp.setLooping(true);
    }
    public void silencio (View v){
        /*Por ahora funciona */
        if(mp!=null){

            mp.stop();
        }

      //  mp.setLooping(false);
    }


}
