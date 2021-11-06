package it.polito.tdp.borders.model;

public class Event implements Comparable<Event>{
	
	private Country country;
	private int ImmigratiInArrivo;
	private int T;
	
	
	public Event(Country country,int ImmigratiInArrivo,int T) {
		this.country = country;
		this.ImmigratiInArrivo=ImmigratiInArrivo;
		this.T = T;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public int getT() {
		return T;
	}
	public void setT(int t) {
		T = t;
	}
	
	
	public int getImmigratiInArrivo() {
		return ImmigratiInArrivo;
	}
	public void setImmigratiInArrivo(int immigratiInArrivo) {
		ImmigratiInArrivo = immigratiInArrivo;
	}
	@Override
	public int compareTo(Event o) {
		return this.T-o.getT();
	}
	
	

}
