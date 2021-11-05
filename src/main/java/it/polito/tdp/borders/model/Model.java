package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	
	private Graph<Country,DefaultEdge> grafo;
	private BordersDAO dao;
	private List<Country> paesiList;
	private Map<Integer,Country> paesiMap;
	
	public Model() {
		this.dao= new BordersDAO();
		this.paesiMap = new HashMap<>();
		this.paesiList = dao.loadAllCountries(this.paesiMap);
		
	}
	
	
	public List<Country> creaGrafo(int year) {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.dao.loadAllCountriesGivenTheYear(year));
		
		List<Connessione> connessioni = this.dao.loadAllConnectionGivenTheYear(year);
		
		for(Connessione c: connessioni) {
			Graphs.addEdgeWithVertices(this.grafo, this.paesiMap.get(c.getState1no()), this.paesiMap.get(c.getState2no()));
		}
		
		for(Country c:this.grafo.vertexSet()) {
			c.setNumStatiConfinanti(this.grafo.degreeOf(c));
		}
		
		List<Country> list = new ArrayList<Country>(this.grafo.vertexSet());
		ComparatorePaesiPerStatiConfinanti c = new ComparatorePaesiPerStatiConfinanti();
		Collections.sort(list, c);
		
		return list;
		
		//System.out.println("GRAFO CREATO CON "+this.grafo.vertexSet().size()+" VERTICI E "+this.grafo.edgeSet().size()+" ARCHI");
		
	}
	
	

}
