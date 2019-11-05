package a01DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

class DBConnect {
	private static Connection con;

	/**
	 * Erstellt die Verbindung zu einer Access DB, bei der zuvor ein ODBC
	 * Treiber erstellt wurde.
	 * 
	 * @param strAccessDB
	 * @return Conncetion
	 */
	public static Connection getConnection(){
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			
			//Pfad zur DB z.B.: "c:\\db\\mydb.accdb"
			String pathStat = "/Users/nh/Nextcloud2/Statistik/2018/2018/stat2018.accdb";
			String pathDeputat = "/Users/nh/Nextcloud2/SSDB/deputat.mdb";
			
					
			//URL zur DB
            String url="jdbc:ucanaccess://"+pathDeputat;
			
			con = DriverManager.getConnection(url);
			
			System.out.println("Connected zu DB");
			
		} catch (Exception e) {
			System.out.println("Error");
			JOptionPane.showMessageDialog(null, "Keine Verbindung zur DB", "Connection Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return con;
	}

}
