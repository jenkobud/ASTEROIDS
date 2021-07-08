package Game;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import Asteroids.BigAsteroid;
import Asteroids.SmallAsteroid;
import Enemies.Enemy;
import processing.core.PApplet;
import processing.core.PImage;

public class Screen extends PApplet{
	PImage background, pause;//Imagen de Fondo
	Platform platform1;
	boolean[] keys = new boolean[8];//vector de booleans utilizado para mejorar la jugabilidad cuando se presionan las teclas o el mouse
	//0=W; 1=s; 2=d; 3=a; 4=mouse; 5=G(guardar); 6=C(Cargar); 7=SpaceBar(shoot)
	Integer ARRIBA = 0;
	Integer ABAJO = 1;
	Integer DERECHA = 2;
	Integer IZQUIERDA = 3;
	Integer MOUSE = 4;
	Integer GUARDAR = 5;
	Integer CARGAR = 6;
	Integer SPACEPAR = 7;
	int count, itemsCounter, scoreBefore, lvl;
	Boolean isPaused;
	
	public void settings() {
		size(1200, 800);
	}

	public void setup() {
		isPaused = false;
		pause = loadImage("pause.png");
		background = loadImage("FondoDeJuego.jpg");//Cargamos la IMG de fondo
		background(background);//se la asignamos al fondo
		background(0, 50);
		platform1 = new Platform("Juego",1,this);//creamos la plataforma
		count = 0;
		itemsCounter = 0;
		lvl= 0;
	}
	
	public void draw() {
		noCursor();
		background(background);
		if(!isPaused){
			keyVerification();//funcion para mejorar el uso de teclas
			platform1.getShip().draw(this);//mostramos la nave
			platform1.collisions(this);
			platform1.getShip().followMouse(this);
			if((count == 300) && (itemsCounter < platform1.getItems().size())){//Muestro los items cada cierto tiempo.
				platform1.getItems().elementAt(itemsCounter).draw(this);
				itemsCounter++;//Pasamos al siguiente item
				count = 0;//contador que se reinicia cuando muestro un item
			}
			platform1.mostradorDeItems(itemsCounter,this);
			count++;//contador que cuenta el tiempo para mostrar un  items
			for(SmallAsteroid Actual : platform1.getSmallAsteroids()){//dibuja Asteroides chicos
				Actual.draw(this);
				Actual.movement(0, this);
			}
			for(BigAsteroid Actual : platform1.getBigAsteroids()){//dibuja Asteroides grandes
				Actual.draw(this);
				Actual.movement(0, this);
			}
			for(Enemy Actual : platform1.getEnemies()){//dibuja enemigos
				Actual.draw(this);
				Actual.shoot(Actual.getXspeed(),3);
				Actual.movement(0, this);
			}
			if(platform1.winLose(this)){//Si ganamos y quedan m�s niveles
				scoreBefore = platform1.getPuntaje();//Guardamos el puntaje anterior para pode seguir con el mismo en el proximo lvl
				platform1 = new Platform(platform1.getName(), platform1.getLevel().getLvL()+1 , this);//subimos unon la dificultad dell nivel.a
				platform1.setPuntaje(scoreBefore);//Se lo asignamos par seguir con el juego
				
				//Reiniciamos ambos al subir de nivel para poder empezar todo de nuevo
				count = 0;
				itemsCounter = 0;
			}
		}else{
    		imageMode(CENTER);
			image(pause, (float) (this.width)/2,(float)(this.height)/2, pause.width*2, pause.height*2);
			//textSize(200);
			//fill(255);
			//this.text("PAUSA", width/2-300, height/2+80);
		}
	}

	/*public void mousePressed(){//Cuando presionamos el mouse.
		keys[4] = true;//Se habilita la key correspondiente para que dispare 
		keyVerificar();//mostramos el tiro
		keys[4] = false;//lo dehabilitamos para que por click nada m�s se dispare 1 vez
	}*/
	public void keyPressed(){
		//Si pausamos el juego.
		if(keyCode== 'P'|| keyCode== 'p'){ isPaused = !isPaused; }
		//Si tocamos hacia arriba
		if (keyCode == 'w'|| keyCode == 'W') { keys[ARRIBA] = true; }
		//Si tocamos hacia abajo
		if (keyCode == 's'|| keyCode == 'S') { keys[ABAJO] = true; }
		//Si tocamos hacia la derecha
		if (keyCode == 'd'|| keyCode == 'D') { keys[DERECHA] = true; }
		//Si tocamos hacia la izquierda
		if (keyCode == 'a'|| keyCode == 'A') { keys[IZQUIERDA] = true; }
		//Si guardamos estado del juego.
		if(keyCode == 'g'|| keyCode=='G'){ keys[GUARDAR] = true; }
		//Si cargamos estado del juego.
		if(keyCode == 'c'|| keyCode=='C'){ keys[CARGAR] = true; }
		//Si disparamos (SpaceBar)
		if(keyCode == 32){ keys[SPACEPAR] = true; }
	}
	public void keyReleased(){
		//Se deshabilita la key
	    if (keyCode == 'w'|| keyCode == 'W') { keys[ARRIBA] = false; }
	    if (keyCode == 's'|| keyCode == 'S') { keys[ABAJO] = false; }
		if (keyCode == 'd'|| keyCode == 'D') { keys[DERECHA] = false; }
	    if (keyCode == 'a'|| keyCode == 'A') { keys[IZQUIERDA] = false; }
	    if (keyCode == 'g'|| keyCode == 'G') { keys[GUARDAR] = false; }
	    if (keyCode == 'c'|| keyCode == 'C') { keys[CARGAR] = false; }
		if(keyCode == 32) { keys[SPACEPAR] = false; }

	}
	public void keyVerification(){//Funcion para reconocimiento de tecla pulsada
		//Se dispara
		if (keys[SPACEPAR]) {
			platform1.getShip().shoot(0, 0);
			//Necesario para que se dispare de una bala a la vez.
			keys[SPACEPAR] = false;
		}
		//Se envia un 1 que seria igual a Arriba y la pantalla para sus parametros(Alto, ancho)
		if (keys[ARRIBA]) { platform1.getShip().movement(ARRIBA, this); }
		//Se envia un 1 que seria igual a Abajo y la pantalla para sus parametros(Alto, ancho)
		if (keys[ABAJO]) { platform1.getShip().movement(ABAJO, this); }
		//Se envia un 3 que seria igual a Derecha y la pantalla para sus parametros(Alto, ancho)
		if (keys[DERECHA]) { platform1.getShip().movement(DERECHA,this); }
	    //Se envia un 4 que seria igual a Izquierda y la pantalla para sus parametros(Alto, ancho)
		if (keys[IZQUIERDA]) { platform1.getShip().movement(IZQUIERDA,this); }
	    //Se envian las coordenadas del mouse
		 if(keys[MOUSE]){ platform1.getShip().shoot(mouseX, mouseY); }
		//Guardar
	    if(keys[GUARDAR]){
	    	String name;
		    name = JOptionPane.showInputDialog("INPUT YOUR NAME...");
		    if(name == null){
		    	keys[GUARDAR]=false;
		    	return;
		    	}//si tocamos cancelar saldriamos de la funcion
	    	platform1.setName(name);
	    	
	    	try {
				Data.SavePlatform(platform1, platform1.getItems().size()-this.itemsCounter, this.itemsCounter);
	    		Data.Save(platform1.getSmallAsteroids(), platform1.getBigAsteroids(), platform1.getEnemies(), platform1.getShip(), platform1, platform1.getItems());
			} catch (ClassNotFoundException e) { e.printStackTrace(); }
	    	  catch (SQLException e) { e.printStackTrace(); }
	    	keys[GUARDAR]=false;
	    }
	    if(keys[CARGAR]){//Cargar
	    	int PlatformLvl=0,lvl;//Este en realidad es el Id de nuestra plataforma con el que vamos a llamar al resto de los objetos de nuestra plataforma.
	    	String Name, Lvl;
		    Name= JOptionPane.showInputDialog("INPUT YOUR NAME...");
		    Lvl = JOptionPane.showInputDialog("INTUP YOUR LEVEL...");
		    if(Lvl==null||Name==null){
		    	keys[CARGAR]=false;
		    	return;
		    }//si tocamos cancelar salddriamos de la funcion
		    lvl = Integer.parseInt(Lvl);//No le llega la variable bien!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
	    	try {
				PlatformLvl =Data.getNroPlataforma(Name,lvl);//pedimos el id de la plataforma
			} catch (ClassNotFoundException e2) { e2.printStackTrace(); }
	    	  catch (SQLException e2) { e2.printStackTrace(); }

	    	try {
				platform1 = Data.getPlataforma(Name,lvl, this);//pedimos y asignamos nuestra plataforma guardada
			} catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }

	    	try {
				platform1.setShip(Data.getNave(PlatformLvl));//pedimos y asignamos la nave
			} catch (ClassNotFoundException | SQLException e1) { e1.printStackTrace(); }

	    	try {
				this.itemsCounter =Data.getcontItems(PlatformLvl);
				this.count =0;
			} catch (ClassNotFoundException | SQLException e1) { e1.printStackTrace(); }

	    	try {
				platform1.setSmallAsteroids(Data.getSmallAstLvl(PlatformLvl,this));//pedimos y asignamos el vector de SmallAst
			} catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }

	    	try {
				platform1.setBigAsteroids(Data.getBigAstLvl(PlatformLvl,this));//pedimos y asignamos el vector de BigAst
			} catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }

	    	try {
				platform1.setEnemies(Data.getEnemyLvl(PlatformLvl,this));//pedimos y asignamos el vector de enemigos
			} catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }

	    	try {
				platform1.setItems(Data.getItemsLvl(PlatformLvl,this));//pedimos y asignamos el vector de items
			} catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }

	    	keys[CARGAR]=false;
	    }
	}	
	
	public static void main(String[] args) {
		PApplet.main(new String[] { "Game.Screen" });
	}

}
