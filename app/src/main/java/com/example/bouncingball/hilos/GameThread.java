package com.example.bouncingball.hilos;

import com.example.bouncingball.logica.GameView;

public class GameThread extends Thread {

    static final long FPS = 30;
    private static final String CLASE = "GameThread";
    private GameView view;
    private boolean jugando = false;
    long ticksPS;

    //Nuevo
    private boolean paused = false;
    private boolean stopped = false;

    public GameThread(GameView view) {
        this.view = view;
    }

    public void setJugando(boolean jugando) {
        jugando = jugando;
    }


    public boolean getJugando() {
        return jugando;
    }


    @Override
    /*
    public void run() {
        //1000milisegundo es 1 segundo
        long ticksPS = 1000 / FPS;//33.33 milisegundo vamos a dibujar

        long startTime;//El momento que se empezo a dibujar el cuadro
        long sleepTime;//
        int i=0;
        while (!stopped) {
                startTime = System.currentTimeMillis();//Guardo el tiempo actual
                view.postInvalidate();//Actualizar el dibujo

                //tiempo cada cuanto tengo que dibujar un cuadro
                sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
                try {
                    if (sleepTime > 0)
                        sleep(sleepTime);
                } catch (Exception e) {
                    System.out.println("Exception capturada ");
                    Log.e(CLASE, "Error " + e.getMessage()+" Exception");
                }


        }
        System.out.println("Fin del juego");
    }
    */
    public void run() {
        //Date t0 = new Date();
        ticksPS = 500 / FPS;//33.33 milisegundo vamos a dibujar

        long startTime;//El momento que se empezo a dibujar el cuadro
        long sleepTime;//

        while (!stopped) {

            try {
                synchronized (this) {
                    if (paused) {
                        System.out.println("Paused");
                        wait();
                        System.out.println("Resumed");
                    }
                    startTime = System.currentTimeMillis();//Guardo el tiempo actual
                    view.postInvalidate();//Actualizar el dibujo

                    //tiempo cada cuanto tengo que dibujar un cuadro
                    sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
                }
                if (sleepTime > 0)
                    sleep(sleepTime);

            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
        }
    }

    //Metodos


    public void play() {
        paused = false;
        stopped = false;
        new Thread(this, "Player").start();
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void continuar() {
        paused = false;
        notify();
    }

    public synchronized void parar() {
        stopped = true;
        // If it was paused then resume and then stop
        notify();
    }
    public void aumentarVelocidad(){
        this.ticksPS = 400 / FPS;
    }


}
