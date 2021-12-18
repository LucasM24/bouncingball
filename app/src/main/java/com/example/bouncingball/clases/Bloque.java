package com.example.bouncingball.clases;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.bouncingball.R;

public class Bloque {

    private Paint pincel;
    private Bitmap imgBloque;
    private Bitmap imgBloqueRoto;
    private int anchoBloque;
    private int altoBloque;
    private int posX;
    private int posY;
    private int dureza;
    private int id;
    private int nroFila;
    private int nroColumna;
    private int puntaje;
    private Rect [] areasDeContacto;
    private Rect modelo;




    public Bloque (int posX, int posY, int ancho, int alto, int est, Bitmap imgBloque, Bitmap imgBloqueRoto, int nroColumna, int nroFila, int id){
        this.posX=posX;
        this.posY=posY;
        this.anchoBloque=ancho;
        this.altoBloque=alto;
        this.imgBloque = imgBloque;
        this.imgBloqueRoto = imgBloqueRoto;
        this.dureza=est;
        this.id=id;
        this.puntaje=5;
        this.modelo = new Rect(posX, posY, posX + ancho, posY + alto);
        this.id=id;
        this.nroColumna=nroColumna;
        this.nroFila=nroFila;
        this.areasDeContacto = this.inicializarAreasDeContacto();
    }

    public Bloque (int posX, int posY, int ancho, int alto, Bitmap imgBloque, int est, int nroColumna, int nroFila, int id){
        this.posX=posX;
        this.posY=posY;
        this.anchoBloque=ancho;
        this.altoBloque=alto;
        //this.pincel=pincel;
        this.imgBloque=imgBloque;
        this.dureza=est;
        this.id=id;
        this.puntaje=100;

        this.nroColumna=nroColumna;
        this.nroFila=nroFila;
        this.areasDeContacto = this.inicializarAreasDeContacto();

    }

    private Rect[] inicializarAreasDeContacto(){
        int velocidad=13;
        Rect [] areas=new Rect[8];
        //Esquina 1
        Rect area1=new Rect(posX,posY,posX+velocidad-1,posY+velocidad-1);
        Rect area2=new Rect(posX+velocidad,posY,posX+anchoBloque-velocidad,posY+velocidad);
        //Esquina 2
        Rect area3=new Rect(posX+anchoBloque-velocidad+1,posY,posX+anchoBloque,posY+velocidad-1);
        Rect area4=new Rect(posX,posY+velocidad,posX+velocidad,posY+altoBloque-velocidad);
        Rect area5=new Rect(posX+getAnchoBloque()-velocidad,posY+velocidad,posX+getAnchoBloque(),posY+getAltoBloque()-velocidad);
        //Esquina 3
        Rect area6=new Rect(posX,posY+getAltoBloque()-velocidad-1,posX+velocidad-1,posY+getAltoBloque());
        Rect area7=new Rect(posX+velocidad,posY+getAltoBloque()-velocidad,posX+getAnchoBloque()-velocidad,posY+getAltoBloque());
        //Esquina 4
        Rect area8=new Rect(posX+getAnchoBloque()-velocidad+1,posY+getAltoBloque()-velocidad+1,posX+getAnchoBloque(),posY+getAltoBloque());

        areas[0]=area1;
        areas[1]=area2;
        areas[2]=area3;
        areas[3]=area4;
        areas[4]=area5;
        areas[5]=area6;
        areas[6]=area7;
        areas[7]=area8;

        return areas;
    }

    public Rect getModelo(){
        return this.modelo;
    }
    public Bitmap getImagen(){
        return this.imgBloque;
    }
    public void setImagen(Bitmap imgBloque){
        this.imgBloque = imgBloque;
    }

    public int getLeft(){
        return this.posX ;
    }
    public int getTop(){
        return this.posY;
    }
    public int getRight(){
        return this.posX+this.getAnchoBloque();
    }
    public int getBottom(){
        return this.posY+this.getAltoBloque();
    }
    public int getNroColumna() {
        return nroColumna;
    }

    public void setNroColumna(int nroColumna) {
        this.nroColumna = nroColumna;
    }

    public int getNroFila() {
        return nroFila;
    }

    public int getDureza() {
        return dureza;
    }

    public void setDureza(int dureza) {
        this.dureza = dureza;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paint getPincel() {
        return pincel;
    }

    public void setPincel(Paint pincel) {
        this.pincel = pincel;
    }


    public int getAnchoBloque() {
        return anchoBloque;
    }

    public int getAltoBloque() {
        return altoBloque;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getAreaDeContacto(Pelota pelota){
       Rect [] areas = this.areasDeContacto;
       int posArea = 0;
       boolean salir = false;
       while(posArea < areas.length && !salir){
           Rect auxRect = areas[posArea];

           if(pelota.interseccion(auxRect)){
               salir = true;
           }else{
               posArea++;
           }
       }
       if(posArea == 0){
           Rect auxRect = areas[1];
           Rect auxRect2 = areas[3];

           if(pelota.interseccion(auxRect)){
               posArea = 1;
           }else if(pelota.interseccion(auxRect2)){
               posArea = 3;
           }
       }

       if(posArea == 2){
           Rect auxRect = areas[1];

           if(pelota.interseccion(auxRect)){
               posArea = 1;
           }
       }

       if(posArea == 5){
           Rect auxRect = areas[6];

           if(pelota.interseccion(auxRect)){
               posArea = 6;
           }
       }

       return posArea;
    }
    public void setImagenBloqueRoto(){
        this.imgBloque=this.imgBloqueRoto;
    }
}