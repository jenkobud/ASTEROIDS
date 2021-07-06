package Ships;
import Enemies.Enemy;
import Game.Dibujable;
import processing.core.*;

public class Laser implements Dibujable {
	private boolean flag;//La utilizamos para saber si las balas son del enemigo o de la nave
	protected float X,Y;//Coordenadas
	protected double Xdestiny, Ydestiny;//Direccion del laser apuntado por Mouse
	protected PImage img;
	
	public Laser(Ship disparador,double xdestiny, double ydestiny) {
		this.X = disparador.getX2();//origen de donde va a partir la bala
		this.Y = disparador.getY2();//origen de donde va a partir la bala
		this.Xdestiny = xdestiny;//Destino de la bala
		this.Ydestiny = ydestiny;//Destino de la bala
		this.flag=true;
	}
	public Laser(double xdestiny, double ydestiny, Enemy disparador){
		this.X = disparador.getX()+(disparador.getLado()+10)/2;//origen de donde va a partir la bala
		this.Y = disparador.getY()+disparador.getLado()/2;//origen de donde va a partir la bala
		this.Xdestiny = xdestiny;//Destino de la bala
		this.Ydestiny = ydestiny;//Destino de la bala
		this.flag=false;
	}
	
	public void dibujar(PApplet pantalla)
	{
		if(this.flag==true){//si las balas son de la nave
			pantalla.fill(0,255,0);
		}else{
			pantalla.fill(255,0,0);
			pantalla.rect(X, Y, 5, 5);
		}
		pantalla.rect(X, Y, 5, 5,100);
		X+=Xdestiny;//se mueve hacia donde le indiquemos
		Y+=Ydestiny;
	}

	public float getX() {
		return X;
	}
	public void setX(float x) {
		X = x;
	}
	public float getY() {
		return Y;
	}
	public void setY(float y) {
		Y = y;
	}
	public double getXdestiny() {
		return Xdestiny;
	}
	public void setXdestiny(float xdestiny) {
		Xdestiny = xdestiny;
	}
	public double getYdestiny() {
		return Ydestiny;
	}
	public void setYdestiny(float ydestiny) {
		Ydestiny = ydestiny;
	}

}
