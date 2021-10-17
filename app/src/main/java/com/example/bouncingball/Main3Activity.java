package com.example.bouncingball;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ListView lista ;
    private UsuarioDB db ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        lista = (ListView) findViewById(R.id.LVMostrar);
        db  = new UsuarioDB(this);
        llenarLista();

    }

    public void llenarLista(){

      if(db!=null){

           SQLiteDatabase base = db.getBaseDatos();

           Cursor c = base.rawQuery("SELECT usuario  from usuario  ",null);

          // Nos aseguramos que al menos exista un registro

          if(c.moveToFirst()){

              // Recorremos el cursor hasta que no haya ningun registro

              do{

                  usuarios.add(new Usuario(c.getString(0)));
                //  usuarios.add(new Usuario(c.getString(2)));

              }while(c.moveToNext());


          }

          String [] arreglo = new String[usuarios.size()];

          for(int i = 0 ;i<arreglo.length;i++){

                      arreglo[i]= usuarios.get(i).getUsuario();
                      //  arreglo[i][1] = usuarios.get(i).getPais();

          }


          ArrayAdapter<String> adaptador = new ArrayAdapter<String>(Main3Activity.this,R.layout.support_simple_spinner_dropdown_item,arreglo);
          lista.setAdapter(adaptador);

           }

    }


}
