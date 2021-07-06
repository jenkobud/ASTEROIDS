package Things;

public class Mapa {//En realidad es nivel. 

	private int LvL,CantDeEnemigos, CantDeAsteroidesChicos,CantDeAsteroidesGrandes, CantDeItems;

	public Mapa(int lvL) {
		super();
		LvL = lvL;
		//Asignamos la cantidad de objetos que va a tener cada nivel
		if(LvL==1){
			CantDeEnemigos=5;
			CantDeAsteroidesChicos=5;
			CantDeAsteroidesGrandes=3;
			CantDeItems=11;//Esto es el doble de lo que vamos a mostrar debido a el metodo que utilizamos para mostrarlo (en el draw)
		}
		if(this.LvL==2){
			CantDeEnemigos=6;
			CantDeAsteroidesChicos=7;
			CantDeAsteroidesGrandes=5;
			CantDeItems=21;
		}
		if(this.LvL==3){
			CantDeEnemigos=6;
			CantDeAsteroidesChicos=9;
			CantDeAsteroidesGrandes=8;
			CantDeItems=21;
		}
		
	}

	public int getCantDeEnemigos() {
		return CantDeEnemigos;
	}

	public void setCantDeEnemigos(int cantDeEnemigos) {
		CantDeEnemigos = cantDeEnemigos;
	}

	public int getCantDeAsteroidesChicos() {
		return CantDeAsteroidesChicos;
	}

	public void setCantDeAsteroidesChicos(int cantDeAsteroidesChicos) {
		CantDeAsteroidesChicos = cantDeAsteroidesChicos;
	}

	public int getCantDeAsteroidesGrandes() {
		return CantDeAsteroidesGrandes;
	}

	public void setCantDeAsteroidesGrandes(int cantDeAsteroidesGrandes) {
		CantDeAsteroidesGrandes = cantDeAsteroidesGrandes;
	}

	public int getCantDeItems() {
		return CantDeItems;
	}

	public void setCantDeItems(int cantDeItems) {
		CantDeItems = cantDeItems;
	}

	public int getLvL() {
		return LvL;
	}

	public void setLvL(int lvL) {
		LvL = lvL;
	}

	

}
