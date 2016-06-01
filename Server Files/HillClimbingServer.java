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
		LinkedList bestSolutionsNums = new LinkedList();
		LinkedList bestSolutionsItems = new LinkedList();
		startTime = System.currentTimeMillis();

	    // Open a new DatagramSocket, which will be used to send the data.
    	try (DatagramSocket serverSocket = new DatagramSocket()) {
    		try {
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
						char[] part2Chars = part2.toCharArray();
						String part2A = null, part2B = null;
						for (int i = 0; i < part2Chars.length; i++) {
    	    		if (part2Chars[i] == '-') {
    	    			part2A = part2.substring(1, i-1);
								part2B = part2.substring(i+2, part2.length()-1);
    	  			} // if
    	    	} // for
						bestSolutionsItems.add(part2A);
						bestSolutionsNums.add(part2B);
						counter++;
						System.out.println("Recieved " + counter + " of " + numNodes + " responses");
    	    } catch (IOException e) {
    	        System.out.println(e);
    	        e.printStackTrace();
    	    } // catch
    	    if (counter == numNodes) {
    	    	break;
    	    } // if
				} // while
		int min = 0;
		for (int i = 0; i < numNodes; i++){
			if (Double.parseDouble((String)bestSolutionsNums.get(i)) < Double.parseDouble((String)bestSolutionsNums.get(min))) {
				min = i;
			}//if
		}//if
		System.out.println("\nYour best knapsack contains: \n" + bestSolutionsItems.get(min));
		System.out.println("\nIt has a cost/benefit ratio of: " + bestSolutionsNums.get(min) + "\n");
	}//main
} // HillClimbingServer
