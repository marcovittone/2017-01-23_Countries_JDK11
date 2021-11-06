package it.polito.tdp.borders.db;

import it.polito.tdp.borders.model.Connessione;
import it.polito.tdp.borders.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BordersDAO {
	
	public List<Country> loadAllCountries(Map<Integer,Country> mappa) {
		
		String sql = 
				"SELECT ccode,StateAbb,StateNme " +
				"FROM country " +
				"ORDER BY StateAbb " ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				Country c = new Country(
						rs.getInt("ccode"),
						rs.getString("StateAbb"), 
						rs.getString("StateNme")) ;
				
				list.add(c) ;
				mappa.put(c.getcCode(), c);
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	
	
public List<Connessione> loadAllConnectionGivenTheYear(int year) {
	
	String sql = "SELECT state1no,state2no "
			+ "FROM contiguity "
			+ "WHERE YEAR <= ? AND conttype = 1 AND state1no<state2no" ;

	try {
		Connection conn = DBConnect.getConnection() ;

		PreparedStatement st = conn.prepareStatement(sql) ;
		
		st.setInt(1, year);
		
		ResultSet rs = st.executeQuery() ;
		
		List<Connessione> list = new LinkedList<Connessione>() ;
		
		while( rs.next() ) {
			
			Connessione c = new Connessione(rs.getInt(1),rs.getInt(2)) ;
			list.add(c);
		}
		
		conn.close() ;
		
		return list ;
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return null ;
}
	
	
	
	
}

