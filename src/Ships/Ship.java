package Ships;
import java.util.Iterator;
import java.util.Vector;

import Game.Dibujable;
import Game.Disparador;
import Game.Movible;
import processing.core.PApplet;
import processing.core.PImage;

public class Ship extends Triangle implements Movible,Disparador,Dibujable {
	protected float X1,Y1,X2,Y2,X3,Y3;
	protected int life;
	protected double angle;
	protected Vector<Laser> balas;
	private PImage img;
	
	public Ship(int x2, int y2, int alto) {
		//Calculamos los puntos de nuestra nave
		super(x2, y2, alto);
		this.X1 = Xcentro-(alto/2);
		this.X3 = Xcentro+(alto/2);
		this.Y1 = Ycentro+(alto/2);
		this.Y3 = Ycentro+(alto/2);
		this.Y2 = Ycentro-(alto/2);
		this.X2 = Xcentro;
		this.angle = 0;
		this.life = 3;
		this.balas = new Vector<Laser>();//municiones de la nave
	}
	

	public void dibujar(PApplet pantalla)
	{
		pantalla.fill(0,255,0);//relleno
		pantalla.triangle(this.X1,this.Y1,this.X2,this.Y2,this.X3,this.Y3);//nave
		pantalla.color(1);
		Iterator<Laser> iterador = this.balas.iterator();
		while (iterador.hasNext())
		{
			Laser actual = iterador.next();
			actual.dibujar(pantalla);
			if (actual.getX()>pantalla.width)
			{
				iterador.remove();
			}
		}
	}
	
	public void disparar(double Xdisparo,double Ydisparo)//Se crea la bala y se calcula la trayectoria
	{
		/*
		 * La funcion 'Math.atan2(double, double)' busca el angulo de las coordenadas 'x' e 'y' en relacion a las coordenadas (0,0).
		 * El resultado del angulo es en radianes.
		 * A este angulo, se le busca el coseno y el seno, para la velocidad en X y la velocidad en Y respectivamente.
		 * Estas velocidades son multiplicadas por 3 para dar un poco mas de velocidad.
		 * Una vez que se calcularon los valores de velocidad, se los envian a la funcion 'Nave.disparar()'.
		 */
		Xdisparo=this.X2;
		Ydisparo=this.Y2;
		
		double angle = Math.atan2(Ydisparo - this.getYcentro(), Xdisparo - this.getXcentro());
		double velx = Math.cos(angle) * 6;
		double vely = Math.sin(angle) * 6;
		balas.add(new Laser(this,velx,vely)); 
	}
	public void movimiento(int opc, PApplet screen) {
		Boolean yInScreen = (Ycentro-(this.alto/2)>0) && (Ycentro+(this.alto/2)<screen.height);
		Boolean xInScreen = (Xcentro-(this.alto/2)>0) && (Xcentro+(this.alto/2)<screen.width);

		double velX, velY;
		switch(opc){
			case 1:
				velX = Math.cos(this.angle) * 5;
				velY = Math.sin(this.angle) * 5;
				if (xInScreen && yInScreen){
					Xcentro += velX;
					Ycentro += velY;
				}
				//Still some movement-bugs.
				if (Xcentro-(this.alto/2) == 0 && velX > 0) Xcentro += velX;// Left-side case.
				if (Xcentro+(this.alto/2) == screen.width && velX < 0) Xcentro += velX; //Right-side case.
				if (Ycentro-(this.alto/2) == 0 && velY > 0) Ycentro += velY;// Bottom-side case.
				if (Ycentro+(this.alto/2) == screen.height && velY < 0) Ycentro += velY; //Top-side case.
				break;
			case 2:
				//Adapt case 1 logic for reverse movement.
				if(xInScreen && yInScreen) {
					velX = Math.cos(this.angle) * 3;
					velY = Math.sin(this.angle) * 3;
					Xcentro -= velX;
					Ycentro -= velY;
				}
				break;
			case 3:
				this.angle+=Math.PI/30;
				break;
			case 4:
				this.angle-=Math.PI/30;
				break;
		}
	}
	
	public void seguirMouse(PApplet screen){//RotarTriangulo
		/*float x_mouse = screen.mouseX;//posici�n X del mouse	
		float y_mouse = screen.mouseY;//posici�n Y del mouse.*/
		float x_center = this.Xcentro;//centro X del triangulo
		float y_center = this.Ycentro;//centro Y del triangulo
		
		
		// Ahora voy a primero hacer que uno de los v�rtices, "me siga"
		//float alfa = (float) Math.atan2(y_mouse - this.getYcentro(), x_mouse - this.getXcentro());//Se calcula el angulo de la pendiente de la recta que forma el cursor del mouse respecto al centro del triangulo
		//if key==a
		/*if(screen.keyCode=='a'){
		}
		if(screen.keyCode=='d'){
			angle+=Math.PI/180;
		}*/
		this.X3 = (int) (x_center - Math.sin(angle-Math.PI*2/3-Math.PI/2) * this.alto /2);//Le sumamos 120� al angulo para averiguar X3 e Y3. 
		this.Y3 = (int) (y_center + Math.cos(angle-Math.PI*2/3-Math.PI/2) * this.alto /2);

		// El que apunta al mouse
		this.X2 = (int) (x_center - Math.sin(angle-Math.PI/2) * this.alto /2);//Utilizando la formula para averiguar la pendiente de una recta conociendo 2 puntos (el mouse y el centro del triangulo), averiguamos su inclinacion correspondiente en X. 
		this.Y2 = (int) (y_center + Math.cos(angle-Math.PI/2) * this.alto /2);//Utilizando la formula para averiguar la pendiente de una recta conociendo 2 puntos (el mouse y el centro del triangulo), averiguamos su inclinacion correspondiente en Y.
		
		this.X1 = (int) (x_center - Math.sin(angle+Math.PI*2/3-Math.PI/2) * this.alto /2); //Le restamos 120� al angulo para averiguar X1 e Y2.
		this.Y1 = (int) (y_center + Math.cos(angle+Math.PI*2/3-Math.PI/2) * this.alto /2);
	}


	public float getX1() {
		return X1;
	}
	public void setX1(float x1) {
		X1 = x1;
	}
	public float getY1() {
		return Y1;
	}
	public void setY1(float y1) {
		Y1 = y1;
	}
	public float getX3() {
		return X3;
	}
	public void setX3(float x3) {
		X3 = x3;
	}
	public float getY3() {
		return Y3;
	}
	public void setY3(float y3) {
		Y3 = y3;
	}


	public Vector<Laser> getBalas() {
		return balas;
	}


	public void setBalas(Vector<Laser> balas) {
		this.balas = balas;
	}


	public int getLife() {
		return life;
	}


	public void setLife(int life) {
		this.life = life;
	}


	public PImage getImg() {
		return img;
	}


	public void setImg(PImage img) {
		this.img = img;
	}


	public double getAngle() {
		return angle;
	}


	public void setAngle(double d) {
		this.angle = d;
	}
	public float getX2() {
		return X2;
	}


	public void setX2(float x2) {
		X2 = x2;
	}


	public float getY2() {
		return Y2;
	}


	public void setY2(float y2) {
		Y2 = y2;
	}


	
	


}
