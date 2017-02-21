package movie;

import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Copyright (c) 2017 Robert Beerman
 *
 * The author grants to the ASU Software Engineering program the right to copy
 * and execute this software for evaluation purposes only.
 * 
 * The included main method is used, with minor modifications, by permission
 * from Timothy Lindquist under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Purpose: This class is entry point for Assignment 3 of Ser321: MovieLibrary.
 *
 * @author Robert Beerman robert.beerman@asu.edu
 *         Arizona State University, UTO
 * @version 01/28/2017
 **/

public class Main {
	public static void main(String[] args) {
		try {
			String moviesFile = "movies.json";
			String endSaveFile = "moviesSave.json";
			MovieLibrary lib1 = new MovieLibraryImpl(moviesFile);
		
			String aTitle = "Frozen";
			System.out.println("Searching for " + aTitle + "...");
			aTitle = aTitle.replaceAll(" ", "+");
					
			MovieDescription omdbMovie = search(aTitle);
			lib1.add(omdbMovie);
		
			System.out.println("Saving library to " + moviesFile + "...");
			if (lib1.saveToFile(moviesFile)) {
				System.out.println("Successfully saved library");
			}
		
			System.out.println("Restoring from " + moviesFile + "...");
			if (lib1.restoreFromFile(moviesFile)) {
				System.out.println("Successfully restored library");
			}
			
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			String[] commandArgs = stdin.readLine().split(" ");
			String command = commandArgs[0];
			String searchTitle = "";
			
			if (commandArgs.length > 1) {
				for (int i = 1; i < commandArgs.length; i++) {
					if (i > 1) {
						searchTitle += "+";
					}
					searchTitle += commandArgs[i];
				}
			}
			
         while(!command.equalsIgnoreCase("end")) {
		     	if (command.equalsIgnoreCase("search")) {
         		MovieDescription newMovie = search(searchTitle);
         		lib1.add(newMovie);
         		searchTitle = "";
         	} else if (command.equalsIgnoreCase("save")) {
         		if (lib1.saveToFile(moviesFile)) {
         			System.out.println("Successfully saved library");
         		}
         	} else if (command.equalsIgnoreCase("restore")) {
         		if (lib1.restoreFromFile(moviesFile)) {
						System.out.println("Successfully restored library");
					}
         	}
         	
         	System.out.println("Enter search aTitle, save, restore, or end");
         	commandArgs = stdin.readLine().split(" ");
         	command = commandArgs[0];
         	if (commandArgs.length > 1) {
					for (int i = 1; i < commandArgs.length; i++) {
						if (i > 1) {
							searchTitle += "+";
						}
						searchTitle += commandArgs[i];
					}
				}
         }
         
         // saves to moviesSave.json on end, per instructions
         lib1.saveToFile(endSaveFile);

		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static MovieDescription search(String title) {
		MovieDescription md = new MovieDescription();
		StringBuffer contentSb = new StringBuffer();
	
		String urlStr;
		StringBuilder sb = new StringBuilder();
		sb.append("http://www.omdbapi.com/?t=");
		sb.append(title);
		sb.append("&y=&plot=short&r=json");
		urlStr = sb.toString();
		
		try {
			URL url = new URL(urlStr);
			URLConnection uc = url.openConnection();
			uc.connect();
		
			if (uc.getContentType().contains("json")) {
				BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
				String inLine;
				
				while((inLine = in.readLine()) != null) {
					contentSb.append(inLine);
				}
				
				in.close();
			} else {
				System.out.println("Bad content type returned");
			}
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		if (contentSb.lastIndexOf("}") == contentSb.length()-1) {
			md = new MovieDescription(contentSb.toString());
		}
		
		return md;
	}
}
