package Ships;

public abstract class Triangle {
	protected int xCenter, yCenter, height;
	protected Triangle(int xCenter, int yCenter, int height) {
		super();
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.height = height;
	}

	public int getXcenter() {
		return xCenter;
	}
	public void setXcenter(int xcenter) {
		this.xCenter = xcenter;
	}
	public int getYcenter() {
		return yCenter;
	}
	public void setYcenter(int ycenter) {
		this.yCenter = ycenter;
	}
	public int getHeight() { return height; }
	public void setHeight(int height) {
		this.height = height;
	}
}
