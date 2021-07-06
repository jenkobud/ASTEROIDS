package Ships;

public abstract class Triangle {
	protected int Xcentro,Ycentro,alto;	
	protected Triangle(int xcentro, int ycentro, int alto) {
		super();
		Xcentro = xcentro;
		Ycentro = ycentro;
		this.alto = alto;
	}

	public int getXcentro() {
		return Xcentro;
	}
	public void setXcentro(int xcentro) {
		Xcentro = xcentro;
	}
	public int getYcentro() {
		return Ycentro;
	}
	public void setYcentro(int ycentro) {
		Ycentro = ycentro;
	}
	public int getAlto() {
		return alto;
	}
	public void setAlto(int alto) {
		this.alto = alto;
	}
}
