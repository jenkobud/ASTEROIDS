package Enemies;

import java.util.Iterator;

import processing.core.PApplet;
import Game.Drawable;
import Game.Shooter;
import Game.Movable;
import Ships.Laser;

public class EnemyLvL2 extends Enemy implements Drawable, Movable, Shooter {
	private int limBalas;
	private int timer = 50;
	
	public EnemyLvL2(float x, float y) {
		super(x, y, 40, 3, 3);
		limBalas=32;
	}

	public void shoot(double Xdisparo, double Ydisparo)
	{
		if(this.limBalas >0){
			if(timer==0){
			for(int i=0; i<6; i++){//Esto actua como un disparador que suelta 6 balas en 6 direcciones diferentes. 
				if(i==1){
					Xdisparo=3; Ydisparo=0;
					balas.add(new Laser(Xdisparo,Ydisparo,this));
					this.limBalas--;
					}
				if(i==2){
					Xdisparo=3; Ydisparo= (1.5);
					balas.add(new Laser(Xdisparo,Ydisparo,this));
					this.limBalas--;
					}
				if(i==3){
					Xdisparo=0; Ydisparo= 3;
					balas.add(new Laser(Xdisparo,Ydisparo,this));
					this.limBalas--;
					}
				if(i==4){
					Xdisparo=-(1.5); Ydisparo= 3;
					balas.add(new Laser(Xdisparo,Ydisparo,this));
					this.limBalas--;
					}
				if(i==5){
					Xdisparo=-3; Ydisparo= 0;
					balas.add(new Laser(Xdisparo,Ydisparo,this));
					this.limBalas--;
					}
				if(i==6){
					Xdisparo=-3; Ydisparo= -(1.5);
					balas.add(new Laser(Xdisparo,Ydisparo,this));
					this.limBalas--;
					}
				if(i==7){
					Xdisparo=0; Ydisparo= -3;
					balas.add(new Laser(Xdisparo,Ydisparo,this));
					this.limBalas--;
					}
				if(i==8){
					Xdisparo=(1.5); Ydisparo= 3;
					i=0;
					balas.add(new Laser(Xdisparo,Ydisparo,this));
					this.limBalas--;
				}
				}
			timer = 50;
				}
			}
			if(this.limBalas >0)
				timer--;

	}

	public void movement(int opc, PApplet screen) {
		this.setX(this.getX()+Xspeed);//se mueve en base a Xspeed y Yspeed
		this.setY(this.getY()+Yspeed);
		if(this.getX()+this.lado+10>=screen.width){//Si choca contra la pared invierte su sentido
			this.Xspeed = -this.Xspeed;
		}
		if(this.getX()<=0){
			this.Xspeed = (float) Math.sqrt(Math.pow(this.Xspeed, 2));
		}
		if(this.getY()+this.getLado()>=screen.height){//Si choca contra el techo/piso invierte su sentido
			this.Yspeed = -this.Yspeed;
		}
		if(this.getY()<=0){
			this.Yspeed = (float) Math.sqrt(Math.pow(this.Yspeed, 2));
		}
	}

	public void draw(PApplet pantalla) {
		pantalla.image(img, this.getX(), this.getY(),this.getLado()+10,this.getLado());//Enemigo, creamos una imagen con la direccion de nuestra img original
		Iterator<Laser> iterador = this.balas.iterator();
		while (iterador.hasNext())
		{
			Laser actual = iterador.next();
			actual.draw(pantalla);
			if (actual.getX()>pantalla.width || actual.getY()>pantalla.height)
			{
				iterador.remove();
				this.limBalas++;
			}
		}
	}


}
