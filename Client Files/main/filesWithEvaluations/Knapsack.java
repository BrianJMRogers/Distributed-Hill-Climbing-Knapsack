import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;

/*
 * TODO: Run tests (although it *seems* to be working pretty correctly, this is no excuse for not testing!)
 * TODO: Running in a distributed fashion?
 * TODO: Check against a global solution?
 * TODO: How to beat more competitive implementations?
 * TODO: Generate a search tree
 */

@SuppressWarnings("unchecked")

public class Knapsack{
	// Static variables
	static Vector<History> history = new Vector<History>();

	/**
	 * calculateTotalCost:
	 * Calculates the total cost of the items in the Vector
	 *
	 * @author Jake Ballinger
	 *
	 * @parameter Vector<Item> input: The vector containing items whose total cost we want to know
	 */
	public static double calculateTotalCost(Vector<Item> input) {
		double currentCost = 0;
		if (input.size() == 0) {
			return 0;
		} // if
		for (Item i : input) {
			currentCost += i.getCost();
		} // for

		/* equivalent to:
		   for (int i = 0; i < input.size(); i++) {
		  	currentCost += i.getCost();
		} // for */

		return currentCost;
	} // calculateTotalCost

	/**
	 * calculateTotalBenefit:
	 * Calculates the total benefit of the items in the Vector
	 *
	 * @author Jake Ballinger
	 *
	 * @parameter Vector<Item> input: The vector containing items whose total benefit we want to know
	 */
	public static double calculateTotalBenefit(Vector<Item> input) {
		double currentBenefit = 0;
		if (input.size() == 0) {
			return 0;
		} // if
		for (Item i : input) {
			currentBenefit += i.getBenefit();
		} return currentBenefit;
	 } // calculateTotalBenefit

	/**
	 * containsItem
	 * Checks to see if the vector contains the item in question
	 *
	 * @author Jake Ballinger
	 *
	 * @parameter Vector<Item> v: The vector we want to check
	 * @parameter Item i: The item we want to check
	 */
	public static boolean containsItem(Vector<Item> v, Item i) {
		for (int n = 0; n < v.size(); n++) {
			if (i.name.equals(v.elementAt(n).name))
				return false;
		}
		return true;
	} //containsItem

	/**
	 * knapsack
	 * 	A naïve hill-climbing knapsack solver.
	 *
	 * @author Jake Ballinger
	 *
	 * @parameter double maxCost: The upper bound on the total cost of all items in the solution vector
	 * @parameter Vector<Item> input: The vector containing all of the items
	 * @parameter boolean freebie: Flag to determine whether or not to search for freebies (where cost = 0)
	 */
	public static Vector<Item> knapsack(double maxCost, Vector<Item> given, boolean freebie) {
		// Create a new, empty vector
		Vector<Item> v = new Vector<Item>();

		// Create a copy of given
		Vector<Item> copy = new Vector<Item>(given);

		// Now add to the list until we're about to surpass the maxCost; do this a few times to "pack" the list
		for (int j = 0; j < 4; j++) {
			// Shuffle the list in-place
			Collections.shuffle(copy);
			for (int i = 0; calculateTotalCost(v) < maxCost && i < copy.size(); i++) {
				// Check to make sure we don't surpass maxCost
				if ((calculateTotalCost(v) + copy.elementAt(i).getCost()) > maxCost) {
					// If we do break, we go back to the beginning of the loop where we shuffle
					break;
				} else {
					v.add(copy.elementAt(i));
					// Now remove that element from copy
					copy.removeElementAt(i);
				} // if-else
			} // for
		} // for

		if (freebie) {
			// Now search and add the freebies
			for (int i = 0; i < copy.size(); i++) {
				if (copy.elementAt(i).freebie) {
					v.add(copy.elementAt(i));
					copy.removeElementAt(i);
				} // if
			} // for
		} // if

		// Print the current state of the vector just to check what's in it
		printSolution(v);

		// Now call the recursive hill-climbing search
		return hillClimbingSearch(v, copy, maxCost, v.size());
	} // knapsack

	/**
	 * goodNeighbors:
	 * Checks the neighborhood for the most optimal neighbor according to a Greedy heuristic
	 *

	 * @author Jake Ballinger
	 *
	 * @parameter Vector<Node> neighborhood: The vector of Nodes where each node is a neighbor
	 */
	public static Node goodNeighbors(Vector<Node> neighborhood) {
		// Need to return the node from the neighborhood with the lowest ratio
		Node favorite;
		if (neighborhood.size() == 0) {
			return null;
		} else {
			favorite = neighborhood.get(0);
		}

		// Rip through the vector of nodes and find the best node in the neighborhood
		for (int i = 1; i < neighborhood.size(); i++) {
			if (favorite.r > neighborhood.get(i).r) favorite = neighborhood.get(i);
		} // for

		return favorite;
	} // goodNeighbors

	/**
	 * hillClimbingSearch:
	 * A recursive, greedy hill-climbing solution to knapsack.
	 *
	 * @author Jake Ballinger
	 *
	 * @parameter Vector<Item> v: The current test vector
	 * @parameter Vector<Item> copy: The copy vector with all elements NOT in v
	 * @parameter double maxCost: The maximum allowable cost
	 * @parameter int counter: The recursive counter
	 */
	public static Vector<Item> hillClimbingSearch(Vector<Item> v, Vector<Item> copy, double maxCost, int counter) {
		// Check to see if our recursive counter has hit zero
		if (counter == 0) {
			printSolution(v);
			System.out.println("Exiting because we reached our counted bound!");
			return v;
		} // if

		// Create the neighborhood and search it for the best neighbor
		Node favorite = neighbors(v, copy, maxCost);

		// Decrement the counter
		counter--;

		// Save a bit of time and don't call if we're already at the base case
		if (favorite == null) {
			printSolution(v);
			return v;
		} else if (counter == 0) {
			printSolution(favorite.v);
			return favorite.v;
		} // if-else if

		// Otherwise, print the current state and recurse
		return hillClimbingSearch(favorite.v, copy, maxCost, counter);
	} // hillClimbingSearch

	/**
	 * isGoodSubstitution:
	 * Checks the cost/benefit ratios of two items to see if we should replace the first with the second
	 *
	 * @author Jake Ballinger
	 *
	 * @parameter Item current: The item currently in the vector
	 * @parameter Item prospective: The candidate replacement item
	 * @parameter Vector v: The current vector
	 * @parameter double maxCost: The maximum allowable cost
	 */
	public static boolean isGoodSubstitution(Item current, Item prospective, double total, double maxCost) {
		//System.out.println(calculateTotalCost(v) + ", " + current.getCost() + ", " + prospective.getCost() + ", " + maxCost);
		if (current.getRatio() > prospective.getRatio() && (total - current.getCost() + prospective.getCost()) <= maxCost) {
			return true;
		} else {
			return false;
		} // if-else
	} // isGoodSubstitution

	/**
	 * neighbors(Node<Vector<Item>> current):
	 * Gives all of the optimal swap values for a given item in the Node.
	 *
	 * @author Jake Ballinger
	 *
	 * @parameter Node<Vector<Item>> current: The current node
	 * @parameter Vector<Item> copy: The copied vector we made in the knapsack method
	 * @parameter double maxCost: The maximum allowable cost
	 */
	public static Node neighbors(Vector<Item> v, Vector<Item> copy, double maxCost) {
		// Go create the search space of all of the possible current substitutions
		Vector<Node> neighborhood = new Vector<Node>();
		
		// Greedy heuristic: We want to swap out only the single worst element! So let's sort so that the worst element is always in last place.
		Quick.sort(v);
		
		// Nondeterminism: shuffle the copy to randomize our elements
		Collections.shuffle(copy);
		// Upper bound of 1000 is for time contraints; since this implements nondeterminism and is meant to be distributed 
		// 	there shouldn't be any bad side effects from constricting the quantity of potential neighbors.
		for (int j = 0; j < 1000 && j < copy.size(); j++) {
			// Check to see if the element in copy is more optimal then the current element; if it is, make a new node with them switched
			// Also check to make sure that the element in copy isn't already in the current vector: we want to avoid duplicates!
			if (isGoodSubstitution(v.get(v.size()-1), copy.get(j), calculateTotalCost(v), maxCost) && containsItem(v, copy.get(j))) {
				// Create a new node containing a new vector of Items with the proper elements swapped, as indicated above; add this node to the neighborhood
				Vector foo = new Vector<Item>(v);
				foo.set(v.size()-1, copy.get(j));
				neighborhood.add(new Node(foo, v.elementAt(v.size()-1), copy.elementAt(j)));
			} // if
		} // for
		return goodNeighbors(neighborhood);
	} // neighbors

	/**
	 * printSolution:
	 * Prints a string representation of the solution vector
	 *
	 * @author Jake Ballinger
	 *
	 * @parameter Node<Vector<Item>> solution: The solution vector
	 */
	public static void printSolution(Vector<Item> solution) {
		System.out.println("Current State");
		for (Item v : solution) {
			System.out.println(v.getName() + ", (" + v.getCost() + ", " + v.getBenefit() + ") \tRatio: " + v.getRatio());
		}
		System.out.println("(Cost, Benefit) = (" + calculateTotalCost(solution) + ", " + calculateTotalBenefit(solution) + ")");
		System.out.println("Cost/Benefit Ratio (LIB): " + calculateTotalCost(solution)/calculateTotalBenefit(solution) + "\n");
	} // printSolution

	public static void printSolution(Vector<Item> solution, History history) {
		System.out.println("Current State");
		for (Item v : solution) {
			System.out.println(v.getName() + ", (" + v.getCost() + ", " + v.getBenefit() + ") \tRatio: " + v.getRatio());
		} // for
		System.out.println("(Cost, Benefit) = (" + calculateTotalCost(solution) + ", " + calculateTotalBenefit(solution) + ")");
		System.out.println("Cost/Benefit Ratio (LIB): " + calculateTotalCost(solution)/calculateTotalBenefit(solution));
		System.out.println(history + "\n");
	} // printSolution

	/**
	 * main:
	 * Reads in from a .csv file and runs the Knapsack computation
	 *
	 * @author Jake Ballinger
	 */
	public static void main(String cheese[]) {
		Vector v = new Vector(); // the vector that holds everything from the .csv file we read in
		double maxCost;
		boolean freebie;

		if (cheese.length == 0) {
			System.out.println("Error! You need to supply a file name. Exiting.");
			System.exit(0);
		} else if (cheese.length == 1) {
			System.out.println("Error! You need to supply both a file name and the maximum cost. Exiting.");
			System.exit(0);
		} // if-else if

		// Try to read from the file
		try {
			readCSV reader = new readCSV();
			v = new Vector<Item>(reader.read(cheese[0]));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error! We couldn't read from the file. Exiting.");
			System.exit(0);
		} // try-catch

		// Remember to catch the max cost
		maxCost = Double.parseDouble(cheese[1]);

		// Check for freebies?
		try {
			if (cheese[2] != null) {
				freebie = Boolean.parseBoolean(cheese[2]);
			} else {
				freebie = false;
			} // if-else
		} catch (ArrayIndexOutOfBoundsException e) {
			freebie = false;
		} // try-catch

		// Now let the user know we're executing the knapsack code...
		System.out.println("\nExecuting the knapsack code. Please be patient...\n");

		// Now call in all the good stuff
		printSolution(knapsack(maxCost, v, freebie));
		System.out.println("Solution found!");
	} // main

	public static String getFullSolution(Vector<Item> solution) {
		String fullSolution = " ";
		System.out.println("Current State");
		for (Item v : solution) {
			fullSolution += v.getName() + ", "; //gives solution
		} // for
		fullSolution = fullSolution.substring(1, fullSolution.length()-2);
		fullSolution = fullSolution + " - " +Double.toString(getSolution(solution));
		fullSolution += ("\n(Cost, Benefit) = (" + calculateTotalCost(solution) + ", " + calculateTotalBenefit(solution) + ")\n");
		fullSolution += ("Cost/Benefit Ratio (LIB): " + calculateTotalCost(solution)/calculateTotalBenefit(solution) + "\n");
		return fullSolution;
	} // printSolution

	public static double getSolution(Vector<Item> solution) {
		return(calculateTotalCost(solution)/calculateTotalBenefit(solution));
	} // printSolution

	public static String mainCopy(String c, double m, int iter) {
		Vector v = new Vector(); // the vector that holds everything from the .csv file we read in
		String cheese = c; // location of thre data file
		double maxCost = m; 
		int iterations = iter; //number of times it will run
		Vector solution = null; // will contain each solution
		double currentCostBenefit = 0;
		double min = 100;
		String minFullOutputString = ""; 
		String result = "";
		double mean = 0;
		double standardDev = 0;
		csvDataParser dataReader = new csvDataParser();


		// Try to read from the file
		try {
			readCSV reader = new readCSV();
		    v = new Vector<Item>(reader.read(cheese));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error! We couldn't read from the file. Exiting.");
		    System.exit(0);
		} // try-catch
	  
		// Now call in all the good stuff
		for (int i = 0; i < iterations; i++){
		    solution = new Vector<Item>(knapsack(maxCost, v, false));
			currentCostBenefit = getSolution(solution);
			if (min > currentCostBenefit){
				min = currentCostBenefit;
				minFullOutputString = getFullSolution(solution);
			} // if
	    	try {
	      		BufferedWriter out = new
	    		BufferedWriter(new FileWriter("output.csv", true));
	    		out.write(currentCostBenefit + ",");
	    		out.close();
	    	} catch(IOException e) {
	    	}
	  	} // for

		try {
	    	mean = dataReader.getMean("output.csv");
	    	standardDev = dataReader.getStandardDeviation("output.csv", mean);
	  	} catch (Exception e){
	    	e.printStackTrace();
	  	}
		//String.format( "%.2f", dub )
	  	result = "mean:, " + Double.toString(mean) +", standardDev:, "+ Double.toString(standardDev) + ", " + "$ " + minFullOutputString;

		// Let the user know we found a solution
		//System.out.println("MinFullOutputString: " + minFullOutputString);
		System.out.println("Solution found!");

		return result;
	} // mainCopy
} // Knapsack
