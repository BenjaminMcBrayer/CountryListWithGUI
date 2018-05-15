package com.countryframe.gc;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CountryFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane; // Container adds scroll bar
	private JTextArea outputArea; // Holds file output
	private JLabel menuLabel;
	private JTextField userAddField;
	private JTextField userDeleteField;
	private JButton listButton;
	private JButton addButton;
	private JButton deleteButton;
	private JButton quitButton;

	CountryFrame() {
		GridBagConstraints layoutConst = null; // GUI component layout

		// Set frame's title
		setTitle("Welcome to the Countries Maintenance Application!");

		menuLabel = new JLabel("Please select an option:");

		listButton = new JButton("See the list of countries");
		listButton.addActionListener(this);
		addButton = new JButton("Add a country");
		addButton.addActionListener(this);
		deleteButton = new JButton("Delete a country");
		deleteButton.addActionListener(this);
		quitButton = new JButton("Exit");
		quitButton.addActionListener(this);

		userAddField = new JTextField(15);
		userAddField.setEditable(true);
		userAddField.setText(null);
		userAddField.addActionListener(this);
		userDeleteField = new JTextField(15);
		userDeleteField.setEditable(true);
		userDeleteField.setText(null);
		userDeleteField.addActionListener(this);

		outputArea = new JTextArea(10, 25);
		scrollPane = new JScrollPane(outputArea);
		outputArea.setEditable(false);

		// Add components using GridBagLayout
		setLayout(new GridBagLayout());

		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 0);
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.gridx = 0;
		layoutConst.gridy = 0;
		add(menuLabel, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 0);
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.gridx = 1;
		layoutConst.gridy = 2;
		add(userAddField, layoutConst);

		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 0);
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.gridx = 2;
		layoutConst.gridy = 2;
		add(userDeleteField, layoutConst);

		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(0, 10, 10, 5);
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.gridx = 0;
		layoutConst.gridy = 1;
		add(listButton, layoutConst);

		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(0, 10, 10, 5);
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.gridx = 1;
		layoutConst.gridy = 3;
		add(addButton, layoutConst);

		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(0, 10, 10, 5);
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.gridx = 2;
		layoutConst.gridy = 3;
		add(deleteButton, layoutConst);

		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(0, 10, 10, 5);
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.gridx = 5;
		layoutConst.gridy = 6;
		add(quitButton, layoutConst);

		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(1, 10, 10, 10);
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.gridx = 0;
		layoutConst.gridy = 2;
		layoutConst.gridheight = 3;
		layoutConst.gridwidth = 1;
		add(scrollPane, layoutConst);

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String addCountry;
		String deleteCountry;
		
		// Get source of event (4 buttons in GUI)
		JButton sourceEvent = (JButton) event.getSource();

		// User pressed the listCountries button.
		if (sourceEvent == listButton) {
			outputArea.setText("");
			printArrayListOfCountries();
			
		} else if (sourceEvent == addButton) {
			addCountry = userAddField.getText();
			Country userCountry = new Country(addCountry);
			CountriesTextFile.writeToFile(userCountry, "resources", "countries.txt");
			userAddField.setText("");

		} else if (sourceEvent == deleteButton) {
			deleteCountry = userDeleteField.getText();
			CountriesTextFile.removeFromFile(deleteCountry, "resources", "countries.txt", "tempCountries");
			userDeleteField.setText("");
			
		} else if (sourceEvent == quitButton) {
			dispose(); // Terminate program
		}
	}

	public void printArrayListOfCountries() {
		ArrayList<Country> countries = CountriesTextFile.readFromFileToArrayList("resources/countries.txt");
		int i = 0;
		if (i < countries.size() && countries.get(i) != null) {
			
			for (Country c : countries) {
				outputArea.append(++i + ". " + c.getCountryName() + "\n");
			} 
		}
		countries.clear();
	}

	// Creates a CountryFrame and makes it visible.
	public static void main(String[] args) {
		CountryFrame myFrame = new CountryFrame();
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.pack();
		myFrame.setVisible(true);
	}
}
