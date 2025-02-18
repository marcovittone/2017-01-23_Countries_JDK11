package it.polito.tdp.borders.model;

public class Country implements Comparable<Country> {

	private int cCode ; // Country Code for the state
	private String stateAbb ; // State Abbreviation (3 capital letters)
	private String stateName ; // Full State name
	private int numStatiConfinanti;
	private int stanziali;
	
	public int getStanziali() {
		return stanziali;
	}

	public void setStanziali(int stanziali) {
		this.stanziali = stanziali;
	}

	/**
	 * Initialize a new {@link Country} object, with full parameters.
	 * 
	 * @param cCode
	 * @param stateAbb
	 * @param stateName
	 */
	public Country(int cCode, String stateAbb, String stateName) {
		super();
		this.cCode = cCode;
		this.stateAbb = stateAbb;
		this.stateName = stateName;
	}

	/**
	 * @return the cCode
	 */
	public int getcCode() {
		return cCode;
	}

	
	public int getNumStatiConfinanti() {
		return numStatiConfinanti;
	}

	public void setNumStatiConfinanti(int numStatiConfinanti) {
		this.numStatiConfinanti = numStatiConfinanti;
	}

	/**
	 * @param cCode the cCode to set
	 */
	public void setcCode(int cCode) {
		this.cCode = cCode;
	}

	/**
	 * @return the stateAbb
	 */
	public String getStateAbb() {
		return stateAbb;
	}

	/**
	 * @param stateAbb the stateAbb to set
	 */
	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cCode;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (cCode != other.cCode)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.stateAbb+" - "+this.stateName;
	}

	@Override
	public int compareTo(Country o) {
		// TODO Auto-generated method stub
		return -this.stanziali+o.getStanziali();
	}
	
	
	
}

