package com.example.bouncingball.clases;

import android.graphics.Rect;

import androidx.core.math.MathUtils;

public class Pelota {
    private int x;
    private int y;
    private int tamanio;
    private int direccionEnX;
    private int direccionEnY;
    private int velocidad;
    private int radio;
    private int posAnteriorX;
    private int posAnteriorY;
    private int centroX ;
    private int centroY ;

    private int posSiguienteX;
    private int posSiguienteY;

    public Pelota(){

    }

    public Pelota(int posX, int posY, int tamanio, int velocidad) {
        this.x = posX;
        this.y = posY;
        this.tamanio = tamanio;
        this.velocidad = Math.abs(velocidad);
        this.direccionEnX= velocidad;
        this.direccionEnY= -1* Math.abs(velocidad) ;
        //Para trabajar con los rebotes
        this.posAnteriorX=posX;
        this.posAnteriorY=posY;
        this.posSiguienteX=posX;
        this.posSiguienteY=posY;
        this.centroX = posX+18;
        this.centroY = posY+18;
    }

    public int getPosX() {
        return x;
    }

    public int getPosY() {
        return y;
    }

    public int getTamanio() {
        return tamanio;
    }

    public int getDireccionEnX() {
        return direccionEnX;
    }

    public int getDireccionEnY() {
        return direccionEnY;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setDireccionEnY(int direccionEnY) {
        this.direccionEnY = direccionEnY;
    }

    public void setDireccionEnX(int direccionEnX) {
        this.direccionEnX = direccionEnX;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //Metodos
    public void actualizarPosicion(){
        this.posAnteriorX=this.x;
        this.posAnteriorY=this.y;

        this.x=this.x+this.direccionEnX;
        this.y=this.y+this.direccionEnY;

        this.posSiguienteX=this.x+this.direccionEnX;
        this.posSiguienteY=this.y+this.direccionEnY;
    }
    public int getPosSiguienteX() {
        return posSiguienteX;
    }

    public int getPosSiguienteY() {
        return posSiguienteY;
    }

    public String getDireccion(){
        String salida = "";
        if(this.posAnteriorY<this.getPosY()){
            salida = "abajo";
        }else{
            salida = "arriba";
        }
        if(this.posAnteriorX<this.getPosX()){
            salida += "Derecha";
        }else{
            salida += "Izquierda";
        }
        return salida;
    }

    public boolean interseccion(Rect rect){
        boolean band = false ;
        int centroDeX = this.x + (this.tamanio / 2);
        int centroDeY = this.y + (this.tamanio / 2);
        int radio = this.tamanio / 2;
        // clamp(value, min, max) - limita el valor al rango mín. máx.

        // Encuentra el punto más cercano al círculo dentro del rectángulo
        float closestX = MathUtils.clamp(centroDeX, rect.left, rect.right);
        float closestY = MathUtils.clamp(centroDeY, rect.top, rect.bottom);

        // Calcula la distancia entre el centro del círculo y este punto más cercano
        float distanceX = centroDeX - closestX;//5 - 4=1
        float distanceY = centroDeY - closestY;//4 - 3=1

        // Si la distancia es menor que el radio del círculo, se produce una intersección.
        float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);//2
        //2<100
        if(distanceSquared < (radio * radio)){
            band  = true ;
        }
        return band ;
    }

    public void setDireccion (int area, String contacto){
        if(contacto.equalsIgnoreCase("Jugador")){
            if(area >= 0) {
                if (this.getDireccion().equals("abajoIzquierda") || this.getDireccion().equals("abajoDerecha")) {
                    if (this.getDireccion().equals("abajoIzquierda") && area == 4 || this.getDireccion().equals("abajoDerecha") && area == 0) {
                        this.setDireccionEnX(-1 * this.getDireccionEnX());
                        this.setDireccionEnY(-1 * this.getDireccionEnY());
                    } else {
                        int nuevaDireccion;
                        if (area == 0 || area == 4) {
                            nuevaDireccion = (this.getVelocidad() * 33) / 100;
                        } else if (area == 1 || area == 3) {
                            nuevaDireccion = (this.getVelocidad() * 110) / 100;
                        } else {
                            nuevaDireccion = (this.getVelocidad() * 180) / 100;
                        }
                        this.setDireccionEnY(-1 * (nuevaDireccion));
                    }
                }
            }
        }else{
            if(area == 1 || area == 6){
               // System.out.println("choco en el area "+area+" en uno de los centros");
                this.setDireccionEnY(-1 * this.getDireccionEnY());
            }
            else if(area == 3 || area == 4){
                //System.out.println("choco en el area "+area+" en uno de los laterales");
                this.setDireccionEnX(-1 * this.getDireccionEnX());
            }//Aca comienzan las esquinas
            else if(area == 0 && !this.getDireccion().equals("abajoDerecha")){
                if(this.getDireccion().equals("arribaDerecha")){
                  //  System.out.println("choco en el area "+area+" en la direccion arriba a la derecha");
                    this.setDireccionEnX(-1 * this.getDireccionEnX());
                }else if(this.getDireccion().equals("abajoIzquierda")){
                 //   System.out.println("choco en el area "+area+" en la direccion abajo a la izquierda");
                    this.setDireccionEnY(-1 * this.getDireccionEnY());
                }
            }else if(area == 2 && !this.getDireccion().equals("abajoIzquierda")){
                if(this.getDireccion().equals("arribaIzquierda")){
                  //  System.out.println("choco en el area "+ area +" en la direccion arriba la izquierda ");
                    this.setDireccionEnX(-1 * this.getDireccionEnX());
                }else if(this.getDireccion().equals("abajoDerecha")){
                    this.setDireccionEnY(-1 * this.getDireccionEnY());
                }
            }else if(area == 5 && !this.getDireccion().equals("arribaDerecha")){
                if(this.getDireccion().equals("abajoDerecha")){
                   // System.out.println("choco en el area "+area+" en la direccion abajo a la derecha");
                    this.setDireccionEnX(-1 * this.getDireccionEnX());
                }else if(this.getDireccion().equals("arribaIzquierda")){
                  //  System.out.println("choco en el area "+ area +" en la direccion arriba la izquierda ");
                    this.setDireccionEnY(-1 * this.getDireccionEnY());
                }
            }else if(area == 7 && !this.getDireccion().equals("arribaIzquierda")){
                if(this.getDireccion().equals("arribaDerecha")){
                  //  System.out.println("choco en el area "+area+" en la direccion arriba a la derecha");
                    this.setDireccionEnY(-1 * this.getDireccionEnY());
                }else if(this.getDireccion().equals("abajoIzquierda")){
                  //  System.out.println("choco en el area "+area+" en la direccion abajo a la izquierda");
                    this.setDireccionEnX(-1 * this.getDireccionEnX());
                }
            }else{
              //  System.out.println("choco en el area "+area+" sale en direccion correcta");
                this.setDireccionEnX(-1 * this.getDireccionEnX());
                this.setDireccionEnY(-1 * this.getDireccionEnY());
            }
        }

    }
}
