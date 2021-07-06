package Asteroids;
import processing.core.PImage;
import Game.Dibujable;


public abstract class Asteroid extends Circle implements Dibujable {
	protected float Xspeed,Yspeed;//Velocidad
	protected PImage img;
	
	public Asteroid(float x, float y, int radio, float xspeed, float yspeed) {
		super(x, y, radio);
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


	public PImage getImg() {
		return img;
	}


	public void setImg(PImage img) {
		this.img = img;
	}
	

}
