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
	boolean[] keys = new boolean[7];//vector de booleans utilizado para mejorar la jugabilidad cuando se presionan las teclas o el mouse
	//0=W; 1=s; 2=d; 3=a; 4=mouse; 5=G(guardar); 6=C(Cargar)
	int cont,contDeItems,puntajeAnt,lvl,pause;
	
	public void settings()
	{
		size(1200, 800);
		
	}

	public void setup()
	{
		pause = 0;
		pausa = loadImage("pause.png");
		Fondo = loadImage("FondoDeJuego.jpg");//Cargamos la IMG de fondo
		background(Fondo);//se la asignamos al fondo
		background(0, 50);
		plataforma = new Platform("Juego",1,this);//creamos la plataforma
		cont=0;
		contDeItems=0;
		lvl=0;
		
	}
	
	public void draw()
	{
		noCursor();
		background(Fondo);
		if(pause==0){
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
				Actual.Movimiento(0, this);
			}
			for(BigAsteroid Actual : plataforma.getAsteroidesGrandes()){//dibuja Asteroides grandes
				Actual.dibujar(this);
				Actual.Movimiento(0, this);
			}
			for(Enemy Actual : plataforma.getEnemigos()){//dibuja enemigos
				Actual.dibujar(this);
				Actual.disparar(Actual.getXspeed(),3);
				Actual.Movimiento(0, this);
			}
			if(plataforma.WinLose(this)==true){//Si ganamos y quedan m�s niveles 
				puntajeAnt = plataforma.getPuntaje();//Guardamos el puntaje anterior para pode seguir con el mismo en el proximo lvl
				plataforma = new Platform(plataforma.getName(),plataforma.getLevel().getLvL()+1 , this);//subimos unon la dificultad dell nivel.a
				plataforma.setPuntaje(puntajeAnt);//Se lo asignamos par seguir con el juego
				
				//Reiniciamos ambos al subir de nivel para poder empezar todo de nuevo
				cont=0;
				contDeItems=0;
			}
		}
		if(pause==1){
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
	    if (keyCode == 'w'||keyCode == 'W') {//Si tocamos hacia arriba
	    	keys[0] = true;//Se habilita la key correspondiente para que se mueva hacia donnde corresponda
	    }
	    if (keyCode == 32) {//Si tocamos hacia abajo
	    	keys[1] = true;//Se habilita la key correspondiente para que se mueva haciaa donnde corresponda
		}
		if (keyCode == 'd'||keyCode == 'D') {//Si tocamos hacia la derecha
			keys[2] = true;//Se habilita la key correspondiente para que se mueva hacia donnde corresponda
	    }
	    if (keyCode == 'a'||keyCode == 'A') {//Si tocamos hacia la izquierda
	    	keys[3] = true;//Se habilita la key correspondiente para que se mueva hacia donnde corresponda
		 } 
	    if(keyCode== 'g'||keyCode=='G'){//Si guardamos
	    	keys[5] = true;//Se habilita la key correspondiente para que se mueva hacia donnde corresponda
	    }
	    if(keyCode== 'c'||keyCode=='C'){//Si cargamos
	    	keys[6] = true;//Se habilita la key correspondiente para que se mueva hacia donnde corresponda
	    }
	    if(keyCode== 'P'||keyCode== 'p'){
	    	if(pause==0){
	    		pause = 1;
	    	}else{
	    		pause = 0;
	
	    	}
	    }
	}
	public void keyReleased(){
		//Se deshabilita la key
	    if (keyCode == 'w'||keyCode == 'W') {
	    	keys[0] = false;
	    }
	    if (keyCode == 32) {
	    	keys[1] = false;
		}
		if (keyCode == 'd'||keyCode == 'D') {
			keys[2] = false;
	    }
	    if (keyCode == 'a'||keyCode == 'A') {
	    	keys[3] = false;
		 }
	    if(keyCode== 'g'||keyCode=='G'){
	    	keys[5] = false;
	    }
	    if(keyCode== 'c'||keyCode=='C'){
	    	keys[6] = false;
	    }

	}
	public void keyVerificar(){//Funcion para reconocimiento de tecla pulsada
	    if (keys[0]) {
	    	plataforma.getNave().Movimiento(1, this);//Se envia un 1 que seria igual a Arriba y la pantalla para sus parametros(Alto, ancho)
	    }
	    if (keys[1]) {
	    	plataforma.getNave().disparar(0, 0);//Se envia un 2 que seria igual a Abajo y la pantalla para sus parametros(Alto, ancho) 
			keys[1] = false;
	   
		}
		if (keys[2]) {
	    	plataforma.getNave().Movimiento(3,this);//Se envia un 3 que seria igual a Derecha y la pantalla para sus parametros(Alto, ancho)
	    }
	    if (keys[3]) {
	    	plataforma.getNave().Movimiento(4,this);//Se envia un 4 que seria igual a Izquierda y la pantalla para sus parametros(Alto, ancho)
		 } 
	    if(keys[4]){//Se envian las coordenadas del mouse
			plataforma.getNave().disparar(mouseX, mouseY);
	    }
	    if(keys[5]){//Guardar
	    	String Nombre;
		    Nombre = JOptionPane.showInputDialog("INPUT YOUR NAME...");
		    if(Nombre==null){
		    	keys[5]=false;
		    	return;
		    	}//si tocamos cancelar salddriamos de la funcion
	    	plataforma.setName(Nombre);
	    	
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
