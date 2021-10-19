package com.example.bouncingball.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.bouncingball.clases.Usuario;

import java.util.ArrayList;

public class dbConexion extends DbUser {
   Context context ;

   public dbConexion(Context context){
       super(context);
       this.context = context ;
   }
   public long insertarUser(String nickUser ,String clave ,String email ){
   long id= 0 ;
     try {
         DbUser dbplayers = new DbUser(context);
         SQLiteDatabase db = dbplayers.getWritableDatabase();
         //db.delete(TABLE_USER,null,null) ;

         ContentValues values = new ContentValues();
         values.put("usuario", nickUser);
         values.put("clave", clave);
         values.put("email", email);
         values.put("puntaje",0);
         if(db!=null) {

             id = db.insert(TABLE_USER, null, values);
             System.out.println("Pasas por insertar User  ; valor de =   "+id);
         }

     }
     catch(Exception ex){
         ex.toString();
     }
       return id ;
   }
   public int updatePuntaje(String nickUser ,int nuevopuntaje){

       DbUser dbUser = new DbUser(context);
       SQLiteDatabase db = dbUser.getWritableDatabase();
       String []parametros = {nickUser};
       int puntajeAnterior ;
       //String u = us.getUsuario();
       Cursor cursorUser = null;
       Usuario date = null ;

      /* cursorUser = db.rawQuery(
               "UPDATE "+TABLE_USER+" SET "+ Campo_PUNTAJE+"=?"+nuevopuntaje+
                       " WHERE "+Campo_ID+"=?", parametros);*/

       ContentValues cv = new ContentValues();
       cv.put("puntaje",nuevopuntaje); //These Fields should be your String values of actual column names
      // cv.put("Field2","19");
      // cv.put("Field2","Male");
       int valor = db.update(TABLE_USER,cv,Campo_ID+"=?",parametros);
       if(valor>0){
           //Toast.makeText(this.context, "Puntaje Actualizado", Toast.LENGTH_SHORT).show();
       }
     /*  if(cursorUser.moveToFirst()){

           Toast.makeText(this.context, "Puntaje Actualizado", Toast.LENGTH_SHORT).show();

       }*/

       //cursorUser.close();
       return valor ;
   }
   public ArrayList<Usuario>mostrarUsuario(){

       DbUser dbUser = new DbUser(context);
       SQLiteDatabase db = dbUser.getWritableDatabase();

       ArrayList<Usuario> listaUsers = new ArrayList<>();
       Usuario us = null ;
       Cursor cursorUser = null;
       cursorUser = db.rawQuery("SELECT * FROM "+TABLE_USER,null);

       if(cursorUser.moveToFirst()){

           do{
               // nameUser , clave ,email,puntaje
               us = new Usuario();
               us.setUsuario(cursorUser.getString(0));
               us.setPuntaje(cursorUser.getInt(3));
               //us.setPuntaje(cursorUser.getString(3));
               // us.setEmail("loss@gmail.com");
               System.out.println("Cursor Columna : "+cursorUser.getColumnCount());
               System.out.println("Cursor Fila :  "+cursorUser.getCount());
               //us.setPuntaje("102");
               listaUsers.add(us);
           } while(cursorUser.moveToNext());
       }


       cursorUser.close();
       return listaUsers;
   }
   public Usuario consultarUsuario(Usuario us){

       DbUser dbUser = new DbUser(context);
       SQLiteDatabase db = dbUser.getReadableDatabase();
       String []parametros = {us.getUsuario(), us.getClave()};
       String u = us.getUsuario();
       Cursor cursorUser = null;
       Usuario date = null ;
       cursorUser = db.rawQuery("SELECT "+Campo_ID+","+Campo_CLAVE+"  FROM "+TABLE_USER+" WHERE "+Campo_ID+"=?"+" and "+Campo_CLAVE+"=?", parametros);

       if(cursorUser.moveToFirst()){

           do{
               // nameUser , clave ,email,puntaje
               date = new Usuario();
               date.setUsuario(cursorUser.getString(0));

           } while(cursorUser.moveToNext());
       }else{
           date=null;
       }


       cursorUser.close();
       return date ;
   }

}
