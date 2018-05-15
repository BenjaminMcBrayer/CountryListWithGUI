package com.countryframe.gc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
/**
 * @author BenjaminMcBrayer
 * @version 1.0
 */
public class CountriesTextFile {

	public static void main(String[] args) {
		CountriesTextFile.createDirectory("resources");

		CountriesTextFile.createFile("resources", "countries.txt");
		CountriesTextFile.createFile("resources", "tempCountries");
	}

	public static void createDirectory(String path) {
		Path dirPath = Paths.get(path);

		if (Files.notExists(dirPath)) {
			try {
				Files.createDirectories(dirPath);
			} catch (IOException e) {
				System.out.println("Something went wrong!");
			}
		}
	}

	public static void createFile(String dir, String fileName) {
		Path filePath = Paths.get(dir, fileName);

		if (Files.notExists(filePath)) {
			try {
				Files.createFile(filePath);
				System.out.println("Your file was created successfully.");
			} catch (IOException e) {
				System.out.println("Something went wrong! The file was not created.");
			}
		}
	}

	// Reads a list of countries from a file
	public static void readFromFile(String dir, String fileName) {
		Path readFile = Paths.get(dir, fileName);
		File file = readFile.toFile(); // Convert to a file object.

		try {
			FileReader fr = new FileReader(file);

			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();

			while (line != null) {
				System.out.println(line);
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Something went wrong.");
		}

	}

	// Adds a country to a file.
	public static void writeToFile(Country country, String dir, String fileName) {
		Path writeFile = Paths.get(dir, fileName);
		File file = writeFile.toFile();

		try {
			PrintWriter outW = new PrintWriter(new FileOutputStream(file, true));
			outW.println(country);
			outW.close(); // Flush data and close stream.

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}

	}

	// Deletes a country from a file.
	public static void removeFromFile(String lineToRemove, String dir, String originalFileName, String tempFileName) {
		Path removeLineFromOriginalFile = Paths.get(dir, originalFileName);
		File file = removeLineFromOriginalFile.toFile();
		Path writeFile = Paths.get(dir, tempFileName);
		File tempFile = writeFile.toFile();

		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			PrintWriter pw = new PrintWriter(new FileOutputStream(tempFile));

			String line = null;

			while ((line = br.readLine()) != null) {
				if (!line.endsWith(lineToRemove)) {
					pw.println(line);
				}
			}

			pw.close();
			br.close();

			// Delete original file.
			if (!file.delete()) {
				System.out.println("Could not delete file.");
				return;
			}

			// Rename new file.
			if (!tempFile.renameTo(file)) {
				System.out.println("Could not rename file.");
			}

		} catch (IOException e) {
			System.out.println("No need to panic but something's not right here.");
		}
	}

	public static void removeFromFileMoreEfficiently(String lineToRemove, String dir, String originalFileName,
			String tempFileName) {
		Path removeLineFromOriginalFile = Paths.get(dir, originalFileName);
		File file = removeLineFromOriginalFile.toFile();
		Path writeFile = Paths.get(dir, tempFileName);
		File tempFile = writeFile.toFile();

		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(tempFile, true));

			BufferedReader br2 = new BufferedReader(new FileReader(tempFile));

			lineToRemove = br2.readLine();

			HashSet<String> hs = new HashSet<String>();

			while (lineToRemove != null) {
				hs.add(lineToRemove);
				lineToRemove = br2.readLine();
			}

			BufferedReader br1 = new BufferedReader(new FileReader(file));

			String line = br1.readLine();

			while (line != null) {
				if (!hs.contains(line)) {
					pw.println(line);
				}

				line = br1.readLine();
			}

			pw.flush();

			br1.close();
			br2.close();
			pw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<Country> readFromFileToArrayList(String filePath) {
		ArrayList<Country> countries = new ArrayList<>();
		Path readFile = Paths.get(filePath);

		File file = readFile.toFile();

		try {
			FileReader fr = new FileReader(file);
			BufferedReader reader = new BufferedReader(fr);

			String line = reader.readLine();
			String[] temp = new String[1];
			while (line != null) {
				temp = line.split(",");
				Country c = new Country(temp[0]);
				countries.add(c);

				line = reader.readLine();
			}
			reader.close();

		} catch (IOException e) {
			System.out.println("Something went wrong!");
		}
		return countries;
	}

	public static void validateCountry(String userInput, ArrayList<Country> countries) {
		countries = CountriesTextFile.readFromFileToArrayList("resources/countries.txt");
		for (Country c : countries) {
			if (c.getCountryName().equalsIgnoreCase(userInput)) {
				System.out.println("\nMENU\n1 - Add a country\n2 - Delete a country");
			}
		}
	}
	
	public static boolean validateCountry2(String userInput, ArrayList<Country> countries) {
		countries = CountriesTextFile.readFromFileToArrayList("resources/countries.txt");
		HashSet<String> hs = new HashSet<String>();
		
		for (Country c : countries) {
			hs.add(c.getCountryName());
		}
		if (hs.contains(userInput)) {
			System.out.println(
					"\nOPTIONS\n1 - View the current menu\n2 - Add an item to the menu\n3 - Remove an item from the menu");
			return true;
		} else {
			System.out.println("ID not recognized. Please try again.");
			return false;
		}
	}
}
