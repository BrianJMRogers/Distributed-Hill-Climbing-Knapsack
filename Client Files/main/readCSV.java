import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

public class readCSV {
	// Global Variables
	static Vector<Item> v = new Vector<Item>();	

	// main method for testing
	public static void main(String args[]) {
		if (args.length != 0) {
			try {
				System.out.println(read(args[0]).toString());
			} catch (IOException e) {
				e.printStackTrace();
			} // try-catch
		} // if
	} // main

	// Reads in the .csv file
	public static Vector<Item> read(String file) throws IOException {
		// Input file which needs to be parsed
		BufferedReader fileReader = null;
         
		// Delimiter used in CSV file
		final String DELIMITER = ",";
		try {
			String line = "";
			
			// Create the file reader
			fileReader = new BufferedReader(new FileReader(file));
             
			// Read the file line by line
			while ((line = fileReader.readLine()) != null) {
			
				// Get all of the tokens available in the line
				String[] tokens = line.split(DELIMITER);

				// Parse and add
				v.add(new Item(tokens[0], Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2])));
				
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
		return v;
	} // read
} // readCSV
