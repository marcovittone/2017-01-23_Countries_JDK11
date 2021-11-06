package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	//punto 1
	private Graph<Country,DefaultEdge> grafo;
	private BordersDAO dao;
	private List<Country> paesiList;
	private Map<Integer,Country> paesiMap;
	
	
	//punto 2
	private int nonStanzialiTotali;
	private int T;
	private PriorityQueue<Event> eventi;
	
	public Model() {
		this.dao= new BordersDAO();
		this.paesiMap = new HashMap<>();
		this.paesiList = dao.loadAllCountries(this.paesiMap);
		
	}
	
	
	public List<Country> creaGrafo(int year) {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
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
	
	public void simulate(Country c) {
		
		this.nonStanzialiTotali=1000;
		this.T=1;
		this.eventi = new PriorityQueue<>();
		
		
		this.eventi.add(new Event(c,this.nonStanzialiTotali,T));
		
		while(this.nonStanzialiTotali>0)
			this.processEvent(this.eventi.poll());
			
	}
	
	private void processEvent(Event e) {
		
		Country c = e.getCountry();
		
		int migrantiInArrivo = e.getImmigratiInArrivo();
		int devonoRipartire = (int)(migrantiInArrivo/2); //questi sono i non stanziali presenti nello stato
		
		//la meta per eccesso rimane e diminuiscono i non stanziali totali
		int stanziati = migrantiInArrivo-devonoRipartire;
		this.nonStanzialiTotali-= stanziati;
		c.setStanziali(c.getStanziali()+stanziati);
		
		//guardo con quanti paesi confino se sono minori dei migranti da distribuire me li tengo tutti
		List<Country> paesi = Graphs.neighborListOf(this.grafo, c);
		
		if(devonoRipartire<paesi.size()){
			
			c.setStanziali(c.getStanziali()+devonoRipartire);
			this.nonStanzialiTotali-=devonoRipartire;
			return;
		}
		
		//inoltre dalla ripartizione se avanza qualcosa lo salvo lo stesso (aumento stanziali diminuzione non stanziali)
		int divisione = (int)(devonoRipartire/(paesi.size()));
		c.setStanziali(c.getStanziali()+(devonoRipartire%(paesi.size())));
		this.nonStanzialiTotali-=devonoRipartire%(paesi.size());
		
		
		for(Country cc: paesi)
			this.eventi.add(new Event(cc,divisione,this.T++));
		
	}


	public int getT() {
		return T;
	}
	
	public List<Country> getPaesiAccoglienti(){
		
		List<Country> paesi = new ArrayList<Country>();
		
		for(Country c:this.grafo.vertexSet()) {
			if(c.getStanziali()>0)
				paesi.add(c);
		}
		
		Collections.sort(paesi);
		return paesi;
		
	}
	
	

}
