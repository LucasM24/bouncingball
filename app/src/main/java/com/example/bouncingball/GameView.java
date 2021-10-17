package com.example.bouncingball;

import static android.graphics.Color.*;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import androidx.core.math.MathUtils;

import java.sql.SQLOutput;
import java.util.ArrayList;


public class GameView extends SurfaceView {
	//Pinceles
	private Paint pincelIndicadores = new Paint();
	private Paint pincelPelota = new Paint();
	private Paint pincelJugador = new Paint();
	//Indicadores
	private int vidas;
	private int puntaje;
	private int nivel;
	private boolean cayo;
	//Elementos
	private Grilla grilla;
	private Pelota pelota;
	//	private Bloque jugador;
	private Jugador jugador;
	private GameThread gameThread;
	private int xMax, yMax;
	//Dedo
	private int posDedoX;
	private int posDedoY;
	//Inicio del juego
	private boolean inicioJuego = false;

	private boolean pantallaPulsada = false;

	//imagen Game over
	private boolean imgFinJuego=false;

	//imagen Gano
	private boolean imgSuperoNivel=false;
	private Bitmap bmp;
	private Bitmap pelotaImg;
	private Bitmap jugadorImg;
	private Bitmap fondoImg;


	private boolean juegoEnPausa = false;
	private boolean siguienteFotograma = false;

	private boolean futuroContacto=false;

	public GameView(Context context) {
		super(context);
		SurfaceHolder holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceCreated(SurfaceHolder surfaceHolder) {
				setWillNotDraw(false);
				nivel = 0;
				vidas = 1;
				puntaje = 0;
				cayo = false;
				xMax = getWidth();
				yMax = getHeight();

				//Ubicacion del jugador
//				 jugador= new Jugador(150,227,150,20);
				// pelota = new Pelota(jugador.getPosX(),jugador.getPosY()-15,15, 15);

				jugador = new Jugador((getWidth() / 2) - (150 / 2), getHeight() - 200, 150,20);
				pelota = new Pelota(jugador.getPosX(),jugador.getPosY()-20,20, 7);
				grilla = new Grilla(xMax, yMax, 7, 10, 40,context);

				pelotaImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pelotagris), pelota.getTamanio(), pelota.getTamanio(),false);
				jugadorImg= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.jugador), jugador.getAncho(), jugador.getAlto(),false);
				fondoImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fondo1),xMax,yMax,false);

				gameThread = new GameThread(GameView.this);
				gameThread.play();

				//gameThread.setRunning(true);

				//gameThread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

			}

			@Override
			public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

			}
		});
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//Propiedades del canvas
		super.onDraw(canvas);
		canvas.drawBitmap(fondoImg, 0, 0, null);

		//Pinceles
		pincelPelota.setColor(BLUE);
		pincelJugador.setColor(GREEN);
		pincelIndicadores.setColor(WHITE);
		pincelIndicadores.setTextSize(40f);

		//Controles
		verificarContactoPantalla();

		//pelota.actualizarPosicion();
		if (vidas >= 0) {

			//Es el ultimo bloque??
			boolean gano = this.grilla.getCantidadBloquesPintados() == 0;
			if(gano){
				this.nivelSuperado(canvas);
			}else{
				//actualizamos las posiciones
				if (!cayo) {
					//Verifico contacto si la posicion es menor que

					this.verificarContactosMultiples(canvas);
					this.pintarGrilla(canvas);


					//Aca va el metodo pintar grilla
					this.controlDelJuego(canvas);
//					testPausarJuego();

				} else {
					//La pelota pasa al jugador
					this.restarVida();
				}
				//Pinta los indicadores de vida y puntaje
				this.dibujarIndicadores(canvas);
				canvas.drawBitmap(jugadorImg, jugador.getPosX(), jugador.getPosY(), null);
			}
		} else {
			//Si pierdo todas las vidas
			this.reiniciarJuego(canvas);
		}
		//Posición del boton siguiente
		canvas.drawRect(xMax-100, yMax-100, xMax+50, yMax+50, pincelPelota);
		canvas.drawRect(0, yMax-100, 50, yMax-50, pincelPelota);
		if(siguienteFotograma){
			gameThread.pause();
			siguienteFotograma=false;
		}
	}

	//Metodos del Juego
	private ArrayList<Bloque> obtenerBloquesChocados(){
		boolean huboContacto = false;
		int contarContactos = 0;

		ArrayList<Bloque> listaBloque = new ArrayList<Bloque>();
		//Recorre todas las filas verificando si la pelota tuvo algun contacto
		for (int i = 0; i < this.grilla.getCantidadFilas(); i++){
			for (int j = 0; j < this.grilla.getCantidadColumnas(); j++){
				Bloque bloque = this.grilla.getBloque(i, j);
				//Verifica que el bloque todavia no se rompio
				if(bloque.getDureza() == 1 || bloque.getDureza() == 2){
					//optimizar: Para que si ya detecto un contacto, no siga verificando los otros bloques
//					unContacto = verificarContacto(bloque);
                    huboContacto = pelota.interseccion(bloque.getModelo());
					if(huboContacto){
						// Para hacer pruebas
						//Guardo el bloque que hizo contacto
						listaBloque.add(bloque);
						if(bloque.getDureza() == 1){
							this.puntaje+= bloque.getPuntaje();
						}else{
							//Agregar la imagen del bloque quebrado!!
//							bloque.setPincel()
						}
						contarContactos++;
					}
				}
			}
		}
		return listaBloque;
	}

	private void verificarContactosMultiples(Canvas canvas){
		Bloque [] arreBloqueContacto;
		ArrayList<Bloque> listaBloque;
		listaBloque=this.obtenerBloquesChocados();

		if(listaBloque.size() == 1){
			System.out.println("Contacto 1");
			Bloque b=listaBloque.get(0);
			contactoUnBloque(b);
		}else if(listaBloque.size() == 2){
			System.out.println("Contacto 2");
			Bloque b1=listaBloque.get(0);
			Bloque b2=listaBloque.get(1);
			contactoDosBloques(b1,b2);
		} else if(listaBloque.size() == 3){
			System.out.println("Contacto 3");
			Bloque b1=listaBloque.get(0);
			Bloque b2=listaBloque.get(1);
			Bloque b3=listaBloque.get(2);
			contactoTresBloques(b1,b2,b3);
		}

	}

	private void contactoUnBloque(Bloque bloque){
		int areaDeContacto = bloque.getAreaDeContacto(this.pelota);
//		actualizarDireccion2(b);
        this.pelota.setDireccion(areaDeContacto, "Bloque");

		if(bloque.getDureza() == 1) {
			bloque.setDureza(0);
			this.grilla.restarBloquesPintados();
		}else{
		    //Se usa cuando el bloque tiene dureza 2 (nivel dificil)
			bloque.setDureza(1);
		}

	}

	private void contactoDosBloques(Bloque b1, Bloque b2){
		//Estan horizontal
		if(b1.getNroFila()==b2.getNroFila()){
			//Tiene que desaparecer los dos y la dirección solo cambia eje Y
			if(b1.getDureza()==1 && b2.getDureza()==1) {
				b1.setDureza(0);
				b2.setDureza(0);
				this.grilla.restarBloquesPintados();
				this.grilla.restarBloquesPintados();
			}else{
				if(b1.getDureza()==2 && b2.getDureza()==2) {
					b1.setDureza(1);
					b2.setDureza(1);
				}else{
					if(b1.getDureza() == 1){
						b1.setDureza(0);
						b2.setDureza(1);
					}else{
						b2.setDureza(0);
						b1.setDureza(1);
					}
					this.grilla.restarBloquesPintados();
				}
			}
			pelota.setDireccionEnX(-1 * pelota.getDireccionEnX());
		}
		//Estan vertical
		if(b1.getNroColumna() == b2.getNroColumna()){
			System.out.println("Bloques estan Vertical");
			//Tiene que desaparecer los dos y la dirección solo cambia eje Y
			b1.setDureza(0);
			b2.setDureza(0);
			this.grilla.restarBloquesPintados();
			this.grilla.restarBloquesPintados();
			pelota.setDireccionEnY(-1 * pelota.getDireccionEnY());
		}
		//Estan en diagonal
		if(b1.getNroColumna() != b2.getNroColumna() && b1.getNroFila() != b2.getNroFila()){
			System.out.println("Bloques estan diagonal");
			//Tiene que desaparecer los dos y la dirección solo cambia eje Y
			b1.setDureza(0);
			b2.setDureza(0);
			this.grilla.restarBloquesPintados();
			this.grilla.restarBloquesPintados();
			pelota.setDireccionEnX(-1 * pelota.getDireccionEnX());
			pelota.setDireccionEnY(-1 * pelota.getDireccionEnY());
		}

	}

	private void contactoTresBloques(Bloque b1, Bloque b2, Bloque b3){
		System.out.println("Contacto Tres Bloques ");
		System.out.println("Bloque 1 Columna: " + b1.getNroColumna() + " Fila: " + b1.getNroFila());
		System.out.println("Bloque 2 Columna: " + b2.getNroColumna() + " Fila: " + b2.getNroFila());
		System.out.println("Bloque 3 Columna: " + b3.getNroColumna() + " Fila: " + b3.getNroFila());

		//Caso 1
		if(b2.getNroColumna() != b3.getNroColumna() && b2.getNroFila() != b3.getNroFila()){
			//Tiene que desaparecer los dos y la dirección solo cambia eje Y
			System.out.println("Caso 1 ");
			b2.setDureza(0);
			b3.setDureza(0);
			this.grilla.restarBloquesPintados();
			this.grilla.restarBloquesPintados();
			pelota.setDireccionEnX(-1 * pelota.getDireccionEnX());
			pelota.setDireccionEnY(-1 * pelota.getDireccionEnY());
		}
		//Caso 2 y caso 4
		if(b1.getNroColumna()!=b3.getNroColumna()&&b1.getNroFila()!=b3.getNroFila()){
			//Tiene que desaparecer los dos y la dirección solo cambia eje Y
			System.out.println("Caso 2 y 4 ");
			b1.setDureza(0);
			b3.setDureza(0);
			this.grilla.restarBloquesPintados();
			this.grilla.restarBloquesPintados();
			pelota.setDireccionEnX(-1 * pelota.getDireccionEnX());
			pelota.setDireccionEnY(-1 * pelota.getDireccionEnY());
		}
		//Caso 3
		if(b1.getNroColumna() != b2.getNroColumna() && b1.getNroFila() != b2.getNroFila()){
			//Tiene que desaparecer los dos y la dirección solo cambia eje Y
			System.out.println("Caso 3 ");
			b1.setDureza(0);
			b2.setDureza(0);
			this.grilla.restarBloquesPintados();
			this.grilla.restarBloquesPintados();
			pelota.setDireccionEnX(-1 * pelota.getDireccionEnX());
			pelota.setDireccionEnY(-1 * pelota.getDireccionEnY());
		}

	}



	private void pintarGrilla(Canvas canvas){
		for (int i = 0; i < this.grilla.getCantidadFilas(); i++){
			for (int j = 0; j < this.grilla.getCantidadColumnas(); j++){
				Bloque bloque = this.grilla.getBloque(i, j);
				//Este metodo dibuja la grilla creada anteriormente.
				if(bloque.getDureza() == 1){
					canvas.drawBitmap(bloque.getImagen(), bloque.getPosX(), bloque.getPosY(), null);
//					canvas.drawRect(bloque.getModelo(), bloque.getPincel());
				}
			}
		}
	}

	private void nivelSuperado(Canvas canvas){
		imgSuperoNivel=true;
		gameThread.pause();
		this.reubicarPelota();
		this.grilla.avanzarUnNivel();
		bmp= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ganaste),xMax,yMax,false);
		canvas.drawBitmap(bmp, 0, 0, null);
	}

	private void controlDelJuego(Canvas canvas){
		if (inicioJuego && (pelota.getY() < jugador.getPosY()) && (pelota.getY() > (jugador.getPosY() - 50))) {
			verificarContactoJugador2();
		}

		//actualizamos las posiciones
		if (pantallaPulsada) {
			//Actualizar posicion del jugador
			transicionEnX();
			jugador.inicializarAreas();
		}
		if (inicioJuego) {
			//Pelota en movimiento
			pelota.actualizarPosicion();
			canvas.drawBitmap(pelotaImg, pelota.getX(), pelota.getY(), null);
		} else {
			//La pelota esta en la posicion del jugador
			pelota.setX(jugador.getPosX() + (jugador.getAncho() / 2) - pelota.getTamanio() / 2);
			canvas.drawBitmap(pelotaImg, pelota.getX(), pelota.getY(), null);
		}
	}

	private void restarVida(){
		vidas += -1;
		//Reubicar la pelota cuando se cae
		this.reubicarPelota();
		//canvas.drawRect(pelota.getPosX(), pelota.getPosY(), pelota.getPosX() + pelota.getTamanio(), pelota.getPosY() + pelota.getTamanio(), pincelPelota);
		cayo = false;
		inicioJuego=false;
		/*
			 AlertDialog.Builder alerta=new AlertDialog.Builder(getContext());
			 alerta.setMessage("Hola 2");
			 alerta.create();
			 alerta.show();
			 System.out.println("Alerta creada 2");
			 */
	}

	private void dibujarIndicadores(Canvas canvas){
		//Nivel
		canvas.drawText("Nivel:" + String.valueOf(this.grilla.getNivelActual()), (xMax * 10) / 100, 70f, pincelIndicadores);
		//Puntaje
		canvas.drawText("Puntaje:" + String.valueOf(this.puntaje), (xMax * 40) / 100, 70f, pincelIndicadores);
		//Vidas
		if(vidas>0){
			canvas.drawText("Vidas:" + String.valueOf(this.vidas), (xMax * 75) / 100, 70f, pincelIndicadores);
		}else{
			canvas.drawText("Vidas:" + String.valueOf(0), (xMax * 75) / 100, 70f, pincelIndicadores);
		}
	}

	private void reiniciarJuego(Canvas canvas){
		System.out.println("Vuelve a iniciar el juego");
		vidas = 2;
		puntaje=0;
		imgFinJuego=true;
		this.grilla.setCantidadBloquesPintados(0);
		this.grilla.reiniciarGrilla();
		gameThread.pause();
		bmp= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.finjuego),xMax,yMax,false);
		canvas.drawBitmap(bmp, 0, 0, null);
		System.out.println("Despues de la pausa");
	}

	private void reubicarPelota(){
		pelota.setX(jugador.getPosX() + (jugador.getAncho() / 2));
		pelota.setY(jugador.getPosY()- pelota.getTamanio()-2);
		pelota.setDireccionEnX(-pelota.getVelocidad());
		pelota.setDireccionEnY(-pelota.getVelocidad());
	}

	public boolean onTouchEvent(MotionEvent evento) {
		//if (evento.getAction() == MotionEvent.ACTION_DOWN) {}
		//Pulso la pantalla
		pantallaPulsada = true;
		posDedoX = (int) evento.getX();
		posDedoY = (int) evento.getY();

		//invalidate();

		//    if(evento.getAction() == MotionEvent.ACTION_UP){}
		// MotionEvent.ACTION_DOWN
		if(imgFinJuego || imgSuperoNivel){
			if (evento.getAction() == MotionEvent.ACTION_UP) {
				//Si perdio o gano
				if(posDedoX>50&&posDedoX<(xMax-50)){
					if(posDedoY>600&&posDedoY<(yMax-300)){
						gameThread.continuar();
						imgFinJuego=false;
						imgSuperoNivel=false;
					}
				}
			}
		}else{
			if(evento.getAction() == MotionEvent.ACTION_UP){
				//System.out.println("Pulso la pantalla *********************");
				inicioJuego = true;
				pantallaPulsada = false;
			}
		}


		//Boton continuar
		if(evento.getAction() == MotionEvent.ACTION_UP){
			if(posDedoX>50&&posDedoX<(xMax-50)){
				if(posDedoY>10&&posDedoY<300){
					gameThread.continuar();
					System.out.println("Continuar jugando Boton Segui jugando #0# ");

				}
			}
		}
		if(juegoEnPausa && !siguienteFotograma){
			if (evento.getAction() == MotionEvent.ACTION_UP) {
				if(posDedoX>xMax-100){
					if(posDedoY>yMax-100){
						gameThread.continuar();
						System.out.println("Continuar jugando Boton Segui jugando [1] ");
						siguienteFotograma=true;
					}
				}
			}

		}

		if(juegoEnPausa && !siguienteFotograma){
			if (evento.getAction() == MotionEvent.ACTION_UP) {
				if(posDedoX<100){
					if(posDedoY>yMax-100){
						gameThread.continuar();
						System.out.println("Continuar jugando Boton Segui jugando *2* ");

					}
				}
			}

		}
		return true;
	}

	//Controlar rebote
	public void transicionEnX() {
		int posNueva = posDedoX - (jugador.getAncho() / 2);
		//int posNueva=posDedoX;
		int velocidadTransicion = 50;
		int rangoMovimiento = 50;
		int posJugadorCentro = jugador.getPosX() + (jugador.getAncho() / 2);
		//Verifica que el jugador no salga del limite izquierdo ni del derecho
		if(posNueva<0 || (posNueva+jugador.getAncho())>xMax){
			if(posNueva<0){
				//posNueva=-20;
				posNueva=0;
				jugador.setPosX(posNueva);

			}else{
				//posNueva=-20;
				posNueva=xMax-jugador.getAncho();
				jugador.setPosX(posNueva);
			}

		}else{
			if (((posJugadorCentro < (posNueva + rangoMovimiento)) && ((posNueva - rangoMovimiento) < posJugadorCentro))) {
				jugador.setPosX(posNueva);
			} else {
				if (jugador.getPosX() < posNueva) {
					//El bloque esta a la izquierda
					//verifico que si sumo la velocidad de transicion no supere la posicion que tiene que llegar
					if ((jugador.getPosX() + velocidadTransicion) < posNueva) {
						jugador.setPosX(jugador.getPosX()+velocidadTransicion);
					} else {
						jugador.setPosX(posNueva);
					}
				} else {
					//El bloque esta a la derecha
					if ((jugador.getPosX() - velocidadTransicion) > posNueva) {
						jugador.setPosX(jugador.getPosX()- velocidadTransicion);
					} else {
						jugador.setPosX(posNueva);
					}
				}
			}
		}

	}


	//La pelota verifica si toco algun borde de la pantalla
	public void verificarContactoPantalla() {

		//Derecha
		if (pelota.getX() + pelota.getTamanio() >= xMax) {
			pelota.setDireccionEnX(-pelota.getVelocidad());
		}

		//Izquierda
		if (pelota.getX() <= 0) {
			pelota.setDireccionEnX(pelota.getVelocidad());
		}

		//Fondo
		if (pelota.getY() + pelota.getTamanio() >= yMax) {
			pelota.setDireccionEnY(-pelota.getVelocidad());
			cayo=true;
		}

		//Techo
		if (pelota.getY() <= 100) {
			pelota.setDireccionEnY(pelota.getVelocidad());
		}

	}

	public void actualizarDireccion2(Bloque b) {
		Rect [] areasBloque=b.areaDeContacto();

		int posArea=0;
		boolean salir=false;

		while(posArea<areasBloque.length && !salir){
			Rect auxRect=areasBloque[posArea];
			if(pelota.interseccion(auxRect)){
				salir=true;
			}else{
				posArea++;
			}
		}
		System.out.println(" *************** Area despues del while es: "+posArea);

		if(posArea == 0){
			Rect auxRect = areasBloque[1];
			Rect auxRect2 = areasBloque[3];

			if(pelota.interseccion(auxRect)){
				posArea = 1;
			}else if(pelota.interseccion(auxRect2)){
				posArea = 3;
			}
		}

		if(posArea == 2){
			Rect auxRect = areasBloque[1];
			Rect auxRect2 = areasBloque[4];

			if(pelota.interseccion(auxRect)){
				posArea = 1;
			}else if(pelota.interseccion(auxRect2)){
				posArea = 4;
			}
		}

		if(posArea == 5){
			Rect auxRect = areasBloque[6];
			Rect auxRect2 = areasBloque[3];

			if(pelota.interseccion(auxRect)){
				posArea = 6;
			}else if(pelota.interseccion(auxRect2)){
				posArea = 3;
			}
		}

		if(posArea == 7){
			Rect auxRect = areasBloque[6];
			Rect auxRect2 = areasBloque[4];

			if(pelota.interseccion(auxRect)){
				posArea = 6;
			}else if(pelota.interseccion(auxRect2)){
				posArea = 4;
			}
		}

		System.out.print("Area al salir del metodo actualizarDireccion2 : "+posArea);
		System.out.println();
		updateDireccion(posArea);

	}

	private void updateDireccion(int area){
		if(area == 1 || area == 6){
			System.out.println("choco en el area "+area+" en uno de los centros");
			pelota.setDireccionEnY(-1 * pelota.getDireccionEnY());
		}
		else if(area == 3 || area == 4){
			System.out.println("choco en el area "+area+" en uno de los laterales");
			pelota.setDireccionEnX(-1 * pelota.getDireccionEnX());
		}//Aca comienzan las esquinas
		else if(area == 0 && !pelota.getDireccion().equals("abajoDerecha")){
			if(pelota.getDireccion().equals("arribaDerecha")){
				System.out.println("choco en el area "+area+" en la direccion arriba a la derecha");
				pelota.setDireccionEnX(-1 * pelota.getDireccionEnX());
			}else if(pelota.getDireccion().equals("abajoIzquierda")){
				System.out.println("choco en el area "+area+" en la direccion abajo a la izquierda");
				pelota.setDireccionEnY(-1 * pelota.getDireccionEnY());
			}
		}else if(area == 2 && !pelota.getDireccion().equals("abajoIzquierda")){
			if(pelota.getDireccion().equals("arribaIzquierda")){
				System.out.println("choco en el area "+ area +" en la direccion arriba la izquierda ");
				pelota.setDireccionEnX(-1 * pelota.getDireccionEnX());
			}else if(pelota.getDireccion().equals("abajoDerecha")){
				pelota.setDireccionEnY(-1 * pelota.getDireccionEnY());
			}
		}else if(area == 5 && !pelota.getDireccion().equals("arribaDerecha")){
			if(pelota.getDireccion().equals("abajoDerecha")){
				System.out.println("choco en el area "+area+" en la direccion abajo a la derecha");
				pelota.setDireccionEnX(-1 * pelota.getDireccionEnX());
			}else if(pelota.getDireccion().equals("arribaIzquierda")){
				System.out.println("choco en el area "+ area +" en la direccion arriba la izquierda ");
				pelota.setDireccionEnY(-1 * pelota.getDireccionEnY());
			}
		}else if(area == 7 && !pelota.getDireccion().equals("arribaIzquierda")){
			if(pelota.getDireccion().equals("arribaDerecha")){
				System.out.println("choco en el area "+area+" en la direccion arriba a la derecha");
				pelota.setDireccionEnY(-1 * pelota.getDireccionEnY());
			}else if(pelota.getDireccion().equals("abajoIzquierda")){
				System.out.println("choco en el area "+area+" en la direccion abajo a la izquierda");
				pelota.setDireccionEnX(-1 * pelota.getDireccionEnX());
			}
		}else{
			System.out.println("choco en el area "+area+" sale en direccion correcta");
			pelota.setDireccionEnX(-1 * pelota.getDireccionEnX());
			pelota.setDireccionEnY(-1 * pelota.getDireccionEnY());
		}
	}

	private void verificarContactoJugador2(){
		int areaDeContacto = this.jugador.getAreaDeContacto(this.pelota);
		this.pelota.setDireccion(areaDeContacto, "Jugador");
	}

	//Metodos de test!!!!!

	private void testPausarJuego(){
		boolean unContacto = false;
		int i=0;
		while (i<this.grilla.getCantidadFilas()&&! unContacto){
			int j=0;
			while (j<this.grilla.getCantidadColumnas()&&! unContacto){
				if(this.grilla.getBloque(i,j).getDureza()==1){
					Bloque bloque = this.grilla.getBloque(i,j);
					unContacto=verificarContacto2(bloque);
				}
				j++;
			}
			i++;
		}
	}

	public boolean verificarContacto2(Bloque b){
		boolean salida=false;
		int x=pelota.getPosSiguienteX();
		int y=pelota.getPosSiguienteY();
		int anchoPelota=pelota.getTamanio();
		int alto=pelota.getTamanio();

		Rect rec1=new Rect(x,y,x+anchoPelota,y+alto);
		int bX=(int)b.getPosX();
		int by=(int)b.getPosY();
		int bAncho=(int)(b.getPosX()+b.getAnchoBloque());
		int bAlto=(int)(b.getPosY()+b.getAltoBloque());
		Rect rec2=new Rect(bX, (int)b.getPosY(), (int)(b.getPosX()+b.getAnchoBloque()), (int)(b.getPosY()+b.getAltoBloque()));

		if(rec1.intersect(rec2)){

			controlarChoques();
			Paint colorChoque = new Paint();
			colorChoque.setColor(RED);
			b.setPincel(colorChoque);

			salida=true;
		}

		return salida;
	}

	public void controlarChoques(){
		gameThread.pause();//Para verificar contactos
		juegoEnPausa=true;
	}

	}
