package com.countryframe.gc;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author BenjaminMcBrayer
 * @version 1.0
 *
 */

public class CountriesApp {

	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		ArrayList<Country> countries = new ArrayList<>();
		int userNum;

		System.out.println("Welcome to the Countries Maintenance Application!");

		 String userInput = Validator.getString(scnr, "Please enter the name of the country: ");
		 CountriesTextFile.validateCountry2(userInput, countries);

		do {
			System.out.println(
					"\nMENU\n1 - See the list of countries\n2 - Add a country\n3 - Remove a country\n4 - Exit");

			userNum = Validator.getInt(scnr, "\nPlease enter menu number: ", 1, 4);

			maintainCountries(scnr, userNum);

		} while (userNum == 1 || userNum == 2 || userNum == 3);

		scnr.close();
	}

	public static void maintainCountries(Scanner scnr, int userNum) {
		Country userCountry;
		switch (userNum) {

		case 1:
			CountriesTextFile.readFromFile("resources", "countries.txt");
			break;

		case 2:
			String userInput = Validator.getString(scnr, "Please enter a country: ");
			userCountry = new Country(userInput);
			CountriesTextFile.writeToFile(userCountry, "resources", "countries.txt");
			break;

		case 3:
			userInput = Validator.getString(scnr, "Please enter the name of the country you wish to delete: ");
			userCountry = new Country(userInput);
			CountriesTextFile.writeToFile(userCountry, "resources", "tempCountries");
			CountriesTextFile.removeFromFile(userInput, "resources", "countries.txt", "tempCountries");
			break;

		case 4:
			System.out.println("Buh-Buy!");
			break;
		}
	}

}
