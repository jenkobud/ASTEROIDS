package Asteroids;


import processing.core.PApplet;
import Game.Dibujable;
import Game.Movible;

public class SmallAsteroid extends Asteroid implements Dibujable,Movible {

	


	public SmallAsteroid(float x, float y, int radio, float xspeed, float yspeed) {
		super(x, y, radio, xspeed, yspeed);//la vida esta preasignada
	}

	public void dibujar(PApplet pantalla) {
		pantalla.image(img, this.getX(), this.getY(),this.getRadio(),this.getRadio());
	}

	public void Movimiento(int opc, PApplet screen) {//OPC no se utiliza pero no puedo omitirlo debido a que lo utilizo para la nave.
		this.X += this.Xspeed;//Movemos el asteroide segun la velocidad otorgada
		this.Y+=this.Yspeed;
		if(this.X+this.Radio>=screen.width){//Si este choca contra el costado de la pantalla rebota con la misma direccion pero invirtiendo X
			this.Xspeed -= (this.Xspeed*2);
		}
		if(this.X<=0){
			this.Xspeed += -(this.Xspeed*2);
		}
		if(this.Y+this.Radio>=screen.height){//Si este choca contra el piso o arriba de la pantalla rebota con la misma direccion pero invirtiendo Y
			this.Yspeed -= (this.Yspeed*2);
		}
		if(this.Y<=0){
			this.Yspeed += -(this.Yspeed*2);
		}
		
	}
	

}
