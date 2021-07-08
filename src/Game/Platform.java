package Game;

import processing.core.*;

import java.util.Vector;
import Asteroids.*;
import Enemies.*;
import Ships.Laser;
import Ships.Ship;
import Things.*;

public class Platform {
	private String name;
	private int puntaje;
	private Ship ship = new Ship(512, 272, 50);
	private Vector<SmallAsteroid> smallAsteroids;
	private Vector<BigAsteroid> bigAsteroids;
	private Vector<Enemy> enemies;
	private Vector<Item> items;
	private Mapa level;
	private int killEnemy, killAsteroid, lifes;
	private PImage lifeIcon;
	
	protected Platform(String name, int Lvl, PApplet screen) {//Construtor de plataforma.
		super();
		this.name = name;
		level = new Mapa(Lvl);
		killEnemy = 2;
		killAsteroid = 1;
		smallAsteroids = new Vector<SmallAsteroid>();
		bigAsteroids = new Vector<BigAsteroid>();
		enemies = new Vector<Enemy>();
		items = new Vector<Item>();
		ship.setImg(screen.loadImage("ship.png"));
		lifeIcon = screen.loadImage("ship.png");

		//creamos todos los asteroides chicos seg�n el nivel que se encuentre
		for(int i=0; i < level.getCantDeAsteroidesChicos(); i++){
			float xspeed = screen.random(-4,4);
			float yspeed = screen.random(-4,4);
			int radio = (int)screen.random(20,35);
			float x = 20;
			float y = screen.random(36,screen.height-36);
			smallAsteroids.add(new SmallAsteroid(x, y, radio, xspeed, yspeed));
			smallAsteroids.elementAt(i).setImg(screen.loadImage("ast.png"));
		}

		//creamos todos los asteroides grandes seg�n el nivel que se encuentre
		for(int i=0; i < level.getCantDeAsteroidesGrandes();i++){
			float xspeed = screen.random(-4,4);
			float yspeed = screen.random(-4,4);
			int radio = (int)screen.random(36,46);
			float X = screen.width-46;
			float Y = screen.random(47,screen.height-47);
			bigAsteroids.add(new BigAsteroid(X, Y, radio, xspeed, yspeed));
			bigAsteroids.elementAt(i).setImg(screen.loadImage("ast2.png"));
		}
		/* Quito naves enemigas para facilidad de testeo. QUITAR */
		for(int i=0; i < level.getCantDeEnemigos();i++){//creamos todos los enemigos seg�n el nivel que se encuentre
			int typeOfEnemy = (int) screen.random(1,3);
			float x = 1;
			float y = screen.random(1,screen.height-100);
			if(typeOfEnemy == 1){
				enemies.add(new EnemyLvL1(x, y));
				enemies.elementAt(i).setImg(screen.loadImage("Lvl1.png"));
			}
			if(typeOfEnemy == 2){
				enemies.add(new EnemyLvL2(x, y));
				enemies.elementAt(i).setImg(screen.loadImage("Lvl2.png"));
			}
		}
		for(int i=0;i < level.getCantDeItems(); i++){
			int typeOfItem = (int) screen.random(1,3);
			float x = screen.random(0,screen.width-20);
			float y = screen.random(0,screen.height-20);
			if(typeOfItem==1)items.add(new Item(x, y, true));
			if(typeOfItem==2)items.add(new Item(x, y, false));
			items.elementAt(i).setImg(screen.loadImage("double.png"));
			items.elementAt(i).setImg2(screen.loadImage("extralife.png"));
		}
	} 
	
	public boolean winLose(PApplet screen){//Si pierde o gana
		//si la vida de la nave llega a 0
		if(ship.getLife() <= 0){
			screen.fill(255,0,0);
			screen.text("YOU LOST", (screen.width/2),(screen.height/2) );
			screen.text(puntaje, (screen.width/2), (screen.height/2)+10);
			screen.exit();
		}else if(smallAsteroids.isEmpty() && bigAsteroids.isEmpty() && enemies.isEmpty()){//Si logramos eliminar a todos los enemigos & asteroides.
			screen.fill(0,255,0);
			screen.text("YOU WON", (screen.width/2),(screen.height/2) );
			screen.text(puntaje, (screen.width/2), (screen.height/2)+10);
			//Si quedan m�s niveles por delante
			if(level.getLvL() < 3) return true; //El 3 debería volverlo una constante QUITAR.
			//En el caso de que no hayan m�s niveles
			screen.fill(0,255,0);
			screen.text("THE END", (screen.width/3),(screen.height/2) );
			screen.text(puntaje, (screen.width/2), (screen.height/2)+10);
		}
		return false;
	}

	public void collisions(PApplet screen){//Verifica los choques de todo
		//INTENTE UTILIZAR ITERATOR PERO NO ME FUNCIONABA CORRECTAMENTE.
		//Utilizamos un auxiliar para eliminar el objeto correspondiente del vector que le corresponda.
		BigAsteroid bigAsteroidAux = null;
		Laser laserAux =null;
		Item itemAux = null;
		lifes = ship.getLife();
		screen.fill(255,255,255);
		String Score = "SCORE: "+ puntaje;
		String life = "LIFES: ";
		screen.textSize(12);
		screen.text(Score, 10, 10);
		screen.text(life, 15, 30);
		float xl = 50, yl = 20;
		for(int i = 0; i< lifes; i++){
			if(xl+10>screen.width){
				xl=50;
				yl+=16;
			}
			screen.image(lifeIcon, xl, yl,10,15);
			xl+=12;
		}
		for(BigAsteroid actualBigAsteroid : bigAsteroids){//Comprabacion de asteroides Grandes
			if(verificarColision(actualBigAsteroid, ship.getXcenter()- ship.getHeight()/2, ship.getYcenter()-(ship.getHeight()/2)) || verificarColision(actualBigAsteroid, ship.getXcenter()+ ship.getHeight()/2, ship.getYcenter()+ ship.getHeight()/2)|| verificarColision(actualBigAsteroid, ship.getXcenter()+ ship.getHeight()/2, ship.getYcenter()- ship.getHeight()/2)|| verificarColision(actualBigAsteroid, ship.getXcenter()- ship.getHeight()/2, ship.getYcenter()+ ship.getHeight()/2)){
				screen.fill(255,0,0);
				ship.setLife(ship.getLife()-1);
				bigAsteroidAux = actualBigAsteroid;//En el caso de que nuestra nave tenga mas de uno de vida eliminamos el objeto que choco para evitar que este siga colisionandola y quitandole m�s vida.
				bigAsteroidAux.destruir(this,screen);
				puntaje =0;
				}
			for(Laser Bact : ship.getLasers()){//Si la nave le acierta a un ASTEROIDE GRANDE.
				if(verificarColision(actualBigAsteroid, Bact.getX(), Bact.getY()) || (verificarColision(actualBigAsteroid, Bact.getX()+5, Bact.getY()+5))){
					bigAsteroidAux = actualBigAsteroid;
					bigAsteroidAux.destruir(this,screen);
					laserAux = Bact;
					puntaje += killAsteroid;
				}
			}
		}
		ship.getLasers().remove(laserAux);
		bigAsteroids.remove(bigAsteroidAux);
		SmallAsteroid Aux = null;
		for(SmallAsteroid Act : smallAsteroids){//Comprabacion de asterroides chicos
			if(verificarColision(Act, ship.getXcenter()- ship.getHeight()/2, ship.getYcenter()-(ship.getHeight()/2)) || verificarColision(Act, ship.getXcenter()+ ship.getHeight()/2, ship.getYcenter()+ ship.getHeight()/2)|| verificarColision(Act, ship.getXcenter()+ ship.getHeight()/2, ship.getYcenter()- ship.getHeight()/2)|| verificarColision(Act, ship.getXcenter()- ship.getHeight()/2, ship.getYcenter()+ ship.getHeight()/2)){
				ship.setLife(ship.getLife()-1);
				Aux=Act;
				puntaje =0;
			}
			for(Laser Bact : ship.getLasers()){//Si la nave le acierta a un ASTEROIDE CHICO.
				if(verificarColision(Act, Bact.getX(), Bact.getY()) || (verificarColision(Act, Bact.getX()+5, Bact.getY()+5))){
					Aux = Act;
					laserAux = Bact;
					puntaje += killAsteroid;
				}
			}
		}
		ship.getLasers().remove(laserAux);
		smallAsteroids.remove(Aux);
		Laser EnLaux = null;
		Enemy Eaux =null;
		for(Enemy Act : enemies){//Colision enemigos
			if(verificarColision(Act, ship.getXcenter()- ship.getHeight()/2, ship.getYcenter()-(ship.getHeight()/2)) || verificarColision(Act, ship.getXcenter()+ ship.getHeight()/2, ship.getYcenter()+ ship.getHeight()/2)|| verificarColision(Act, ship.getXcenter()+ ship.getHeight()/2, ship.getYcenter()- ship.getHeight()/2)|| verificarColision(Act, ship.getXcenter()- ship.getHeight()/2, ship.getYcenter()+ ship.getHeight()/2)){
				ship.setLife(ship.getLife()-1);
				Eaux=Act;
				puntaje =0;
			}
			for(Laser Bact : ship.getLasers()){//Si la nave le acierta a un enemigo
				if(verificarColision(Act, Bact.getX(), Bact.getY()) || (verificarColision(Act, Bact.getX()+5, Bact.getY()+5))){
					Eaux = Act;
					laserAux = Bact;
					puntaje += killEnemy;
				}
			}
			for(Laser Bact : Act.getBalas()){//Si las balas de enemigo da a nave
				if(verificarColision(ship, Bact.getX(), Bact.getY()) || (verificarColision(ship, Bact.getX()+5, Bact.getY()+5))){
					ship.setLife(ship.getLife()-1);
					EnLaux=Bact;
					puntaje =0;
				}
			}
			Act.getBalas().remove(EnLaux);
		}
		ship.getLasers().remove(laserAux);
		enemies.remove(Eaux);
		for(Item Iact : items){//Comprobacion de la nave si toca un item
			if(verificarColision(ship, Iact.getX(), Iact.getY()) || (verificarColision(ship, Iact.getX()+Iact.getLado(), Iact.getY()+Iact.getLado()))){
				itemAux = Iact;
				if(itemAux.isAccion()){
					killEnemy = killEnemy *Iact.getBonus();
					killAsteroid = killAsteroid *Iact.getBonus();
				}else if(!itemAux.isAccion()){
					ship.setLife(ship.getLife()+1);
				}
			}
		}
		items.remove(itemAux);
		winLose(screen);
	}
	public void mostradorDeItems(int cantAMostrar, PApplet screen){// muestra los items que ya aparecieron
		for(int i=0;i<items.size() && i< cantAMostrar;i++){
			items.elementAt(i).draw(screen);
		}
	}

	private boolean verificarColision(Asteroid asteroid, float X, float Y){//Verifica choque de asteroides contra nave
		if((asteroid.getX()-asteroid.getRadio()<=X && asteroid.getX()+asteroid.getRadio()>=X)||(asteroid.getX()+asteroid.getRadio()<=X && asteroid.getX()>=X)){
			if((asteroid.getY()<=Y && asteroid.getY()+asteroid.getRadio()/2>=Y)||(asteroid.getY()+asteroid.getRadio()/2<=Y && asteroid.getY()>=Y)){
				return true;
			}
		}
		return false;
	}
	private boolean verificarColision(Enemy enemy, float X, float Y){//Verifica choque de enemigo contra nave.
		if(enemy.getX()<=X && enemy.getX()+enemy.getLado()+10>=X||enemy.getX()>=X&& enemy.getX()-enemy.getLado()<=X){
			if(enemy.getY()<=Y && enemy.getY()+enemy.getLado()>=Y||enemy.getY()>=Y && enemy.getY()+enemy.getLado()<=Y){
				return true;
			}
		}
		return false;
	}
	private boolean verificarColision(Ship ship, float X, float Y){//Verifica choque de enemigo contra nave y de item contra nave.
		if(X<=ship.getXcenter()+ this.ship.getHeight()/2 && X>=ship.getXcenter()- this.ship.getHeight()/2){
			if(Y<=ship.getYcenter()+(this.ship.getHeight()/2) && Y>=ship.getYcenter()-(this.ship.getHeight()/2)){
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<SmallAsteroid> getSmallAsteroids() {
		return smallAsteroids;
	}

	public void setSmallAsteroids(Vector<SmallAsteroid> smallAsteroids) {
		this.smallAsteroids = smallAsteroids;
	}

	public Vector<BigAsteroid> getBigAsteroids() {
		return bigAsteroids;
	}

	public void setBigAsteroids(Vector<BigAsteroid> bigAsteroids) {
		this.bigAsteroids = bigAsteroids;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Vector<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(Vector<Enemy> enemies) {
		this.enemies = enemies;
	}

	public Mapa getLevel() {
		return level;
	}

	public void setLevel(Mapa level) {
		this.level = level;
	}

	public Vector<Item> getItems() {
		return items;
	}

	public void setItems(Vector<Item> items) {
		this.items = items;
	}


	public int getPuntaje() {
		return puntaje;
	}


	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public int getKillEnemy() {
		return killEnemy;
	}

	public void setKillEnemy(int killEnemy) {
		this.killEnemy = killEnemy;
	}

	public int getKillAsteroid() {
		return killAsteroid;
	}

	public void setKillAsteroid(int killAsteroid) {
		this.killAsteroid = killAsteroid;
	}

}
