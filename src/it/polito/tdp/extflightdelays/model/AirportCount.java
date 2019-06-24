package it.polito.tdp.extflightdelays.model;

public class AirportCount {

	private Airport airport;
	private int count;
	
	public AirportCount(Airport airport, int count) {
		
		this.airport = airport;
		this.count = count;
	}
	@Override
	public String toString() {
		return  airport + " " + count;
	}
	public Airport getAirport() {
		return airport;
	}
	public void setAirport(Airport airport) {
		this.airport = airport;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count += count;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airport == null) ? 0 : airport.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AirportCount other = (AirportCount) obj;
		if (airport == null) {
			if (other.airport != null)
				return false;
		} else if (!airport.equals(other.airport))
			return false;
		return true;
	}
	
	
}
