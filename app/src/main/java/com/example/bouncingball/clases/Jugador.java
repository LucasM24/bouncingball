package com.example.bouncingball.clases;
import android.graphics.Rect;

public class Jugador {

	private Rect [] areas;
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private int velocidad = 13;

	public Jugador (int x, int y, int ancho, int alto ){
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.inicializarAreas();
	}

	public Rect[] inicializarAreas(int x, int y, int ancho, int alto, int velocidad){
		this.areas = new Rect [5];

		Rect area1 = new Rect(x, y,x + velocidad - 1, y + this.alto);

		Rect area2 = new Rect(x + velocidad, y,(ancho / 4) - 1,y + alto);

		Rect area3 = new Rect(x + ancho / 4, y,(ancho / 2) - 1,y + alto);

		Rect area4 = new Rect(x + (ancho / 2) , y, (ancho / 4) - 1, y + alto);

		Rect area5 = new Rect (x + (ancho / 4), y, velocidad, y + alto);

		this.areas[0]=area1;
		this.areas[1]=area2;
		this.areas[2]=area3;
		this.areas[3]=area4;
		this.areas[4]=area5;

		return areas;
	}

	public Rect[] inicializarAreas(){
		this.areas = new Rect [5];

		int areaEsquina = (this.ancho / 4) / 2; // 18,75
		int areaIntermedia = this.ancho / 4; //37,5

		Rect area1 = new Rect(this.x, this.y, this.x + areaEsquina - 1, y + this.alto);

		Rect area2 = new Rect(this.x + areaEsquina, this.y,this.x + areaEsquina + areaIntermedia - 1, y + this.alto);

		Rect area3 = new Rect(this.x + areaEsquina + areaIntermedia, this.y,this.x + this.ancho - areaEsquina - areaIntermedia - 1, y + this.alto);
//
		Rect area4 = new Rect(this.x + this.ancho - areaEsquina - areaIntermedia , this.y, this.x + this.ancho - areaEsquina - 1, y + this.alto);

		Rect area5 = new Rect (this.x + this.ancho - areaEsquina, this.y, this.x + this.ancho, y + this.alto);

		this.areas[0]=area1;
		this.areas[1]=area2;
		this.areas[2]=area3;
		this.areas[3]=area4;
		this.areas[4]=area5;

		return areas;
	}

	public int getAreaDeContacto(Pelota pelota){
		int areaDeContacto = 0;
		int i = 0;
		boolean salir = false, choco = false;
		int cantidadDeAreas = this.areas.length;
		while(!salir && cantidadDeAreas > i){
			if(pelota.interseccion(this.areas[i])){
				choco = true;
				System.out.println("Hubo una intercepcion en el area" + i);
				if(i == 1){
					areaDeContacto = 1;
					salir = true;
				}else if(i == 3){
					areaDeContacto = 3;
					salir = true;
				}
				areaDeContacto = i;
			}
			i += 1;
		}

		if(!choco){
			areaDeContacto = -1;
		}

		return areaDeContacto;
	}

	public int getPosX(){
		return this.x;
	}

	public int getPosY(){
		return this.y;
	}

	public int getAncho(){
		return this.ancho;
	}

	public int getAlto(){
		return this.alto;
	}

	public void setPosX(int x){
		this.x = x;
	}

	public void setPosY(int y){
		this.y = y;
	}

	public Rect getRect(int pos){
		return this.areas[pos];
	}
}
