package Game;

import processing.core.*;

import java.util.Vector;
import Asteroids.*;
import Enemies.*;
import Ships.Laser;
import Ships.Ship;
import Things.*;

public class Platform {
	private String Name;
	private int Puntaje;
	private Ship Nave = new Ship(512, 272, 50);
	private Vector<SmallAsteroid> asteroidesChicos;
	private Vector<BigAsteroid> asteroidesGrandes;
	private Vector<Enemy> enemigos;
	private Vector<Item> items;
	private Mapa level;
	private int KillEnemy,KillAsteroid,Lifes;
	private PImage lifeI;
	
	protected Platform(String name,int Lvl,PApplet screen) {//Construtor de plataforma.
		super();
		Name = name;
		level = new Mapa(Lvl);
		KillEnemy=2;
		KillAsteroid=1;
		asteroidesChicos = new Vector<SmallAsteroid>();
		asteroidesGrandes = new Vector<BigAsteroid>();
		enemigos = new Vector<Enemy>();
		items = new Vector<Item>();
		Nave.setImg(screen.loadImage("ship.png"));
		lifeI = screen.loadImage("ship.png");
		for(int i=0; i<level.getCantDeAsteroidesChicos();i++){//creamos todos los asteroides chicos según el nivel que se encuentre
			float xspeed = screen.random(-4,4);
			float yspeed = screen.random(-4,4);
			int radio = (int)screen.random(20,35);
			float x = 20;
			float y = screen.random(36,screen.height-36);
			asteroidesChicos.add(new SmallAsteroid(x, y, radio, xspeed, yspeed));
			asteroidesChicos.elementAt(i).setImg(screen.loadImage("ast.png"));
		}
		
		for(int i=0; i<level.getCantDeAsteroidesGrandes();i++){//creamos todos los asteroides grandes según el nivel que se encuentre
			float xspeed = screen.random(-4,4);
			float yspeed = screen.random(-4,4);
			int radio = (int)screen.random(36,46);
			float X = screen.width-46;
			float Y = screen.random(47,screen.height-47);
			asteroidesGrandes.add(new BigAsteroid(X, Y, radio, xspeed, yspeed));
			asteroidesGrandes.elementAt(i).setImg(screen.loadImage("ast2.png"));
		}
		for(int i=0; i<level.getCantDeEnemigos();i++){//creamos todos los enemigos según el nivel que se encuentre
			int type = (int) screen.random(1,3);
			float x = 1;
			float y = screen.random(1,screen.height-100);
			if(type==1){
				enemigos.add(new EnemyLvL1(x, y));
				enemigos.elementAt(i).setImg(screen.loadImage("Lvl1.png"));
			}else if(type==2){
				enemigos.add(new EnemyLvL2(x, y));
				enemigos.elementAt(i).setImg(screen.loadImage("Lvl2.png"));
			}
		}
		for(int i=0;i<level.getCantDeItems();i++){
			int type = (int) screen.random(1,3);
			float x = screen.random(0,screen.width-20);
			float y = screen.random(0,screen.height-20);
			if(type==1)items.add(new Item(x, y, true));
			if(type==2)items.add(new Item(x, y, false));
			items.elementAt(i).setImg(screen.loadImage("double.png"));
			items.elementAt(i).setImg2(screen.loadImage("extralife.png"));
		}
	} 
	
	public boolean WinLose(PApplet screen){//Si pierde o gana 
		if(Nave.getLife()<=0){//si la vida de la nave llega a 0
			screen.fill(255,0,0);
			screen.text("YOU LOST", (screen.width/2),(screen.height/2) );
			screen.text(Puntaje, (screen.width/2), (screen.height/2)+10);
			screen.exit();
		}else if(asteroidesChicos.isEmpty() && asteroidesGrandes.isEmpty() && enemigos.isEmpty()){//Si logramos eliminar a todos los enemigos
			screen.fill(0,255,0);
			screen.text("YOU WON", (screen.width/2),(screen.height/2) );
			screen.text(Puntaje, (screen.width/2), (screen.height/2)+10);
			if(level.getLvL()<3){//Si quedan más niveles por delante
			return true;
			}else{	//En el caso de que no hayan más niveles
			screen.fill(0,255,0);
			screen.text("THE END", (screen.width/3),(screen.height/2) );
			screen.text(Puntaje, (screen.width/2), (screen.height/2)+10);
			}
		}
		return false;
	}

	public void colisiones(PApplet screen){//Verifica los choques de todo
		//INTENTE UTILIZAR ITERATOR PERO NO ME FUNCIONABA CORRECTAMENTE.
		//Utilizamos un auxiliar para eliminar el objeto correspondiente del vector que le corresponda.
		BigAsteroid Auxi = null;
		Laser Laux =null;
		Item Iaux = null;
		Lifes=Nave.getLife();
		screen.fill(255,255,255);
		String Score = "SCORE: "+Puntaje;
		String life = "LIFES: ";
		screen.textSize(12);
		screen.text(Score, 10, 10);
		screen.text(life, 15, 30);
		float xl=50,yl=20;
		for(int i=0;i<Lifes;i++){
			if(xl+10>screen.width){
				xl=50;
				yl+=16;
			}
			screen.image(lifeI, xl, yl,10,15);
			xl+=12;
		}
		for(BigAsteroid Act : asteroidesGrandes){//Comprabacion de asteroides Grandes
			if(verificarColision(Act, Nave.getXcentro()-Nave.getAlto()/2, Nave.getYcentro()-(Nave.getAlto()/2)) || verificarColision(Act, Nave.getXcentro()+Nave.getAlto()/2, Nave.getYcentro()+Nave.getAlto()/2)|| verificarColision(Act, Nave.getXcentro()+Nave.getAlto()/2, Nave.getYcentro()-Nave.getAlto()/2)|| verificarColision(Act, Nave.getXcentro()-Nave.getAlto()/2, Nave.getYcentro()+Nave.getAlto()/2)){
				screen.fill(255,0,0);
				Nave.setLife(Nave.getLife()-1);
				Auxi = Act;//En el caso de que nuestra nave tenga mas de uno de vida eliminamos el objeto que choco para evitar que este siga colisionandola y quitandole más vida.
				Auxi.destruir(this,screen);
				Puntaje=0;
				}
			for(Laser Bact : Nave.getBalas()){//Si la nave le acierta a un ASTEROIDE GRANDE.
				if(verificarColision(Act, Bact.getX(), Bact.getY()) || (verificarColision(Act, Bact.getX()+5, Bact.getY()+5))){
					Auxi = Act;
					Auxi.destruir(this,screen);
					Laux = Bact;
					Puntaje += KillAsteroid;
				}
			}
		}
		Nave.getBalas().remove(Laux);
		asteroidesGrandes.remove(Auxi);
		SmallAsteroid Aux = null;
		for(SmallAsteroid Act : asteroidesChicos){//Comprabacion de asterroides chicos
			if(verificarColision(Act, Nave.getXcentro()-Nave.getAlto()/2, Nave.getYcentro()-(Nave.getAlto()/2)) || verificarColision(Act, Nave.getXcentro()+Nave.getAlto()/2, Nave.getYcentro()+Nave.getAlto()/2)|| verificarColision(Act, Nave.getXcentro()+Nave.getAlto()/2, Nave.getYcentro()-Nave.getAlto()/2)|| verificarColision(Act, Nave.getXcentro()-Nave.getAlto()/2, Nave.getYcentro()+Nave.getAlto()/2)){
				Nave.setLife(Nave.getLife()-1);
				Aux=Act;
				Puntaje=0;
			}
			for(Laser Bact : Nave.getBalas()){//Si la nave le acierta a un ASTEROIDE CHICO.
				if(verificarColision(Act, Bact.getX(), Bact.getY()) || (verificarColision(Act, Bact.getX()+5, Bact.getY()+5))){
					Aux = Act;
					Laux = Bact;
					Puntaje+= KillAsteroid;
				}
			}
		}
		Nave.getBalas().remove(Laux);
		asteroidesChicos.remove(Aux);
		Laser EnLaux = null;
		Enemy Eaux =null;
		for(Enemy Act : enemigos){//Colision enemigos
			if(verificarColision(Act, Nave.getXcentro()-Nave.getAlto()/2, Nave.getYcentro()-(Nave.getAlto()/2)) || verificarColision(Act, Nave.getXcentro()+Nave.getAlto()/2, Nave.getYcentro()+Nave.getAlto()/2)|| verificarColision(Act, Nave.getXcentro()+Nave.getAlto()/2, Nave.getYcentro()-Nave.getAlto()/2)|| verificarColision(Act, Nave.getXcentro()-Nave.getAlto()/2, Nave.getYcentro()+Nave.getAlto()/2)){
				Nave.setLife(Nave.getLife()-1);
				Eaux=Act;
				Puntaje=0;
			}
			for(Laser Bact : Nave.getBalas()){//Si la nave le acierta a un enemigo
				if(verificarColision(Act, Bact.getX(), Bact.getY()) || (verificarColision(Act, Bact.getX()+5, Bact.getY()+5))){
					Eaux = Act;
					Laux = Bact;
					Puntaje+=KillEnemy;
				}
			}
			for(Laser Bact : Act.getBalas()){//Si las balas de enemigo da a nave
				if(verificarColision(Nave, Bact.getX(), Bact.getY()) || (verificarColision(Nave, Bact.getX()+5, Bact.getY()+5))){
					Nave.setLife(Nave.getLife()-1);
					EnLaux=Bact;
					Puntaje=0;
				}
			}
			Act.getBalas().remove(EnLaux);
		}
		Nave.getBalas().remove(Laux);
		enemigos.remove(Eaux);
		for(Item Iact : items){//Comprobacion de la nave si toca un item
			if(verificarColision(Nave, Iact.getX(), Iact.getY()) || (verificarColision(Nave, Iact.getX()+Iact.getLado(), Iact.getY()+Iact.getLado()))){
				Iaux = Iact;
				if(Iaux.isAccion()){
					KillEnemy= KillEnemy*Iact.getBonus();
					KillAsteroid = KillAsteroid*Iact.getBonus();
				}else if(!Iaux.isAccion()){
					Nave.setLife(Nave.getLife()+1);
				}
			}
		}
		items.remove(Iaux);
		WinLose(screen);
	}
	public void mostradorDeItems(int cantAMostrar, PApplet screen){// muestra los items que ya aparecieron
		for(int i=0;i<items.size() && i< cantAMostrar;i++){
			items.elementAt(i).dibujar(screen);
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
		if(X<=ship.getXcentro()+Nave.getAlto()/2 && X>=ship.getXcentro()-Nave.getAlto()/2){
			if(Y<=ship.getYcentro()+(Nave.getAlto()/2) && Y>=ship.getYcentro()-(Nave.getAlto()/2)){
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Vector<SmallAsteroid> getAsteroidesChicos() {
		return asteroidesChicos;
	}

	public void setAsteroidesChicos(Vector<SmallAsteroid> asteroidesChicos) {
		this.asteroidesChicos = asteroidesChicos;
	}

	public Vector<BigAsteroid> getAsteroidesGrandes() {
		return asteroidesGrandes;
	}

	public void setAsteroidesGrandes(Vector<BigAsteroid> asteroidesGrandes) {
		this.asteroidesGrandes = asteroidesGrandes;
	}

	public Ship getNave() {
		return Nave;
	}

	public void setNave(Ship nave) {
		Nave = nave;
	}

	public Vector<Enemy> getEnemigos() {
		return enemigos;
	}

	public void setEnemigos(Vector<Enemy> enemigos) {
		this.enemigos = enemigos;
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
		return Puntaje;
	}


	public void setPuntaje(int puntaje) {
		Puntaje = puntaje;
	}

	public int getKillEnemy() {
		return KillEnemy;
	}

	public void setKillEnemy(int killEnemy) {
		KillEnemy = killEnemy;
	}

	public int getKillAsteroid() {
		return KillAsteroid;
	}

	public void setKillAsteroid(int killAsteroid) {
		KillAsteroid = killAsteroid;
	}

}
