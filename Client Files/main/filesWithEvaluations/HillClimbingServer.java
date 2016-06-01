import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.ServerSocket;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.net.Socket;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.LinkedList;
@SuppressWarnings({"deprecation","unchecked"})



public class HillClimbingServer {

	// This example is from: http://examples.javacodegeeks.com/core-java/net/multicastsocket-net/java-net-multicastsocket-example/

	private static String INET_ADDR = "";// "224.0.0.35"; // specify what IP to be multicasting on
	private static int PORT = 0; // specify port number

	public static void main(String[] args) throws UnknownHostException, InterruptedException {
		String msg = "";
		ServerSocket MyService = null;
		Socket serviceSocket = null;
		DataInputStream input = null;
		BufferedReader fileReader = null;
		BufferedWriter out = null;
		BufferedWriter best = null;
		BufferedWriter ratios = null;
		String in = null;
		Vector<String> tokens = new Vector<String>();
		String myAddress = "";
    		int numNodes = 0;
    		int counter = 0;
    		float averageMean = 0;
    		double averageStandardDev = 0;
		int determineAverages = 0;
		double startTime = 0, endTime = 0;
		//float[] bestSolutions = null;
		LinkedList bestSolutionsNums = new LinkedList();
		LinkedList bestSolutionsItems = new LinkedList();


		startTime = System.currentTimeMillis();
	    // Open a new DatagramSocket, which will be used to send the data.
    	try (DatagramSocket serverSocket = new DatagramSocket()) {
    		try {
			//System.out.println(args.length);
		        myAddress = args[0];
        		INET_ADDR = args[1];
        		PORT = Integer.parseInt(args[2]);
        		numNodes = Integer.parseInt(args[3]);
        		msg = args[0] + "," + args[4]+ ","+ args[5];
      		} catch (Exception e){
        		System.out.println("Necessary arguments: 0) This computer address (e.g.: aldenv100), 1) INET_ADDR, 2) "
        		+ "port number, 3) number of distributed nodes, 4) which requirements file, 5) max size of bag");
        		e.printStackTrace();
        		System.exit(0);
      		} // try-catch
	      // Get the address that we are going to connect to.
    	  InetAddress addr = InetAddress.getByName(INET_ADDR);

    	  // Create a packet that will contain the data
    	  // (in the form of bytes) and send it.
    	  DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
    	  msg.getBytes().length, addr, PORT);
    	  serverSocket.send(msgPacket);

    	  System.out.println("\nSent a packet with this message: " + msg + "\n");
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} // try-catch

		/* Receiving the data from the clients via direct socket connection */
		// declare sockets

		try {
	    	// open socket for the server
	        MyService = new ServerSocket(PORT + 1);
		} catch (IOException e) {
			System.out.println(e);
        	e.printStackTrace();
		} // try-catch

	while(true) {
    	    System.out.print("Waiting for responses...");
    	    try {
    	    	// open socket to listen for requests
    	    	serviceSocket = MyService.accept();
    	    	// get an input stream from the socket
    	    	input = new DataInputStream(serviceSocket.getInputStream());
    	    	in = input.readLine();
    	    	//System.out.print("Received: " +  + "\n");
    	    	char[] parts = in.toCharArray();
    	    	String part1 = null, part2 = null;
    	    	for (int i = 0; i < parts.length; i++) {
    	    		if (parts[i] == '$') {
    	    			part1 = in.substring(0, i-1);
				part2 = in.substring(i+1, in.length()-1);
    	  		} // if
    	    	} // for
    	    	
		//System.out.println(part1 + " ~ " + part2);


		char[] part2Chars = part2.toCharArray();
		String part2A = null, part2B = null;
		for (int i = 0; i < part2Chars.length; i++) {
    	    		if (part2Chars[i] == '-') {
    	    			part2A = part2.substring(1, i-1);
				part2B = part2.substring(i+2, part2.length()-1);
    	  		} // if
    	    	} // for
		//System.out.println("part2: " + part2);


		//System.out.println("\n part2A" + part2A);
		//System.out.println("\n part2B" + part2B);
		bestSolutionsItems.add(part2A);
		bestSolutionsNums.add(part2B);
		//System.out.println("part2B: " + part2B + ". size: " + bestSolutionsNums.size());
		counter++;
		System.out.println("Recieved " + counter + " of " + numNodes + " responses");

		//bestSolutions = new float [numNodes];
		//bestSolutions[counter] = Float.parseFloat(part2B);
		//System.out.println("These are equal: " + part2B + ":" + bestSolutions[counter]);
		

		

    	    	//System.out.println("Part1: " + part1);
    	    	//System.out.println("Part2: " + part2);
/*
    	    	// Error Handling
    	    	if (part1 == null || part2 == null) {
    	    		System.out.println("Something went wrong and the output could not be parsed.");
    	    		System.exit(0);
    	    	} // if

    	    	try {
    	    		out = new BufferedWriter(new FileWriter("received.csv", true));
    	    		out.write(part1 + "\n"); // part1 is the statistics (StdDev, Mean) from each node
    	    		out.close();
			best = new BufferedWriter(new FileWriter("bestSolutions.txt", true));
    	    		best.write(part2 + "\n"); // part2 is the best knapsack solution from each node
    	        	best.close();
    	        	//ratios = new BufferedWriter(new FileWriter("ratios.txt", true));
    	        	//ratios.write(part3 + "\n");
    	        	//ratios.close();
    	        	
    	    	} catch(IOException e) {
    	      	} // try-catch
*/
    	    } catch (IOException e) {
    	        System.out.println(e);
    	        e.printStackTrace();
    	    } // catch
    	    if (counter == numNodes) {
    	    	break;
    	    } // if
	} // while
		
		
/*		System.out.println("\n\nPrinting best Solutions Nums");
		for (int i = 0; i < bestSolutionsNums.size(); i++){
			System.out.println(bestSolutionsNums.get(i));
		}

		System.out.println("\n\nPrinting best Solutions Items");
		for (int i = 0; i < bestSolutionsItems.size(); i++){
			System.out.println(bestSolutionsItems.get(i));
		}
*/
		//System.out.println(bestSolutionsNums.size());
		//System.out.println((String)bestSolutionsNums.get(0));
		int min = 0;
		for (int i = 0; i < numNodes; i++){
			//System.out.println("we in here");
			if (Double.parseDouble((String)bestSolutionsNums.get(i)) < Double.parseDouble((String)bestSolutionsNums.get(min))) {
				min = i;
			}//if
		}//if


		System.out.println("\nYour best knapsack contains: \n" + bestSolutionsItems.get(min));
		System.out.println("\nIt has a cost/benefit ratio of: " + bestSolutionsNums.get(min) + "\n");
/*
		final String DELIMITER = ",";
	  	try {
	  		String line = "";

  			// Create the file reader
  			fileReader = new BufferedReader(new FileReader("received.csv"));

  			// Read the file line by line
  			while ((line = fileReader.readLine()) != null) {
				//System.out.println("Are we in here?");
				// Get all of the tokens available in the line
				// the order goes String mean, double meanValue, String standardDev, value standardDev
				// tokens should be 4 times as big as numNodes
				String[] caught = line.split(DELIMITER);
				for (int i = 0; i < caught.length; i++) {
					tokens.add(caught[i]);
				} 

			}
				
   		    		for (int i = 1; i < tokens.size(); i += 4) {
           				averageMean += Float.parseFloat(tokens.get(i));
		            		averageStandardDev += Double.parseDouble(tokens.get(i+2));
					determineAverages++;
       				} //for
       				averageMean = (float) averageMean/determineAverages;
		        	averageStandardDev = averageStandardDev/determineAverages;
		        	

       			//} // while
		endTime = System.currentTimeMillis();

		System.out.println("The aggregate average cost/benefit: " + String.format("%.4f", averageMean));
       		System.out.println("The aggregate average standard deviation: " + String.format("%.4f", averageStandardDev));

       		fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} // try-catch
		int fileNameLength = args[4].length();
		String fileNameSubString = args[4].substring(0,fileNameLength-4);

		//System.out.println("File name: " + fileNameSubString + args[5] + ".csv");
		//System.out.println("Max cost: " + args[5]);
		//System.out.println("Requirements file: " + args[4]);

		try {
    	    		out = new BufferedWriter(new FileWriter(fileNameSubString+args[5] + ".csv", true));
    	    		out.write("Requirements file," + args[4] + "\n");
			out.write("Max Cost," + args[5] + "\n");
			out.write("Aggregate average cost/benefit ratio," + String.format("%.4f", averageMean) + "\n");
			out.write("Aggregate average standard deviation," + String.format("%.4f", averageStandardDev) + "\n");
			out.write("time (seconds)," + (endTime-startTime)/1000);
    	    		out.close();
    	    	} catch(IOException e) {
    	      	} // try-catch
*/
	} // main










//I want more space
} // HillClimbingServer
