package com.example.bouncingball;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Grilla extends GameView{

    private int espacioEntreBloques;
    private int anchoPantalla;
    private int altoPantalla;
    private int cantidadBloquesPintados;
    private int cantidadColumnas;
    private int cantidadFilas;
    private int altoDelBloque;
    private int anchoBloque;
    private int nivelActual;
    private Bloque [][] matrizBloque;




    public Grilla(int ancho, int alto, int cantColumnas, int cantFilas, int alturaBloque, Context contexto){
        super(contexto);
        this.anchoPantalla=ancho;
        this.altoPantalla=alto;
        this.cantidadColumnas=cantColumnas;
        this.cantidadFilas=cantFilas;
        this.altoDelBloque=alturaBloque;
        this.nivelActual=0;
        this.matrizBloque= new Bloque [cantFilas][cantColumnas];
        //Calculos.
        this.espacioEntreBloques=5*(this.cantidadColumnas-1);
        this.anchoBloque=((this.anchoPantalla-espacioEntreBloques)/cantidadColumnas);
        asignarPosiciones(anchoBloque,altoDelBloque,espacioEntreBloques);
       // pintarNivelDePrueba(matrizBloque);
        this.cargarNivel();
    }

    private void asignarPosiciones(int anchoBloque, int altoDelBloque, int espacioEntreBloques){
        //float anchoBloque=(int)((this.xMax-45)/10);
        int borde=((this.anchoPantalla-espacioEntreBloques)/this.cantidadColumnas)%2;
        int id=0;

        //posicion
        int posX=0;
        int posY=100;

        Bitmap imgBloqueAmarillo= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bloquedureza1),this.anchoBloque,this.altoDelBloque,false);

        for(int i=0;i<this.cantidadFilas;i++){
            posX=posX+borde;
            for(int j=0;j<this.cantidadColumnas;j++){
                //int dureza=(int)(Math.random()*2);
                this.matrizBloque[i][j]=new Bloque(posX,posY,anchoBloque,altoDelBloque,0, imgBloqueAmarillo, i, j, id);
                posX=posX+anchoBloque+5;
                //posX=posX+anchoBloque;
                id=id+1;
            }
            posY=posY+altoDelBloque+5;
            //posY=posY+altoDelBloque;
            posX=0;

        }
    }


    //Setter y Getters!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public int getCantidadFilas() {
        return cantidadFilas;
    }

    public void setCantidadFilas(int cantidadFilas) {
        this.cantidadFilas = cantidadFilas;
    }

    public int getCantidadColumnas() {
        return cantidadColumnas;
    }

    public int getCantidadBloquesPintados(){
        return this.cantidadBloquesPintados;
    }

    public void setCantidadBloquesPintados(int n){
        this.cantidadBloquesPintados=n;
    }

    public void restarBloquesPintados(){
        this.cantidadBloquesPintados-=1;
    }
    public void setCantidadColumnas(int cantidadColumnas) {
        this.cantidadColumnas = cantidadColumnas;
    }

    public Bloque getBloque(int fila,int columna){
        return this.matrizBloque[fila][columna];
    }

    public int getNivelActual(){
        return this.nivelActual;
    }

    private void cargarNivel(){
        switch (nivelActual) {
            case 0:
                pintarNivelDePrueba();
                break;
            case 1:
                pintarNivel0();
                break;
            case 2: pintarlNivel1();
                break;
            case 3: pintarNivel2(this.matrizBloque);
                break;
        }
    }

    public void avanzarUnNivel(){
        this.nivelActual++;
        this.cargarNivel();
    }

    public void reiniciarGrilla(){
        this.cargarNivel();
    }

    private void pintarNivelDePrueba(){
        this.cantidadBloquesPintados=6;
        //Fila0
        this.matrizBloque[0][0].setDureza(1);
        this.matrizBloque[0][1].setDureza(1);
        this.matrizBloque[0][2].setDureza(1);
        this.matrizBloque[0][3].setDureza(1);
        this.matrizBloque[0][4].setDureza(1);
        //Fila1
        this.matrizBloque[1][0].setDureza(1);
//        for (int i=0;i<this.matrizBloque.length-9;i++){
//            for (int j=0;j<this.matrizBloque[0].length-6;j++){
//                this.matrizBloque[i][j].setDureza(1);
//                this.cantidadBloquesPintados+=1;
//            }
//        }
    }

    private void pintarNivel0(){
        for (int i=0;i<this.matrizBloque.length-7;i++){
            for (int j=0;j<this.matrizBloque[0].length;j++){
                this.matrizBloque[i][j].setDureza(1);
                this.cantidadBloquesPintados+=1;
            }
        }
    }

    private void pintarlNivel1(){
        System.out.println("nivel 1");
        this.cantidadBloquesPintados=46;
       //Fila0
        this.matrizBloque[0][3].setDureza(1);
        //Fila1
        this.matrizBloque[1][2].setDureza(1);
        this.matrizBloque[1][3].setDureza(1);
        this.matrizBloque[1][4].setDureza(1);
       //Fila2
        this.matrizBloque[2][1].setDureza(1);
        this.matrizBloque[2][2].setDureza(1);
        this.matrizBloque[2][3].setDureza(1);
        this.matrizBloque[2][4].setDureza(1);
        this.matrizBloque[2][5].setDureza(1);
        //Fila3
        this.matrizBloque[3][0].setDureza(1);
        this.matrizBloque[3][1].setDureza(1);
        this.matrizBloque[3][2].setDureza(1);
        this.matrizBloque[3][3].setDureza(1);
        this.matrizBloque[3][4].setDureza(1);
        this.matrizBloque[3][5].setDureza(1);
        this.matrizBloque[3][6].setDureza(1);
        //Fila4
        this.matrizBloque[4][0].setDureza(1);
        this.matrizBloque[4][1].setDureza(1);
        this.matrizBloque[4][2].setDureza(1);
        this.matrizBloque[4][3].setDureza(1);
        this.matrizBloque[4][4].setDureza(1);
        this.matrizBloque[4][5].setDureza(1);
        this.matrizBloque[4][6].setDureza(1);
        //Fila5
        this.matrizBloque[5][0].setDureza(1);
        this.matrizBloque[5][1].setDureza(1);
        this.matrizBloque[5][2].setDureza(1);
        this.matrizBloque[5][3].setDureza(1);
        this.matrizBloque[5][4].setDureza(1);
        this.matrizBloque[5][5].setDureza(1);
        this.matrizBloque[5][6].setDureza(1);
        //Fila6
        this.matrizBloque[6][0].setDureza(1);
        this.matrizBloque[6][1].setDureza(1);
        this.matrizBloque[6][2].setDureza(1);
        this.matrizBloque[6][3].setDureza(1);
        this.matrizBloque[6][4].setDureza(1);
        this.matrizBloque[6][5].setDureza(1);
        this.matrizBloque[6][6].setDureza(1);
        //Fila7
        this.matrizBloque[7][1].setDureza(1);
        this.matrizBloque[7][2].setDureza(1);
        this.matrizBloque[7][3].setDureza(1);
        this.matrizBloque[7][4].setDureza(1);
        this.matrizBloque[7][5].setDureza(1);
        //Fila8
        this.matrizBloque[8][2].setDureza(1);
        this.matrizBloque[8][3].setDureza(1);
        this.matrizBloque[8][4].setDureza(1);
        //Fila 9
        this.matrizBloque[9][3].setDureza(1);



    }

    private void pintarNivel2(Bloque [][] nivel){
        //Fila5
        nivel[5][0].setDureza(0);
        nivel[5][9].setDureza(0);
        //Fila6
        nivel[6][0].setDureza(0);
        nivel[6][1].setDureza(0);
        nivel[6][8].setDureza(0);
        nivel[6][9].setDureza(0);
        //Fila7
        nivel[7][0].setDureza(0);
        nivel[7][1].setDureza(0);
        nivel[7][2].setDureza(0);
        nivel[7][7].setDureza(0);
        nivel[7][8].setDureza(0);
        nivel[7][9].setDureza(0);
        //Fila8
        nivel[8][0].setDureza(0);
        nivel[8][1].setDureza(0);
        nivel[8][2].setDureza(0);
        nivel[8][3].setDureza(0);
        nivel[8][6].setDureza(0);
        nivel[8][7].setDureza(0);
        nivel[8][8].setDureza(0);
        nivel[8][9].setDureza(0);
        //Fila9
        nivel[9][0].setDureza(0);
        nivel[9][1].setDureza(0);
        nivel[9][2].setDureza(0);
        nivel[9][3].setDureza(0);
        nivel[9][6].setDureza(0);
        nivel[9][7].setDureza(0);
        nivel[9][8].setDureza(0);
        nivel[9][9].setDureza(0);
        //Fila10
        nivel[10][0].setDureza(0);
        nivel[10][1].setDureza(0);
        //Fila11
        nivel[11][0].setDureza(0);
        nivel[11][1].setDureza(0);
        nivel[11][2].setDureza(0);
        nivel[11][3].setDureza(0);
        nivel[11][6].setDureza(0);
        nivel[11][7].setDureza(0);
        nivel[11][8].setDureza(0);
        nivel[11][9].setDureza(0);
        //Fila12
        nivel[12][0].setDureza(0);
        nivel[12][1].setDureza(0);
        nivel[12][2].setDureza(0);
        nivel[12][3].setDureza(0);
        nivel[12][4].setDureza(0);
        nivel[12][5].setDureza(0);
        nivel[12][6].setDureza(0);
        nivel[12][7].setDureza(0);
        nivel[12][8].setDureza(0);
        nivel[12][9].setDureza(0);
    }



}
