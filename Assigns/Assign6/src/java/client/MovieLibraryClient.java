package client;

import java.io.*;
import java.util.*;
import java.net.URL; 

import org.json.JSONObject;
import org.json.JSONArray;

import server.MovieDescription;
import server.MovieLibrary;
import client.MovieLibraryStub;

public class MovieLibraryClient extends Object {
	public static final boolean debugOn = false;
		
	private static void debug(String message) {
		if (debugOn) {
			System.out.println("debug: " + message);
		}
	}
	
	public static void main(String[] args) {
		String host = "localhost";
		String port = "8888";
		
		try {
			if (args.length >= 2) {
				host = args[0];
				host = args[1];
			}
			
			String url = "http://" + host + ":" + port + "/";
			System.out.println("Opening connection to: " + url);
			
			MovieLibraryStub movieLibrary = 
					(MovieLibraryStub)new MovieLibraryStub(host, Integer.parseInt(port));
					
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter end or {add|get|getTitles|remove} followed by args>");
			String inStr = stdin.readLine();
			StringTokenizer st = new StringTokenizer(inStr);
			String opn = st.nextToken();
			
			while (!opn.equalsIgnoreCase("end")) {
				if (opn.equalsIgnoreCase("add")) {
				// TODO implement this
					System.out.println("add method not yet implemented in client");
				} else if (opn.equalsIgnoreCase("get")) {
				// TODO implement this
					System.out.println("get method not yet implemented in client");
				} else if (opn.equalsIgnoreCase("getTitles")) {
					String[] result = movieLibrary.getTitles();
					System.out.print("The library has the following movies: ");
					
					for (int i = 0; i < result.length; i++) {
						System.out.print(result[i] + ", ");
					}
					System.out.println();
				}  else if (opn.equalsIgnoreCase("remove")) {
					// TODO implement this
					System.out.println("remove method not yet implemented in client");
				}
				
				System.out.print("Enter end or {add|get|getTitles|remove} followed by args>");
				inStr = stdin.readLine();
            st = new StringTokenizer(inStr);
            opn = st.nextToken();
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Oops, you didn't enter the right stuff");
		}
	}

}
