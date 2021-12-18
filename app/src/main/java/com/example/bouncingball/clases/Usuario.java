package com.example.bouncingball.clases;

import java.io.Serializable;

public class Usuario  implements Serializable {


    private String usuario ;
    private String clave ;
    private String email ;
    private int puntaje;

    public Usuario(){
        this.usuario = null;
        this.clave = null ;
        this.email = null ;
        this.puntaje = 0;
    }
    public Usuario(String us,String pas){
        this.usuario = us ;
        this.clave = pas ;
    }
    public Usuario(String us ,String pas,String em,int p){

        this.usuario = us;
        this.clave = pas ;
        this.email = em ;
        this.puntaje = p ;
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String u) {
        this.usuario = u;
    }

    public String getClave() {
        return clave;
    }

    public int getPuntaje(){return this.puntaje;}
    public void setPuntaje(int p){

        this.puntaje = p ;
    }
 //   public String toString(){
       // return this.usuario ;
  //  }



}
