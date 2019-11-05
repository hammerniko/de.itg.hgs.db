package a01DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDB {

	public static void main(String[] args) {
		
		Connection con = DBConnect.getConnectionTestDB();
		
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Firma");
			
			while(rs.next()){
				System.out.print(rs.getString(1)+"\t");
				System.out.println(rs.getString(2)+"\t");
				
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}

}
