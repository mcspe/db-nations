package org.java.dbnations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		final String url = "jdbc:mysql://localhost:3306/db_nations";
		final String user = "root";
		final String pwd = "";
		
		try (Connection con = DriverManager.getConnection(url, user, pwd)) {
			
			/* Milestone 1
			SELECT c.name name, c.country_id id, r.name region, c2.name continent
			FROM countries c 
			JOIN regions r 
				ON r.region_id = c.region_id 
			JOIN continents c2 
				ON c2.continent_id = r.continent_id 
			ORDER BY c.name  */
			
			/* Milestone 2 */ 
			
//			final String sql = " SELECT c.name name, c.country_id id, r.name region, c2.name continent "
//					+ " FROM countries c "
//					+ " JOIN regions r "
//					+ "	ON r.region_id = c.region_id "
//					+ " JOIN continents c2 "
//					+ "	ON c2.continent_id = r.continent_id "
//					+ " ORDER BY c.name ";
//			
//			try {
//				
//				PreparedStatement ps = con.prepareStatement(sql);
//				ResultSet rs = ps.executeQuery();
//				
//				System.out.println("Nazioni: \n------------------------\n");
//				
//				while(rs.next()) {
//					String name = rs.getString("name");
//					int id = rs.getInt("id");
//					String region = rs.getString("region");
//					String continent = rs.getString("continent");
//					
//					System.out.println("Nome: " + name);
//					System.out.println("id: " + id);
//					System.out.println("Regione: " + region);
//					System.out.println("Continente: " + continent);
//					System.out.println("------------------------\n");
//					
//				}
//				
//			} catch (Exception e) {				
//				System.err.println("Errore: " + e.getMessage());
//			}
			
			
			/* Milestone 3 */
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Inserire il testo da cercare: ");
			String searchInput = "%";
			searchInput += sc.nextLine();
			searchInput += "%";
			
			final String sql = " SELECT c.name name, c.country_id id, r.name region, c2.name continent "
					+ " FROM countries c "
					+ " JOIN regions r "
					+ "	ON r.region_id = c.region_id "
					+ " JOIN continents c2 "
					+ "	ON c2.continent_id = r.continent_id "
					+ " WHERE c.name LIKE ? ";
			
			try {
				
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, searchInput);
				ResultSet rs = ps.executeQuery();
				
				System.out.println("Nazioni: \n------------------------");
				String d1 = String.format("%-5s", "ID");
				String d2 = String.format("%-40s", "NAZIONE");
				String d3 = String.format("%-40s", "REGIONE");
				String d4 = String.format("%s", "CONTINENTE");
				System.out.println(d1 + d2 + d3 + d4);
				
				while(rs.next()) {
					String name = rs.getString("name");
					int id = rs.getInt("id");
					String region = rs.getString("region");
					String continent = rs.getString("continent");
					
					String sd1 = String.format("%-5s", id);
					String sd2 = String.format("%-40s", name);
					String sd3 = String.format("%-40s", region);
					String sd4 = String.format("%s", continent);
					System.out.println(sd1 + sd2 + sd3 + sd4);
					
				}
				
				System.out.println("\n------------------------");
				System.out.print("Seleziona un paese per ID: ");
				int idInput = Integer.valueOf(sc.nextLine());
				
				final String sql1 = " SELECT DISTINCT c.name nation, l.`language` `language` "
						+ " FROM countries c "
						+ " JOIN country_languages cl "
						+ "	ON cl.country_id = c.country_id "
						+ " JOIN languages l "
						+ "	ON l.language_id = cl.language_id "
						+ " WHERE c.country_id = ? ";
				
				final String sql2 = " SELECT cs.`year` `year`, cs.population population, cs.gdp gdp "
						+ " FROM countries c "
						+ " JOIN country_stats cs "
						+ "	ON cs.country_id = c.country_id "
						+ " WHERE c.country_id = ? "
						+ " ORDER BY cs.`year` DESC "
						+ " LIMIT 1 ";
				
				try {
					
					PreparedStatement ps1 = con.prepareStatement(sql1);
					ps1.setInt(1, idInput);
					ResultSet rs1 = ps1.executeQuery();
					
					String nation = null;
					List<String> languages = new ArrayList<>();
					
					while(rs1.next()) {
						nation = rs1.getString("nation");
						languages.add(rs1.getString("language"));
					}
					
					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.setInt(1, idInput);
					ResultSet rs2 = ps2.executeQuery();
					
					int year = 0;
					int population = 0;
					long gdp = 0;
					
					while(rs2.next()) {
						year = rs2.getInt("year");
						population = rs2.getInt("population");
						gdp = rs2.getLong("gdp");
					}
					
					System.out.println("\n------------------------");
					System.out.println("Dettagli della nazione: " + nation);
					System.out.print("Lingue parlate: ");
					String langDisplay = "";
					for (int i = 0; i < languages.size(); i++) {
						langDisplay += languages.get(i);
						if (i != languages.size() - 1) {
							langDisplay += ", ";
						}
					}
					System.out.print(langDisplay);
					System.out.println("Statistiche recenti (" + year + ")");
					System.out.println("Popolazione: " + population);
					System.out.println("GDP: " + gdp);
					
				} catch(Exception e) {
					System.err.println("Errore: " + e.getMessage());
				}
				
				sc.close();
				
			} catch (Exception e) {				
				System.err.println("Errore: " + e.getMessage());
			}
			
		} catch(Exception e) {
			System.err.println("Errore di connessione: " + e.getMessage());
		}
		
	}

}
