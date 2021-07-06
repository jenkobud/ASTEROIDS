package Enemies;

import java.util.Vector;

import processing.core.PImage;
import Game.Dibujable;
import Game.Disparador;
import Game.Movible;
import Ships.Laser;

public abstract class Enemy extends Square implements Dibujable, Movible, Disparador {
	protected float Xspeed, Yspeed;//Coordenadas de velocidad
	protected Vector<Laser> balas = new Vector<Laser>();//municiones de los enemigos.
	protected PImage img;//Variable donde se guarda la ruta de una imagen

	
	public Enemy(float x, float y, int lado,float xspeed, float yspeed) {
		super(x, y, lado);
		this.Xspeed = xspeed;
		this.Yspeed = yspeed;
	}
	

	public float getXspeed() {
		return Xspeed;
	}
	public void setXspeed(float xspeed) {
		Xspeed = xspeed;
	}
	public float getYspeed() {
		return Yspeed;
	}
	public void setYspeed(float yspeed) {
		Yspeed = yspeed;
	}
	public Vector<Laser> getBalas() {
		return balas;
	}
	public void setBalas(Vector<Laser> balas) {
		this.balas = balas;
	}


	public PImage getImg() {
		return img;
	}


	public void setImg(PImage img) {
		this.img = img;
	}

	
}
