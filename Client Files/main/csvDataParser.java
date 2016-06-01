import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;
import java.util.Scanner;

//this class provides a function which can iterate through a csv file
//containing our data output and will calculate the mean of the data
//TODO: implement a getStandardDeviation function

public class csvDataParser {
	// Global Variables
	//static Vector<Item> v = new Vector<Item>();

	public static void main(String args[]) {
		try {
			double mean = getMean("output.csv");
			System.out.println("The average is: " + mean);
			double standardDev = getStandardDeviation("output.csv",mean);
			System.out.println("The standard deviation is: " + standardDev);
		} catch (Exception e){
			e.printStackTrace();
		}
	} // main

	// Reads in the .csv file
	public static double getMean(String file) throws IOException {
		// Input file which needs to be parsed
		BufferedReader fileReader = null;

		// Delimiter used in CSV file
		final String DELIMITER = ",";
		double data = 0;
		double counter = 0;
		try {
			String line = "";

			// Create the file reader
			fileReader = new BufferedReader(new FileReader(file));

			// Read the file line by line
			while ((line = fileReader.readLine()) != null) {

				// Get all of the tokens available in the line
				String[] tokens = line.split(DELIMITER);

				for (int i = 0; i < tokens.length; i++){
					if (tokens[i].equals("NaN")){
						//darn
					} else {
						data += Double.parseDouble(tokens[i]);
						counter++;
					}///else
				}//for
            } // while
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} // try-catch
		} // try-catch-finally
		return data/counter;
	} // getMean

	public static double getStandardDeviation(String file, double m) throws IOException {
		// Input file which needs to be parsed
		BufferedReader fileReader = null;
		double mean = m;

		// Delimiter used in CSV file
		final String DELIMITER = ",";
		double data = 0;
		double result = 0;
		double counter = 0;
		try {
			String line = "";

			// Create the file reader
			fileReader = new BufferedReader(new FileReader(file));

			// Read the file line by line
			while ((line = fileReader.readLine()) != null) {

				// Get all of the tokens available in the line
				String[] tokens = line.split(DELIMITER);

				for (int i = 0; i < tokens.length; i++){
					if (tokens[i].equals("NaN")){
						//darn
					} else {
						data = Double.parseDouble(tokens[i]);
						data = data - mean;
						data = data*data;
						result += data;
						counter++;
					}///else
				}//for
            } // while
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} // try-catch
		} // try-catch-finally
		return Math.sqrt((result/counter));
	} // read
} // readCSV
