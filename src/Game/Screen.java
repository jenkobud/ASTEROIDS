package Game;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import Asteroids.BigAsteroid;
import Asteroids.SmallAsteroid;
import Enemies.Enemy;
import processing.core.PApplet;
import processing.core.PImage;

public class Screen extends PApplet{
	PImage Fondo,pausa;//Imagen de Fondo
	Platform plataforma;
	boolean[] keys = new boolean[8];//vector de booleans utilizado para mejorar la jugabilidad cuando se presionan las teclas o el mouse
	//0=W; 1=s; 2=d; 3=a; 4=mouse; 5=G(guardar); 6=C(Cargar); 7=SpaceBar(shoot)
	int cont,contDeItems,puntajeAnt,lvl;
	Boolean isPaused;
	
	public void settings() {
		size(1200, 800);
	}

	public void setup() {
		isPaused = false;
		pausa = loadImage("pause.png");
		Fondo = loadImage("FondoDeJuego.jpg");//Cargamos la IMG de fondo
		background(Fondo);//se la asignamos al fondo
		background(0, 50);
		plataforma = new Platform("Juego",1,this);//creamos la plataforma
		cont=0;
		contDeItems=0;
		lvl=0;
		
	}
	
	public void draw() {
		noCursor();
		background(Fondo);
		if(!isPaused){
			keyVerificar();//funcion para mejorar el uso de teclas
			plataforma.getNave().dibujar(this);//mostramos la nave
			plataforma.colisiones(this);
			plataforma.getNave().seguirMouse(this);
			if(cont==300){//Muestro los items cada cierto tiempo.
				if(contDeItems<plataforma.getItems().size()){//Muestro los items cada cierto tiempo.
					plataforma.getItems().elementAt(contDeItems).dibujar(this);
					contDeItems++;//Pasamos al siguiente item
					cont=0;//contador que se reinicia cuando muestro un item
				}
			}
			plataforma.mostradorDeItems(contDeItems,this);
			cont++;//contador que cuenta el tiempo para mostrar un  items
			for(SmallAsteroid Actual : plataforma.getAsteroidesChicos()){//dibuja Asteroides chicos
				Actual.dibujar(this);
				Actual.movimiento(0, this);
			}
			for(BigAsteroid Actual : plataforma.getAsteroidesGrandes()){//dibuja Asteroides grandes
				Actual.dibujar(this);
				Actual.movimiento(0, this);
			}
			for(Enemy Actual : plataforma.getEnemigos()){//dibuja enemigos
				Actual.dibujar(this);
				Actual.disparar(Actual.getXspeed(),3);
				Actual.movimiento(0, this);
			}
			if(plataforma.winLose(this)==true){//Si ganamos y quedan m�s niveles
				puntajeAnt = plataforma.getPuntaje();//Guardamos el puntaje anterior para pode seguir con el mismo en el proximo lvl
				plataforma = new Platform(plataforma.getName(),plataforma.getLevel().getLvL()+1 , this);//subimos unon la dificultad dell nivel.a
				plataforma.setPuntaje(puntajeAnt);//Se lo asignamos par seguir con el juego
				
				//Reiniciamos ambos al subir de nivel para poder empezar todo de nuevo
				cont=0;
				contDeItems=0;
			}
		}
		if(isPaused){
    		imageMode(CENTER);
			image(pausa,(float) (this.width/2),this.height/2,pausa.width*2,pausa.height*2);
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
		if (keyCode == 'w'|| keyCode == 'W') { keys[0] = true; }
		//Si tocamos hacia abajo
		if (keyCode == 's'|| keyCode == 'S') { keys[1] = true; }
		//Si tocamos hacia la derecha
		if (keyCode == 'd'|| keyCode == 'D') { keys[2] = true; }
		//Si tocamos hacia la izquierda
		if (keyCode == 'a'|| keyCode == 'A') { keys[3] = true; }
		//Si guardamos estado del juego.
		if(keyCode == 'g'|| keyCode=='G'){ keys[5] = true; }
		//Si cargamos estado del juego.
		if(keyCode == 'c'|| keyCode=='C'){ keys[6] = true; }
		//Si disparamos (SpaceBar)
		if(keyCode == 32){ keys[7] = true; }
	}
	public void keyReleased(){
		//Se deshabilita la key
	    if (keyCode == 'w'|| keyCode == 'W') { keys[0] = false; }
	    if (keyCode == 's'|| keyCode == 'S') { keys[1] = false; }
		if (keyCode == 'd'|| keyCode == 'D') { keys[2] = false; }
	    if (keyCode == 'a'|| keyCode == 'A') { keys[3] = false; }
	    if (keyCode == 'g'|| keyCode == 'G') { keys[5] = false; }
	    if (keyCode == 'c'|| keyCode == 'C') { keys[6] = false; }
		if(keyCode == 32) { keys[7] = false; }

	}
	public void keyVerificar(){//Funcion para reconocimiento de tecla pulsada
		if (keys[7]) {
			plataforma.getNave().disparar(0, 0);//Se dispara
			keys[7] = false;
		}
		if (keys[0]) {
	    	plataforma.getNave().movimiento(1, this);//Se envia un 1 que seria igual a Arriba y la pantalla para sus parametros(Alto, ancho)
	    }
		if (keys[1]) {
			plataforma.getNave().movimiento(2, this);//Se envia un 1 que seria igual a Arriba y la pantalla para sus parametros(Alto, ancho)
		}
		if (keys[2]) {
	    	plataforma.getNave().movimiento(3,this);//Se envia un 3 que seria igual a Derecha y la pantalla para sus parametros(Alto, ancho)
	    }
	    if (keys[3]) {
	    	plataforma.getNave().movimiento(4,this);//Se envia un 4 que seria igual a Izquierda y la pantalla para sus parametros(Alto, ancho)
		 } 
	    if(keys[4]){//Se envian las coordenadas del mouse
			plataforma.getNave().disparar(mouseX, mouseY);
	    }
	    if(keys[5]){//Guardar
	    	String nombre;
		    nombre = JOptionPane.showInputDialog("INPUT YOUR NAME...");
		    if(nombre == null){
		    	keys[5]=false;
		    	return;
		    	}//si tocamos cancelar salddriamos de la funcion
	    	plataforma.setName(nombre);
	    	
	    	try {
				Data.SavePlatform(plataforma, plataforma.getItems().size()-this.contDeItems, this.contDeItems);
	    		Data.Save(plataforma.getAsteroidesChicos(), plataforma.getAsteroidesGrandes(), plataforma.getEnemigos(), plataforma.getNave(), plataforma, plataforma.getItems());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	keys[5]=false;
	    }
	    if(keys[6]){//Cargar
	    	int PlatformLvl=0,lvl;//Este en realidad es el Id de nuestra plataforma con el que vamos a llamar al resto de los objetos de nuestra plataforma.
	    	String Name, Lvl;
		    Name= JOptionPane.showInputDialog("INPUT YOUR NAME...");
		    Lvl = JOptionPane.showInputDialog("INTUP YOUR LEVEL...");
		    if(Lvl==null||Name==null){
		    	keys[6]=false;
		    	return;
		    }//si tocamos cancelar salddriamos de la funcion
		    lvl = Integer.parseInt(Lvl);//No le llega la variable bien!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
	    	try {
				PlatformLvl =Data.getNroPlataforma(Name,lvl);//pedimos el id de la plataforma
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	    	try {
				plataforma = Data.getPlataforma(Name,lvl, this);//pedimos y asignamos nuestra plataforma guardada
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
	    	try {
				plataforma.setNave(Data.getNave(PlatformLvl));//pedimos y asignamos la nave
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
	    	try {
				this.contDeItems=Data.getcontItems(PlatformLvl);
				this.cont=0;
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
	    	try {
				plataforma.setAsteroidesChicos(Data.getSmallAstLvl(PlatformLvl,this));//pedimos y asignamos el vector de SmallAst
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
	    	try {
				plataforma.setAsteroidesGrandes(Data.getBigAstLvl(PlatformLvl,this));//pedimos y asignamos el vector de BigAst
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
	    	try {
				plataforma.setEnemigos(Data.getEnemyLvl(PlatformLvl,this));//pedimos y asignamos el vector de enemigos
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
	    	try {
				plataforma.setItems(Data.getItemsLvl(PlatformLvl,this));//pedimos y asignamos el vector de items
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
	    	keys[6]=false;
	    }
	}	
	
	public static void main(String[] args) {
		PApplet.main(new String[] { "Game.Screen" });
	}

}
