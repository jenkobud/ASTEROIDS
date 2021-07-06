package Enemies;

public abstract  class Square {

	protected float x,y,lado;
	
	public Square(float x, float y, int lado) {
		super();
		this.x = x;
		this.y = y;
		this.lado = lado;
	}

	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getLado() {
		return lado;
	}
	public void setLado(float lado) {
		this.lado = lado;
	}

}

