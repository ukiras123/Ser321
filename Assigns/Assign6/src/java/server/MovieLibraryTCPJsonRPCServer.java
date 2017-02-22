package server;

import java.net.*;
import java.io.*;
import java.util.*;

public class MovieLibraryTCPJsonRPCServer extends Thread {
	private Socket sock;
	private int id;
	private MovieLibrarySkeleton skeleton;
	
	public MovieLibraryTCPJsonRPCServer(Socket sock, int id, MovieLibrary movieLibrary) {
		this.sock = sock;
		this.id = id;
		skeleton = new MovieLibrarySkeleton(movieLibrary);
	}
	
	public void run() {
		try {
			OutputStream outSock = sock.getOutputStream();
			InputStream inSock = sock.getInputStream();
			byte clientInput[] = new byte[1024]; // up to 1024 bytes in a message
			int numr = inSock.read(clientInput, 0, 1024);
			
			if (numr != -1) {
				String request = new String(clientInput, 0, numr);
				System.out.println("request is: " + request);
				String response = skeleton.callMethod(request);
				byte clientOut[] = response.getBytes();
				outSock.write(clientOut, 0, clientOut.length);
				
				System.out.println("response is: " + response);
			}
			
			inSock.close();
			outSock.close();
			sock.close();
		} catch(IOException e) {
			System.out.println("I/O exception occurred for the connection:\n" +
					e.getMessage());
		}
	}
	
	public static void main (String args[]) {
		Socket sock;
		MovieLibrary movieLibrary = new MovieLibraryImpl();
		int id = 0;
		
		try {
			if (args.length != 1) {
				System.out.println("Usage: java server.MovieLibraryTCPJsonRPCServer [portNum]");
				System.exit(0);
			}
			
			int portNo = Integer.parseInt(args[0]);
			
			if (portNo <= 1024) {portNo = 8888;}
			
			ServerSocket serv = new ServerSocket(portNo);
			
			// accept client requests. For each request create a new thread to handle
			while (true) {
				System.out.println("MovieLibrary server waiting for connects on port " + portNo);
				sock = serv.accept();
				System.out.println("MovieLibrary server connected to client: " + id);
				MovieLibraryTCPJsonRPCServer serverThread = 
						new MovieLibraryTCPJsonRPCServer(sock, id++, movieLibrary);
				serverThread.start();
			}
		} catch(Exception e) {e.printStackTrace();}
	}
}

