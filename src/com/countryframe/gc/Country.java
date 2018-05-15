package com.countryframe.gc;

/**
 * @author BenjaminMcBrayer
 * @version 1.0
 */
public class Country {
	private String countryName;

	public Country(String countryName) {
		super();
		this.countryName = countryName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	@Override
	public String toString() {
		return countryName;
	}
}
