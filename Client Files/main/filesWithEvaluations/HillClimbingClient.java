import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.net.Socket;
import java.io.DataOutputStream;

@SuppressWarnings("unchecked")

public class HillClimbingClient {

	// This example is from: http://examples.javacodegeeks.com/core-java/net/multicastsocket-net/java-net-multicastsocket-example/

	private static String INET_ADDR = "";// "224.0.0.35"; // specify what IP to be multicasting on
	private static int PORT = 0;//12345; // specify port number

	public static void main(String[] args) throws UnknownHostException {
		String msg = "";
    	final String DELIMITER = ",";
    	DataOutputStream output = null;
    	Socket MyClient = null;
    	String in = null;
    	String result = "";
    	String serverLocation = "";
    	int iterations = 0;
    	String tokens[] = null;
    	Knapsack myKnapsack = new Knapsack();

    	try {
    		INET_ADDR = args[0];
    		PORT = Integer.parseInt(args[1]);
    		iterations = Integer.parseInt(args[2]);
    	} catch (Exception e) {
      		System.out.println("Necessary arguments: 1) INET_ADDR, 2) port number, 3) number of" + " times you want the loop to run");
			System.exit(0);
		}

    	// Get the address that we are going to connect to.
    	InetAddress address = InetAddress.getByName(INET_ADDR); // pass ip address variable into InetAddress

    	// Create a buffer of bytes, which will be used to store the incoming bytes containing the information from the server.
    	// Since the message is small here, 256 bytes should be enough.
    	byte[] buf = new byte[256];

    	// Create a new Multicast socket (that will allow other sockets/programs
    	// to join it as well.
    	try (MulticastSocket clientSocket = new MulticastSocket(PORT)) {
    		// Join the Multicast group.
    		clientSocket.joinGroup(address);

	    	while(true) {
    			// Receive the information and print it.
    			DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
    			clientSocket.receive(msgPacket);
	
		        msg = new String(buf, 0, buf.length);
    		    System.out.println("The socket received this message: " + msg);
    		    tokens = msg.split(DELIMITER);
    		    break;
    		}
		    serverLocation = tokens[0];
    	  	System.out.println("Calling method to get result...");
					// Call knapsack method to run
    	  	result = myKnapsack.mainCopy("../data/" + tokens[1], Double.parseDouble(tokens[2]), iterations);
    
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} // try-catch

		try {
    		// open socket to localhost, port stated
        	MyClient = new Socket(serverLocation, PORT+1);
    	} catch(IOException e) {
			System.out.println(e);
		} // try-catch
		if (MyClient != null) {
			try {
				byte[] data = result.getBytes("UTF-8");
		        // make an output stream for the socket
        		output = new DataOutputStream(MyClient.getOutputStream());
        		output.write(data);
				output.close();
      		} catch(IOException e) {
        		System.out.println(e);
      		} // try-catch
		} // if
	} // main
} // HillClimbingClient
