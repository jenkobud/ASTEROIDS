package Asteroids;

import Game.Drawable;
import Game.Movable;
import Game.Platform;
import processing.core.PApplet;

public class BigAsteroid extends Asteroid implements Drawable, Movable {

	public BigAsteroid(float x, float y, int radio, float xspeed, float yspeed) {
		super(x, y, radio, xspeed, yspeed);
	}

	
	public void draw(PApplet pantalla) {
		pantalla.image(img, this.getX(), this.getY(),this.getRadio(),this.getRadio());

	}
	
	public void destruir(Platform plataforma,PApplet screen){//Cuando este choca contra algo crea 2 m�s peque�os como si se desfragmentase
			SmallAsteroid ast1 = new SmallAsteroid(this.X, this.Y, (this.Radio/2), this.Xspeed*(-2), this.Yspeed*(-2));//Creamos los asteroides chicos
			SmallAsteroid ast2 = new SmallAsteroid(this.X, this.Y, (this.Radio/2), this.Xspeed*2, this.Yspeed*2);
			ast1.setImg(screen.loadImage("ast.png"));
			ast2.setImg(screen.loadImage("ast.png"));
			plataforma.getSmallAsteroids().add(ast1);//los agregamos al vector de asteroides chicos en plataforma
			plataforma.getSmallAsteroids().add(ast2);
	}


	public void movement(int opc, PApplet screen) {//OPC no se utiliza pero no puedo omitirlo debido a que lo utilizo para la nave.
		this.X += this.Xspeed;//Movemos el asteroide segun la velocidad otorgada
		this.Y+=this.Yspeed;
		if(this.X+this.Radio>=screen.width){//Si este choca contra el costado de la pantalla rebota con la misma direccion pero invirtiendo X
			this.Xspeed -= (this.Xspeed*2);
		}
		if(this.X<=1){
			this.Xspeed += -(this.Xspeed*2);
		}
		if(this.Y+this.Radio>=screen.height){//Si este choca contra el piso o arriba de la pantalla rebota con la misma direccion pero invirtiendo Y
			this.Yspeed -= (this.Yspeed*2);
		}
		if(this.Y<=1){
			this.Yspeed += -(this.Yspeed*2);
		}
		
	}
	

}

