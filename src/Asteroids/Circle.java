package Asteroids;

public abstract class Circle {
	protected float X,Y;
	protected int Radio;

	public Circle(float x, float y, int radio) {
		super();
		this.X = x;
		this.Y = y;
		this.Radio = radio;
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
	public int getRadio() {
		return Radio;
	}
	public void setRadio(int radio) {
		Radio = radio;
	}

}
