package Game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import processing.core.PApplet;
import Ships.Ship;
import Things.Item;
import Asteroids.BigAsteroid;
import Asteroids.SmallAsteroid;
import Enemies.Enemy;
import Enemies.EnemyLvL1;
import Enemies.EnemyLvL2;

public class Data {
/*
 Cargar--------------------------------------------------------------------------------------------------------------------------------------
 */
	public static Platform getPlataforma(String Name, int numeroNivel, PApplet screen) throws ClassNotFoundException, SQLException//agarramos Items
	{
		Platform resultado=null;
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "") ;//Se conecta con la base de datos de nuestro juego
		Statement stmt = conn.createStatement() ;//Corrobora que se conecte correctamente a la BD
		String query = "SELECT * FROM platform WHERE Nivel = "+numeroNivel+" AND Name = '"+Name+"'" ;//Creamos nuestra consulta
		ResultSet rs = stmt.executeQuery(query) ;//La consulta es ejecutada en la BD
		while(rs.next()){//mientras que el query devuelva algo
			resultado = new Platform(rs.getString("Name"), rs.getInt("Nivel"), screen);//Creamos una nueva plataforma la cual se la asignamos luego a la plataforma
		}
		return resultado;
	}
	public static int getNroPlataforma(String Name, int numeroNivel) throws ClassNotFoundException, SQLException
	{
		int resultado=0;
		
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "") ;//Se conecta con la base de datos de nuestro juego
		Statement stmt = conn.createStatement() ;
		String query = "SELECT NroLvl FROM platform WHERE Nivel = "+numeroNivel+" AND Name = '"+Name+"'" ;//Creamos nuestra consulta
		ResultSet rs = stmt.executeQuery(query) ;//La consulta es ejecutada en la BD
		while(rs.next()){//mientras que el query devuelva algo
			resultado = rs.getInt("NroLvl");//Le asignamos a la variable que devolvemos el valor que pedimos a la BD
		}
		return resultado;
	}
	
	public static Ship getNave(int numeroNivel) throws ClassNotFoundException, SQLException//agarramos Items
	{
		Ship resultado=null;
		
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "") ;
		Statement stmt = conn.createStatement() ;
		String query = "SELECT * FROM ship WHERE Platform_NroLvl = "+numeroNivel+"" ;
		ResultSet rs = stmt.executeQuery(query) ;
		while(rs.next()){
			resultado = new Ship((int)rs.getFloat("Xcenter"), (int)rs.getFloat("Ycenter"),rs.getInt("Alto"));
		}
		return resultado;
	}
	//El metodo utilizado en getSmallAstLvl es el mismo que los utilizados en getBigAstLvl, getEnemyLvl, getItemsLvl.
	public static Vector<SmallAsteroid> getSmallAstLvl(int numeroNivel,PApplet screen) throws ClassNotFoundException, SQLException//agarramos Asteroides chicos
	{
		Vector<SmallAsteroid> resultado = new Vector<SmallAsteroid>();//creamos un vector de asteroides para reemplazarlo luego en nuestra pantalla
		
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "") ;
		Statement stmt = conn.createStatement() ;
		String query = "SELECT * FROM smallasteroid  WHERE Platform_NroLvl = "+numeroNivel+"" ;//consulta
		ResultSet rs = stmt.executeQuery(query) ;//Ejecucion
	
		while (rs.next())//mientras que haya una siguiente devolución
		{
			SmallAsteroid ast = new SmallAsteroid(rs.getFloat("X"),rs.getFloat("Y"),rs.getInt("radio"),rs.getFloat("Xspeed"),rs.getFloat("Yspeed"));
			ast.setImg(screen.loadImage("ast.png"));
			resultado.add(ast);//creamos un nuevo asteroide y se lo agregamos al vector correspondiente
		}
		return resultado;
	}
	
	public static Vector<BigAsteroid> getBigAstLvl(int numeroNivel,PApplet screen) throws ClassNotFoundException, SQLException//agarramos Asteroides  grandes
	{
		Vector<BigAsteroid> resultado = new Vector<BigAsteroid>();
		
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "") ;
		Statement stmt = conn.createStatement() ;
		String query = "SELECT * FROM bigasteroid WHERE Platform_NroLvl = "+numeroNivel+"";
		ResultSet rs = stmt.executeQuery(query) ;
	
		while (rs.next())
		{
			BigAsteroid astb = new BigAsteroid(rs.getFloat("X"),rs.getFloat("Y"),rs.getInt("radio"),rs.getFloat("Xspeed"),rs.getFloat("Yspeed"));
			astb.setImg(screen.loadImage("ast2.png"));
			resultado.add(astb);
		}
		
		return resultado;
	}
	public static Vector<Enemy> getEnemyLvl(int numeroNivel,PApplet screen) throws ClassNotFoundException, SQLException//agarramos Enemigos
	{
		Vector<Enemy> resultado = new Vector<Enemy>();
		
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "") ;
		Statement stmt = conn.createStatement() ;
		String query = "SELECT * FROM enemylvl1 WHERE Platform_NroLvl = "+numeroNivel+"" ;
		ResultSet rs = stmt.executeQuery(query) ;
	
		while (rs.next())
		{
			EnemyLvL1 enem1 = new EnemyLvL1(rs.getFloat("X"),rs.getFloat("Y"));
			enem1.setImg(screen.loadImage("Lvl1.png"));
			resultado.add(enem1);
		}
		String query2 = "SELECT * FROM enemylvl2 WHERE Platform_NroLvl = "+numeroNivel+"" ;
		ResultSet rs2 = stmt.executeQuery(query2);
		while (rs2.next())
		{
			EnemyLvL2 enem2 = new EnemyLvL2(rs2.getFloat("X"),rs2.getFloat("Y"));
			enem2.setImg(screen.loadImage("Lvl2.png"));
			resultado.add(enem2);
		}
		
		return resultado;
	}
	public static Vector<Item> getItemsLvl(int numeroNivel,PApplet screen) throws ClassNotFoundException, SQLException//agarramos Items
	{
		Vector<Item> resultado = new Vector<Item>();
		
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "") ;
		Statement stmt = conn.createStatement() ;
		String query = "SELECT * FROM item WHERE Platform_NroLvl = "+numeroNivel+"" ;
		ResultSet rs = stmt.executeQuery(query) ;
	
		while (rs.next())
		{
			Item item = new Item(rs.getFloat("X"),rs.getFloat("Y"),rs.getBoolean("Accion"));
			item.setBonus(rs.getInt("Bonus"));
			resultado.add(item);
		}
		for(int i=0;i<resultado.size();i++ ){
			resultado.elementAt(i).setImg(screen.loadImage("double.png"));
			resultado.elementAt(i).setImg2(screen.loadImage("extralife.png"));
		}
		return resultado;
	}
	
	public static int getcontItems(int numeroNivel) throws ClassNotFoundException, SQLException//agarramos Items
	{
		int resultado=0;
		
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "") ;
		Statement stmt = conn.createStatement() ;
		String query = "SELECT * FROM platform WHERE Nivel = "+numeroNivel+"" ;
		ResultSet rs = stmt.executeQuery(query) ;
		while(rs.next()){
			resultado = rs.getInt("cantItemsShown");
		}
		return resultado;
	}
/*	
 Guardar--------------------------------------------------------------------------------------------------------------------------------------
*/
	public static void SavePlatform(Platform plataforma,int CantItemLeft,int CantItemShown) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "") ;//Se conecta a nuestra BD
		Statement stmt = conn.createStatement() ;//Consulta si fue posible conectarse a nuestra base de datos
		String query;
		String Name = plataforma.getName();
		int Lvl = plataforma.getLevel().getLvL();
		int killEnemy = plataforma.getKillEnemy();
		int killAsteroid = plataforma.getKillAsteroid();
		int contItems = CantItemShown;
		int cantItemsLeft = CantItemLeft;
		query = "INSERT INTO platform (`Name`, `cantItemLeft`, `KillEnemy`, `KillAsteroid`, `cantItemsShown`, `Nivel`) VALUES ('"+Name+"', "+cantItemsLeft+","+killEnemy+", "+killAsteroid+", "+contItems+","+Lvl+")" ;
		stmt.executeUpdate(query);
	}
	public static void Save(Vector<SmallAsteroid> VSast,Vector<BigAsteroid> VBast,Vector<Enemy> Venemy,Ship Nave,Platform plataforma,Vector<Item> VItems) throws ClassNotFoundException, SQLException//agarramos Items
	{
		
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "") ;//Se conecta a nuestra BD
		Statement stmt = conn.createStatement() ;//Consulta si fue posible conectarse a nuestra base de datos
		String query2,query3,query4,query5,query6,queryPlat;
		String Name = plataforma.getName();
		int DBlvl = 99;
		int Lvl = plataforma.getLevel().getLvL();
		queryPlat = "SELECT NroLvl FROM platform WHERE Nivel = "+Lvl+" AND Name = '"+Name+"'";
		ResultSet nroDEDB =stmt.executeQuery(queryPlat);
		while(nroDEDB.next()){
			DBlvl = nroDEDB.getInt("NroLvl");
		}
		for(int i=0;i<VSast.size();i++){//Guardamos todos los asteroides chicos que se encuentren en la partida en la BD
			float  X = VSast.elementAt(i).getX();
			float Y = VSast.elementAt(i).getY();
			float Xspeed =  VSast.elementAt(i).getXspeed();
			float Yspeed =  VSast.elementAt(i).getYspeed();
			int radio = VSast.elementAt(i).getRadio();
			query2 = "INSERT INTO smallasteroid (`X`, `Y`, `Xspeed`, `Yspeed`, `Platform_NroLvl`, `Radio`) VALUES ("+X+", "+Y+", "+Xspeed+", "+Yspeed+", '"+DBlvl+"',"+radio+")";//Escribimos nuestra consulta
			stmt.executeUpdate(query2);//Mediante este comando enviamos para que se ejecute nuestar consulta en la base de datos.
		}
		for(int i=0;i<VBast.size();i++){//Guardamos los asteroides grandes en la BD
			float  X = VBast.elementAt(i).getX();
			float Y = VBast.elementAt(i).getY();
			float Xspeed =  VBast.elementAt(i).getXspeed();
			float Yspeed =  VBast.elementAt(i).getYspeed();
			int radio = VBast.elementAt(i).getRadio();
			query3 = "INSERT INTO bigasteroid (`X`, `Y`, `Xspeed`, `Yspeed`, `Platform_NroLvl`, `Radio`) VALUES ("+X+", "+Y+", "+Xspeed+", "+Yspeed+", "+DBlvl+","+radio+")" ;
			stmt.executeUpdate(query3) ;
		}
		for(int i=0;i<Venemy.size();i++){//Guardamos los enemigos en la BD
			float  X = Venemy.elementAt(i).getX();
			float Y = Venemy.elementAt(i).getY();
			if( Venemy.elementAt(i).getYspeed()==0){//Si nuestro enemimgo es de lvl1 	
				query4 = "INSERT INTO enemylvl1 (`X`, `Y`, `Platform_NroLvl`) VALUES ("+X+", "+Y+", "+DBlvl+")" ;
			stmt.executeUpdate(query4) ;
			}else if( Venemy.elementAt(i).getYspeed()!=0){//Si nuestro enemimgo es de lvl2
				query4 = "INSERT INTO enemylvl2 (`X`, `Y`, `Platform_NroLvl`) VALUES ("+X+", "+Y+", "+DBlvl+")" ;
				stmt.executeUpdate(query4) ;
			}
		}	
		for(int i=0;i<VItems.size();i++){//Guardamos los items en la BD
			float  X = VItems.elementAt(i).getX();
			float Y = VItems.elementAt(i).getY();
			boolean Accion = VItems.elementAt(i).isAccion();
			int Bonus = VItems.elementAt(i).getBonus();
			query5 = "INSERT INTO item (`X`, `Y`, `Accion`, `Bonus`, `Platform_NroLvl`) VALUES ("+X+", "+Y+", "+Accion+", "+Bonus+", "+DBlvl+")" ;
			stmt.executeUpdate(query5) ;
		}
		float Xnave = Nave.getXcentro();
		float Ynave = Nave.getYcentro();
		int Life = Nave.getLife();
		int Alto = Nave.getAlto();
		query6 = "INSERT INTO ship (`Xcenter`, `Ycenter`, `Lifes`, `Platform_NroLvl`, `Alto`) VALUES ("+Xnave+", "+Ynave+","+Life+", "+DBlvl+", "+Alto+")" ;
		stmt.executeUpdate(query6) ;

	}
}
