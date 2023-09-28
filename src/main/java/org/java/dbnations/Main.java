package org.java.dbnations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

	public static void main(String[] args) {

		final String url = "jdbc:mysql://localhost:3306/db_nations";
		final String user = "root";
		final String pwd = "";
		
		try (Connection con = DriverManager.getConnection(url, user, pwd)) {
			
			final String sql = " SELECT c.name name, c.country_id id, r.name region, c2.name continent "
					+ " FROM countries c "
					+ " JOIN regions r "
					+ "	ON r.region_id = c.region_id "
					+ " JOIN continents c2 "
					+ "	ON c2.continent_id = r.continent_id "
					+ " ORDER BY c.name ";
			
			try {
				
				PreparedStatement ps = con .prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				
				System.out.println("Nazioni: \n------------------------\n");
				
				while(rs.next()) {
					String name = rs.getString("name");
					int id = rs.getInt("id");
					String region = rs.getString("region");
					String continent = rs.getString("continent");
					
					System.out.println("Nome: " + name);
					System.out.println("id: " + id);
					System.out.println("Regione: " + region);
					System.out.println("Continente: " + continent);
					System.out.println("------------------------\n");
					
				}
				
			} catch (Exception e) {				
				System.err.println("Errore: " + e.getMessage());
			}
			
		} catch(Exception e) {
			System.err.println("Errore di connessione: " + e.getMessage());
		}
		
	}

}
