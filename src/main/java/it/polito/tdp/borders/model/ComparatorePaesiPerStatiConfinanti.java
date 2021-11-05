package it.polito.tdp.borders.model;

import java.util.Comparator;

public class ComparatorePaesiPerStatiConfinanti implements Comparator<Country>{

	@Override
	public int compare(Country o1, Country o2) {
		return o2.getNumStatiConfinanti()-o1.getNumStatiConfinanti();
	}

}
