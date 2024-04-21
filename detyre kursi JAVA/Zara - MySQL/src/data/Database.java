package data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import panels.PlayerTableRenderer;

public class Database {

	private static Connection conn = null;//nje lidhje me nje databaze
	private static Statement stmt = null;//objekt per te ekzekutuar statements ne MySQL
	private static ResultSet rs;//nje tabele te dhenash qe merren si rezultat nga nje query ne MySQL
	
	public static boolean getConnection(String userName, String password, String serverName, int portNumber) {
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
        try {//krijon lidhje me MySQL, databazen playerDatabase dhe tabelat playerTable (qe do mbaj te dhenat e lojetareve) dhe scoreTable (qe mban numrin e lojerave dhe piket qe kane marre)
			conn = DriverManager.getConnection("jdbc:mysql://"+ serverName + ":" + portNumber +"/", connectionProps);
	        stmt = conn.createStatement();
	        stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS playerDatabase");
			conn = DriverManager.getConnection("jdbc:mysql://"+ serverName + ":" + portNumber + "/ playerDatabase", connectionProps);
		    stmt = conn.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS playerTable (firstName varchar(50) NOT NULL, lastName varchar(50) NOT NULL, age INTEGER NOT NULL, PRIMARY KEY (firstName,lastName));");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS scoreTable (firstName varchar(50) NOT NULL, lastName varchar(50) NOT NULL, playCount INTEGER NOT NULL, score INTEGER NOT NULL)");
		} catch (SQLException e) {}
        if(conn == null)
        	return false;
        return true;
	}

	public static void createPlayer(String fName, String lName, String age) {//krijon nje lojetar te ri ne databaze
		try{
			stmt.executeUpdate("INSERT INTO playerTable VALUES ('"+fName+"','"+lName+"', '"+age+"') ON DUPLICATE KEY UPDATE age ="+age+";");
		} catch (SQLException | NullPointerException e) {}//NullPointerException, ne rast se nuk krijohet dot lidhje me MySQL
	}
	
	public static int getCurrentPlayCount(String fName, String lName) {//kthen numrin e lojes qe ndodhet lojetari qe po luan ne moment
		try {
			rs = stmt.executeQuery("SELECT * FROM scoreTable WHERE (firstName = '"+fName+"' AND lastName = '"+lName+"') ORDER BY playCount DESC LIMIT 1;");
			if(rs.next()) {
				return rs.getInt("playCount")+1;
			}
			return 1;
		} catch (SQLException | NullPointerException e) {return 1;}
	}
	
	public static void highestScore() {//percakton ne rs rreshtin ne scoreTable me piket me te larta
		try {
			rs = stmt.executeQuery("SELECT * FROM scoreTable WHERE score=(SELECT MAX(score) FROM scoreTable);");
			rs.next();
		} catch (SQLException | NullPointerException e) {}
	}
	
	public static void lowestScore() {//percakton ne rs rreshtin ne scoreTable me piket me te uleta
		try {
			rs = stmt.executeQuery("SELECT * FROM scoreTable WHERE score=(SELECT MIN(score) FROM scoreTable);");
			rs.next();
		} catch (SQLException | NullPointerException e) {}
	}
	
	public static Object[] calculateHighestLowest() {//kthen nje array me emrin, mbiemerin, numrin e lojes dhe piket qe ka marre lojetari me pike me te larta/uleta ne databaze
		try {
		highestScore();
		String maxFName = rs.getString("firstName");
		String maxLName = rs.getString("lastName");
		int highestPlayCount = rs.getInt("playCount");
		int max = rs.getInt("score");
		
		lowestScore();
		String minFName = rs.getString("firstName");
		String minLName = rs.getString("lastName");
		int lowestPlayCount = rs.getInt("playCount");
		int min = rs.getInt("score");

		return new Object[] {maxFName, maxLName, highestPlayCount, max, minFName, minLName, lowestPlayCount, min};
		} catch (SQLException | NullPointerException e) {return null;}
	}
	
	public static void writeDatabaseData() {//kalon te dhenat e lojetareve qe po luajne ne moment ne databaze ne fund te lojes
		for(int i=0;i<PlayerGame.currentPlayers.size();i++) {
			String fName = PlayerGame.currentPlayers.get(i).getFirstName();
			String lName = PlayerGame.currentPlayers.get(i).getLastName();
			int score = PlayerTableRenderer.total.get(i);
			int playCount = PlayerGame.currentPlayers.get(i).getPlayCount();
			try {
				stmt.executeUpdate("INSERT INTO scoreTable VALUES ('"+fName+"','"+lName+"','"+playCount+"','"+score+"');");
			} catch (SQLException | NullPointerException e) {}
		}
	}
}