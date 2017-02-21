package client;

import java.io.*;
import java.util.*;
import java.net.URL; 

import org.json.JSONObject;
import org.json.JSONArray;

import server.MovieDescription;
import server.MovieLibrary;
import server.MovieLibraryHttpProxy;

public class MovieLibraryClient extends Object {
	public static final boolean debugOn = false;
	public String serviceURL;
	public JsonRpcRequestViaHttp server;
	public static int id = 0;
	
	public MovieLibraryClient (String serviceURL) {
		this.serviceURL = serviceURL;
		
		try {
			this.server = new JsonRpcRequestViaHttp(new URL(serviceURL));
		} catch(Exception e) {
			System.out.println("Malformed URL " + e.getMessage());
		}
	}
	
	
	
	private static void debug(String message) {
		if (debugOn) {
			System.out.println("debug: " + message);
		}
	}
	
	public static void main(String[] args) {
		String host = "localhost";
		String port = "8080"
		
		try {
			if (args.length >= 2) {
				host = args[0];
				host = args[1];
			}
			
			String url = "http://" + host + ":" + port + "/";
			System.out.println("Opening connection to: " + url);
			MovieLibrary library = (MovieLibrary)new MovieLibraryHttpProxy(new URL(url));
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter end or {add|get|getTitles|remove} followed by args>");
			String inStr = stdin.readLine()
			StringTokenizer st = new StringTokenizer(inStr);
			String opn = st.nextToken();
			while (!opn.equalsIgnoreCase("end")) {
				if (opn.equalsIgnoreCase("add")) {
					//implement this
				} else if (opn.equalsIgnoreCase("get")) {
					
				} else if (opn.equalsIgnoreCase("getTitles")) {
					//implement this
				}  else if (opn.equalsIgnoreCase("remove")) {
					//implement this
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
