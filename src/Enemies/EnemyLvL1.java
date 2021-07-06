package Enemies;

import java.util.Iterator;

import processing.core.PApplet;
import Game.Dibujable;
import Game.Movible;
import Ships.Laser;

public class EnemyLvL1 extends Enemy implements Dibujable, Movible {
	
	private int timer = 50;
	private int limBalas;
	
	public EnemyLvL1(float x, float y) {
		super(x, y, 30, 2, 0);
		limBalas = 9;
	}

	public void movimiento(int opc, PApplet screen) {
			this.setX(this.getX()+Xspeed);//se mueve donde le indique xspeed
			if(this.getX()+this.lado+10>=screen.width){//si chocan contra la pared invierte su direcci�n
				this.Xspeed = -this.Xspeed;
			}
			if(this.getX()<=0){
				this.Xspeed = (float) Math.sqrt(Math.pow(this.Xspeed, 2));
			}
	}
	public void disparar(double Xdisparo,double Ydisparo)
	{
		if(this.limBalas >0){
			if(timer==0){//Esto actua como un contador para que las balas tengan un tiempo entrre disparo y disparo
			
			balas.add(new Laser(Xdisparo,Ydisparo,this));//CAMBIAR
			this.limBalas--;
			timer = 50;
		}
			if(this.limBalas >0)
			timer--;
	}
		 
	}

	public void dibujar(PApplet pantalla) {
		pantalla.image(img, this.getX(), this.getY(),this.getLado()+10,this.getLado());//Enemigo, creamos una imagen con la direccion de nuestra img original
		Iterator<Laser> iterador = this.balas.iterator();
		while (iterador.hasNext())
		{
			Laser actual = iterador.next();
			actual.dibujar(pantalla);
			if (actual.getX()>pantalla.width || actual.getY()>pantalla.height)
			{
				iterador.remove();
				this.limBalas++;
			}
		}
		
	}
	

}
