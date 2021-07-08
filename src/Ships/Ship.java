package Ships;
import java.util.Iterator;
import java.util.Vector;

import Game.Drawable;
import Game.Shooter;
import Game.Movable;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Ship extends Triangle implements Movable, Shooter, Drawable {
	protected float x1, y1, x2, y2, x3, y3;
	protected int life;
	protected double angle;
	protected Vector<Laser> lasers;
	private PImage img;
	
	public Ship(int x2, int y2, int height) {
		//Calculamos los puntos de nuestra nave
		super(x2, y2, height);
		this.x1 = xCenter -(height/2);
		this.x3 = xCenter +(height/2);
		this.y1 = yCenter +(height/2);
		this.y3 = yCenter +(height/2);
		this.y2 = yCenter -(height/2);
		this.x2 = xCenter;
		this.angle = 0;
		this.life = 3;
		this.lasers = new Vector<Laser>();//municiones de la nave
	}
	

	public void draw(PApplet screen)
	{
		//Triangle drawing.
		screen.fill(0,255,0);//relleno
		screen.triangle(this.x1,this.y1,this.x2,this.y2,this.x3,this.y3);//nave
		screen.color(1);

		//Codigo sacado de internet QUITAR
		//Rotating image drawing.
		screen.pushMatrix();
		screen.imageMode(PConstants.CENTER);
		//screen.translate(this.height/2, this.height/2, 0);
		// Este rotate es para render P3D
		//screen.rotate((float) this.angle, this.xCenter, this.yCenter, 0);
		//screen.rotate(screen.random(0,  6.28f));
		screen.rotate((float) this.angle);
		//screen.getMatrix().rotate((float)this.angle);
		screen.image(img, this.xCenter, this.yCenter, this.height/2, this.height);
		screen.popMatrix();
		Iterator<Laser> iterator = this.lasers.iterator();
		while (iterator.hasNext())
		{
			Laser actual = iterator.next();
			actual.draw(screen);
			if (actual.getX()>screen.width) iterator.remove();
		}
	}
	
	public void shoot(double xDisparo, double yDisparo)//Se crea la bala y se calcula la trayectoria
	{
		// disparar no requiere de parametros de coordenadas ya que dispara siempre hacia adelante x ahora. QUITAR
		/*
		 * La funcion 'Math.atan2(double, double)' busca el angulo de las coordenadas 'x' e 'y' en relacion a las coordenadas (0,0).
		 * El resultado del angulo es en radianes.
		 * A este angulo, se le busca el coseno y el seno, para la velocidad en X y la velocidad en Y respectivamente.
		 * Estas velocidades son multiplicadas por 3 para dar un poco mas de velocidad.
		 * Una vez que se calcularon los valores de velocidad, se los envian a la funcion 'Nave.disparar()'.
		 */

		double angle = Math.atan2(this.y2 - this.getYcenter(), this.x2 - this.getXcenter());
		double velX = Math.cos(angle) * 6;
		double velY = Math.sin(angle) * 6;
		lasers.add(new Laser(this, velX, velY));
	}
	public void movement(int option, PApplet screen) {
		Boolean yInScreen = (yCenter -(this.height /2)>0) && (yCenter+(this.height /2) < screen.height);
		Boolean xInScreen = (xCenter -(this.height /2)>0) && (xCenter+(this.height /2) < screen.width);

		double velX, velY;
		switch(option){
			case 0:
				velX = Math.cos(this.angle) * 5;
				velY = Math.sin(this.angle) * 5;
				if (xInScreen && yInScreen){
					xCenter += velX;
					yCenter += velY;
				}
				//Movement-bugs because of strict logic condition.
				if (xCenter -(this.height /2) <= 0 && velX > 0) xCenter += velX;// Left-side case.
				if (xCenter +(this.height /2) >= screen.width && velX < 0) xCenter += velX; //Right-side case.
				if (yCenter -(this.height /2) <= 0 && velY > 0) yCenter += velY;// Bottom-side case.
				if (yCenter +(this.height /2) >= screen.height && velY < 0) yCenter += velY; //Top-side case.
				break;
			case 1:
				velX = -(Math.cos(this.angle) * 3);
				velY = -(Math.sin(this.angle) * 3);
				if(xInScreen && yInScreen) {
					xCenter += velX;
					yCenter += velY;
				}
				if (xCenter -(this.height /2) <= 0 && velX > 0) xCenter += velX;// Left-side case.
				if (xCenter +(this.height /2) >= screen.width && velX < 0) xCenter += velX; //Right-side case.
				if (yCenter -(this.height /2) <= 0 && velY > 0) yCenter += velY;// Bottom-side case.
				if (yCenter +(this.height /2) >= screen.height && velY < 0) yCenter += velY; //Top-side case.
				break;
			case 2:
				this.angle += Math.PI/30;
				break;
			case 3:
				this.angle -= Math.PI/30;
				break;
		}
	}
	public void followMouse(PApplet screen){//RotarTriangulo según posición del mouse.
		/*float x_mouse = screen.mouseX;//posici�n X del mouse	
		float y_mouse = screen.mouseY;//posici�n Y del mouse.*/
		float x_center = this.xCenter;//centro X del triangulo
		float y_center = this.yCenter;//centro Y del triangulo
		
		
		// Ahora voy a primero hacer que uno de los v�rtices, "me siga"
		//float alfa = (float) Math.atan2(y_mouse - this.getYcentro(), x_mouse - this.getXcentro());//Se calcula el angulo de la pendiente de la recta que forma el cursor del mouse respecto al centro del triangulo
		//if key==a
		/*if(screen.keyCode=='a'){
		}
		if(screen.keyCode=='d'){
			angle+=Math.PI/180;
		}*/
		this.x3 = (int) (x_center - Math.sin(angle-Math.PI*2/3-Math.PI/2) * this.height /2);//Le sumamos 120� al angulo para averiguar X3 e Y3.
		this.y3 = (int) (y_center + Math.cos(angle-Math.PI*2/3-Math.PI/2) * this.height /2);

		// El que apunta al mouse
		this.x2 = (int) (x_center - Math.sin(angle-Math.PI/2) * this.height /2);//Utilizando la formula para averiguar la pendiente de una recta conociendo 2 puntos (el mouse y el centro del triangulo), averiguamos su inclinacion correspondiente en X.
		this.y2 = (int) (y_center + Math.cos(angle-Math.PI/2) * this.height /2);//Utilizando la formula para averiguar la pendiente de una recta conociendo 2 puntos (el mouse y el centro del triangulo), averiguamos su inclinacion correspondiente en Y.
		
		this.x1 = (int) (x_center - Math.sin(angle+Math.PI*2/3-Math.PI/2) * this.height /2); //Le restamos 120� al angulo para averiguar X1 e Y2.
		this.y1 = (int) (y_center + Math.cos(angle+Math.PI*2/3-Math.PI/2) * this.height /2);
	}


	public float getX1() { return x1; }
	public void setX1(float x1) { this.x1 = x1; }
	public float getY1() { return y1; }
	public void setY1(float y1) { this.y1 = y1; }
	public float getX3() { return x3; }
	public void setX3(float x3) { this.x3 = x3; }
	public float getY3() { return y3; }
	public void setY3(float y3) { this.y3 = y3; }
	public Vector<Laser> getLasers() { return lasers; }
	public void setLasers(Vector<Laser> lasers) { this.lasers = lasers; }
	public int getLife() { return life; }
	public void setLife(int life) { this.life = life; }
	public PImage getImg() { return img; }
	public void setImg(PImage img) { this.img = img; }
	public double getAngle() { return angle; }
	public void setAngle(double angle) { this.angle = angle; }
	public float getX2() { return x2; }
	public void setX2(float x2) { this.x2 = x2; }
	public float getY2() { return y2; }
	public void setY2(float y2) { this.y2 = y2; }

}
