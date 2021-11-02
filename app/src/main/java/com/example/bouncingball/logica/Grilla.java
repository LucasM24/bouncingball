package com.example.bouncingball.logica;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.bouncingball.R;
import com.example.bouncingball.clases.Bloque;

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
    private Bloque[][] matrizBloque;
    private Paint pincelDureza =  new Paint();
    private Paint pincelMuralla =  new Paint();
    private Paint pincelTorre = new Paint();
    private Paint pincelPeon = new Paint();
    //Imagenes
    //private Bitmap imgBloqueAmarillo;
    private Bitmap imgBloqueGris;
    private Bitmap imgBloqueGrisRoto;

    private Bitmap imgBloqueAmarillo;
    private Bitmap imgBloqueAzul;
    private Bitmap imgBloqueVioleta;
    private Bitmap imgBloqueRojo;




    public Grilla(int ancho, int alto, int cantColumnas, int cantFilas, int alturaBloque,int nivel, Context contexto){
        super(contexto);
        this.anchoPantalla=ancho;
        this.altoPantalla=alto;
        this.cantidadColumnas=cantColumnas;
        this.cantidadFilas=cantFilas;
        this.altoDelBloque=alturaBloque;
        this.nivelActual=nivel;
        this.matrizBloque= new Bloque [cantFilas][cantColumnas];
        //Calculos.
        this.espacioEntreBloques=5*(this.cantidadColumnas-1);
        this.anchoBloque=((this.anchoPantalla-espacioEntreBloques)/cantidadColumnas);
        this.cargarImagenes();

        asignarPosiciones(anchoBloque,altoDelBloque,espacioEntreBloques);
       // pintarNivelDePrueba(matrizBloque);
        this.cargarNivel();
    }
    private void cargarImagenes(){
        imgBloqueAmarillo= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bloque_amarillo),this.anchoBloque,this.altoDelBloque,false);
        imgBloqueVioleta= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bloque_violeta),this.anchoBloque,this.altoDelBloque,false);
        imgBloqueAzul= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bloque_azul),this.anchoBloque,this.altoDelBloque,false);
        imgBloqueRojo= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bloque_rojo),this.anchoBloque,this.altoDelBloque,false);
        imgBloqueGris= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bloquesdureza2),this.anchoBloque,this.altoDelBloque,false);
        imgBloqueGrisRoto= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bloquesdureza2roto),this.anchoBloque,this.altoDelBloque,false);


    }

    private void asignarPosiciones(int anchoBloque, int altoDelBloque, int espacioEntreBloques){
        //float anchoBloque=(int)((this.xMax-45)/10);
        int borde=((this.anchoPantalla-espacioEntreBloques)/this.cantidadColumnas)%2;
        int id=0;

        //posicion
        int posX=0;
        int posY=120;

        //Numero aleatorio
        //numero = (int) (Math.random() * n) + 1;
        int n=4;


        for(int fil=0;fil<this.cantidadFilas;fil++){
            posX=posX+borde;
            for(int col=0;col<this.cantidadColumnas;col++){
                //int dureza=(int)(Math.random()*2);
                int numero = (int) (Math.random() * n) + 1;
                Bitmap imgAux;
                if(numero==1){
                    imgAux=imgBloqueAmarillo;
                }else if(numero==2){
                    imgAux=imgBloqueAzul;
                }else if(numero==3){
                    imgAux=imgBloqueVioleta;
                }else{
                    imgAux=imgBloqueRojo;
                }
                this.matrizBloque[fil][col]=new Bloque(posX,posY,anchoBloque,altoDelBloque,0, imgAux,imgBloqueGrisRoto, col, fil, id);
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
                //pintarNivelDePrueba();
                pintarNivelDePrueba0();
                break;
            case 1:
               //pintarNivel0();
                pintarNivelDePrueba0();
                break;
            case 2:
                //pintarlNivel1();
                pintarNivelDePrueba1();
                break;
            case 3:
                //nave();
                pintarNivelDePrueba2();
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
    private void pintarNivelDePrueba0(){
        this.cantidadBloquesPintados=1;
        //Fila0
        this.matrizBloque[0][0].setDureza(1);
    }
    private void pintarNivelDePrueba1(){
        this.cantidadBloquesPintados=2;
        //Fila0
        this.matrizBloque[0][0].setDureza(1);
        this.matrizBloque[0][1].setDureza(1);
    }
    private void pintarNivelDePrueba2(){
        this.cantidadBloquesPintados=3;
        //Fila0
        this.matrizBloque[0][0].setDureza(1);
        this.matrizBloque[0][1].setDureza(1);
        this.matrizBloque[0][2].setDureza(1);
    }

    private void pintarNivel0(){
        for (int i=0;i<this.matrizBloque.length-7;i++){
            for (int j=0;j<this.matrizBloque[0].length;j++){
                this.matrizBloque[i][j].setDureza(1);
                this.cantidadBloquesPintados+=1;
            }
        }
        pintarLosBloquesNivelO();
    }
    private void pintarLosBloquesNivelO(){
        /*
         * Definir los colores
         * */
        Paint pincelBlue = new Paint();
        pincelBlue.setColor(Color.BLUE);
        Paint pincelGreen = new Paint();
        pincelGreen.setColor(Color.GREEN);
        Paint pincelRed = new Paint();
        pincelRed.setColor(Color.RED);
        Paint pincel = new Paint();
        pincel.setColor(Color.CYAN);
        //fila 0
        this.matrizBloque[0][0].setPincel(pincelBlue);
        this.matrizBloque[0][1].setPincel(pincelBlue);
        this.matrizBloque[0][2].setPincel(pincelBlue);
        this.matrizBloque[0][3].setPincel(pincelBlue);
        this.matrizBloque[0][4].setPincel(pincelBlue);
        this.matrizBloque[0][5].setPincel(pincelBlue);
        this.matrizBloque[0][6].setPincel(pincelBlue);
        // fila 1
        this.matrizBloque[1][0].setPincel(pincelGreen);
        this.matrizBloque[1][1].setPincel(pincelGreen);
        this.matrizBloque[1][2].setPincel(pincelGreen);
        this.matrizBloque[1][3].setPincel(pincelGreen);
        this.matrizBloque[1][4].setPincel(pincelGreen);
        this.matrizBloque[1][5].setPincel(pincelGreen);
        this.matrizBloque[1][6].setPincel(pincelGreen);
        // fila 2 yellow , 3
        // fila 4
        this.matrizBloque[4][0].setPincel(pincel);
        this.matrizBloque[4][1].setPincel(pincel);
        this.matrizBloque[4][2].setPincel(pincel);
        this.matrizBloque[4][3].setPincel(pincel);
        this.matrizBloque[4][4].setPincel(pincel);
        this.matrizBloque[4][5].setPincel(pincel);
        this.matrizBloque[4][6].setPincel(pincel);
        // fila 5
        this.matrizBloque[5][0].setPincel(pincelRed);
        this.matrizBloque[5][1].setPincel(pincelRed);
        this.matrizBloque[5][2].setPincel(pincelRed);
        this.matrizBloque[5][3].setPincel(pincelRed);
        this.matrizBloque[5][4].setPincel(pincelRed);
        this.matrizBloque[5][5].setPincel(pincelRed);
        this.matrizBloque[5][6].setPincel(pincelRed);
        // fila 6
        this.matrizBloque[6][0].setPincel(pincelRed);
        this.matrizBloque[6][1].setPincel(pincelRed);
        this.matrizBloque[6][2].setPincel(pincelRed);
        this.matrizBloque[6][3].setPincel(pincelRed);
        this.matrizBloque[6][4].setPincel(pincelRed);
        this.matrizBloque[6][5].setPincel(pincelRed);
        this.matrizBloque[6][6].setPincel(pincelRed);

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
        pintarBloqueNivel1();
    }

    private void pintarBloqueNivel1(){
        Paint pincelBlue = new Paint();
        pincelBlue.setColor(Color.BLUE);
        Paint pincelR = new Paint();
        pincelR.setColor(Color.RED);
        this.matrizBloque[0][3].setImagen(imgBloqueVioleta);
        this.matrizBloque[1][2].setImagen(imgBloqueVioleta);
        this.matrizBloque[1][4].setImagen(imgBloqueVioleta);
        this.matrizBloque[2][1].setImagen(imgBloqueVioleta);
        this.matrizBloque[2][5].setImagen(imgBloqueVioleta);
        this.matrizBloque[3][0].setImagen(imgBloqueVioleta);
        this.matrizBloque[3][6].setImagen(imgBloqueVioleta);
        this.matrizBloque[4][0].setImagen(imgBloqueVioleta);
        this.matrizBloque[4][6].setImagen(imgBloqueVioleta);
        this.matrizBloque[5][0].setImagen(imgBloqueVioleta);
        this.matrizBloque[5][6].setImagen(imgBloqueVioleta);
        this.matrizBloque[6][0].setImagen(imgBloqueVioleta);
        this.matrizBloque[6][6].setImagen(imgBloqueVioleta);
        this.matrizBloque[7][1].setImagen(imgBloqueVioleta);
        this.matrizBloque[7][5].setImagen(imgBloqueVioleta);
        this.matrizBloque[8][2].setImagen(imgBloqueVioleta);
        this.matrizBloque[8][4].setImagen(imgBloqueVioleta);
        this.matrizBloque[9][3].setImagen(imgBloqueVioleta);

        this.matrizBloque[1][3].setImagen(imgBloqueAzul);
        this.matrizBloque[2][2].setImagen(imgBloqueAzul);
        this.matrizBloque[2][4].setImagen(imgBloqueAzul);
        this.matrizBloque[3][1].setImagen(imgBloqueAzul);
        this.matrizBloque[3][5].setImagen(imgBloqueAzul);
        this.matrizBloque[4][1].setImagen(imgBloqueAzul);
        this.matrizBloque[4][5].setImagen(imgBloqueAzul);
        this.matrizBloque[5][1].setImagen(imgBloqueAzul);
        this.matrizBloque[5][5].setImagen(imgBloqueAzul);
        this.matrizBloque[6][1].setImagen(imgBloqueAzul);
        this.matrizBloque[6][5].setImagen(imgBloqueAzul);
        this.matrizBloque[7][2].setImagen(imgBloqueAzul);
        this.matrizBloque[7][4].setImagen(imgBloqueAzul);
        this.matrizBloque[8][3].setImagen(imgBloqueAzul);

        this.matrizBloque[2][3].setImagen(imgBloqueAmarillo);
        this.matrizBloque[3][2].setImagen(imgBloqueAmarillo);
        this.matrizBloque[3][4].setImagen(imgBloqueAmarillo);
        this.matrizBloque[6][2].setImagen(imgBloqueAmarillo);
        this.matrizBloque[6][4].setImagen(imgBloqueAmarillo);
        this.matrizBloque[7][3].setImagen(imgBloqueAmarillo);

        this.matrizBloque[3][3].setImagen(imgBloqueRojo);
        this.matrizBloque[4][2].setImagen(imgBloqueRojo);
        this.matrizBloque[4][3].setImagen(imgBloqueRojo);
        this.matrizBloque[4][4].setImagen(imgBloqueRojo);
        this.matrizBloque[5][2].setImagen(imgBloqueRojo);
        this.matrizBloque[5][3].setImagen(imgBloqueRojo);
        this.matrizBloque[5][4].setImagen(imgBloqueRojo);
        this.matrizBloque[6][3].setImagen(imgBloqueRojo);


    }

    public void nave(){
        this.cantidadBloquesPintados=45;
        // fila 0
        this.matrizBloque[0][0].setDureza(1);
        this.matrizBloque[0][1].setDureza(1);
        this.matrizBloque[0][2].setDureza(1);
        this.matrizBloque[0][3].setDureza(1);
        this.matrizBloque[0][4].setDureza(1);
        this.matrizBloque[0][5].setDureza(1);
        this.matrizBloque[0][6].setDureza(1);
        // fila 1
        this.matrizBloque[1][0].setDureza(1);
        this.matrizBloque[1][1].setDureza(1);
        this.matrizBloque[1][2].setDureza(1);
        this.matrizBloque[1][3].setDureza(1);
        this.matrizBloque[1][4].setDureza(1);
        this.matrizBloque[1][5].setDureza(1);
        this.matrizBloque[1][6].setDureza(1);
        //fila 2
        this.matrizBloque[2][0].setDureza(1);
        this.matrizBloque[2][1].setDureza(1);
        this.matrizBloque[2][2].setDureza(1);
        this.matrizBloque[2][3].setDureza(1);
        this.matrizBloque[2][4].setDureza(1);
        this.matrizBloque[2][5].setDureza(1);
        this.matrizBloque[2][6].setDureza(1);
        // fila 3
        this.matrizBloque[3][1].setDureza(1);
        this.matrizBloque[3][2].setDureza(1);
        this.matrizBloque[3][3].setDureza(1);
        this.matrizBloque[3][4].setDureza(1);
        this.matrizBloque[3][5].setDureza(1);
        // fila 4
        this.matrizBloque[4][2].setDureza(1);
        this.matrizBloque[4][3].setDureza(1);
        this.matrizBloque[4][4].setDureza(1);
        // fila 5
        this.matrizBloque[5][3].setDureza(1);
        // fila 6
        this.matrizBloque[6][1].setDureza(1);
        this.matrizBloque[6][2].setDureza(1);
        this.matrizBloque[6][3].setDureza(1);
        this.matrizBloque[6][4].setDureza(1);
        this.matrizBloque[6][5].setDureza(1);
        // fila 7
        this.matrizBloque[7][2].setDureza(1);
        this.matrizBloque[7][3].setDureza(1);
        this.matrizBloque[7][4].setDureza(1);
        // fila 9
        this.matrizBloque[9][0].setDureza(2);
        this.matrizBloque[9][1].setDureza(2);
        this.matrizBloque[9][2].setDureza(2);
        this.matrizBloque[9][3].setDureza(2);
        this.matrizBloque[9][4].setDureza(2);
        this.matrizBloque[9][5].setDureza(2);
        this.matrizBloque[9][6].setDureza(2);
        pintarNave();
    }
    private void pintarNave(){

        /*
         * Definir los colores
         * */

        // fila 0
        this.matrizBloque[0][0].setImagen(imgBloqueAzul);
        this.matrizBloque[0][1].setImagen(imgBloqueAzul);
        this.matrizBloque[0][2].setImagen(imgBloqueAzul);
        this.matrizBloque[0][3].setImagen(imgBloqueAzul);
        this.matrizBloque[0][4].setImagen(imgBloqueAzul);
        this.matrizBloque[0][5].setImagen(imgBloqueAzul);
        this.matrizBloque[0][6].setImagen(imgBloqueAzul);

        // fila 1
        this.matrizBloque[1][0].setImagen(imgBloqueAmarillo);
        this.matrizBloque[1][1].setImagen(imgBloqueAmarillo);
        this.matrizBloque[1][2].setImagen(imgBloqueAmarillo);
        this.matrizBloque[1][3].setImagen(imgBloqueAmarillo);
        this.matrizBloque[1][4].setImagen(imgBloqueAmarillo);
        this.matrizBloque[1][5].setImagen(imgBloqueAmarillo);
        this.matrizBloque[1][6].setImagen(imgBloqueAmarillo);

        // fila 2
        this.matrizBloque[2][0].setImagen(imgBloqueAzul);
        this.matrizBloque[2][1].setImagen(imgBloqueAzul);
        this.matrizBloque[2][2].setImagen(imgBloqueAzul);
        this.matrizBloque[2][3].setImagen(imgBloqueAzul);
        this.matrizBloque[2][4].setImagen(imgBloqueAzul);
        this.matrizBloque[2][5].setImagen(imgBloqueAzul);
        this.matrizBloque[2][6].setImagen(imgBloqueAzul);
        // fila 3
        this.matrizBloque[3][1].setImagen(imgBloqueAmarillo);
        this.matrizBloque[3][2].setImagen(imgBloqueAmarillo);
        this.matrizBloque[3][3].setImagen(imgBloqueAmarillo);
        this.matrizBloque[3][4].setImagen(imgBloqueAmarillo);
        this.matrizBloque[3][5].setImagen(imgBloqueAmarillo);
        // fila 4
        this.matrizBloque[4][2].setImagen(imgBloqueVioleta);
        this.matrizBloque[4][3].setImagen(imgBloqueVioleta);
        this.matrizBloque[4][4].setImagen(imgBloqueVioleta);
        // fila 5
        this.matrizBloque[5][3].setImagen(imgBloqueRojo);
        // fila 6
        this.matrizBloque[6][1].setImagen(imgBloqueRojo);
        this.matrizBloque[6][2].setImagen(imgBloqueRojo);
        this.matrizBloque[6][3].setImagen(imgBloqueRojo);
        this.matrizBloque[6][4].setImagen(imgBloqueRojo);
        this.matrizBloque[6][5].setImagen(imgBloqueRojo);
        // fila 7
        this.matrizBloque[7][2].setImagen(imgBloqueRojo);
        this.matrizBloque[7][3].setImagen(imgBloqueRojo);
        this.matrizBloque[7][4].setImagen(imgBloqueRojo);
        // fila 9

        this.matrizBloque[9][0].setImagen(imgBloqueGris);
        this.matrizBloque[9][1].setImagen(imgBloqueGris);
        this.matrizBloque[9][2].setImagen(imgBloqueGris);
        this.matrizBloque[9][3].setImagen(imgBloqueGris);
        this.matrizBloque[9][4].setImagen(imgBloqueGris);
        this.matrizBloque[9][5].setImagen(imgBloqueGris);
        this.matrizBloque[9][6].setImagen(imgBloqueGris);

    }



}
