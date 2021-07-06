package Things;

import processing.core.PApplet;
import processing.core.PImage;
import Enemies.Square;
import Game.Dibujable;

public class Item extends Square  implements Dibujable{
	protected int Bonus;//cantidad de bonificacion del item
	protected boolean Accion;
	private PImage img;
	//Si Accion es TRUE este item duplicara el puntaje del jugador
	//Si es FALSE le sumara una vida.
	private PImage img2;
	

	public Item(float x, float y,boolean accion) {
		super(x, y, 20);
		Bonus = 2;
		Accion = accion;
	}
	
	public void dibujar(PApplet pantalla) {
		if(this.Accion==true){
			pantalla.image(img,this.x,this.y,this.lado,this.lado);
		}else{
			pantalla.image(img2,this.x,this.y,this.lado,this.lado);
		}
		
	}
	
	

	public int getBonus() {
		return Bonus;
	}

	public void setBonus(int bonus) {
		Bonus = bonus;
	}

	public boolean isAccion() {
		return Accion;
	}

	public void setAccion(boolean accion) {
		Accion = accion;
	}

	public PImage getImg() {
		return img;
	}

	public void setImg(PImage img) {
		this.img = img;
	}

	public PImage getImg2() {
		return img2;
	}

	public void setImg2(PImage img2) {
		this.img2 = img2;
	}
	
}
