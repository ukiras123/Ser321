package server;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.io.FileInputStream;
import java.io.PrintWriter;

import org.json.JSONString;
import org.json.JSONObject;
import org.json.JSONTokener;

//import edu.asu.ser.jsonrpc.common.JsonRpcException;

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
 * Purpose: This class is a representation of a movie library developed for
 * Assignment 3 of Ser321: MovieLibrary.
 *
 * @author Robert Beerman robert.beerman@asu.edu
 *         Arizona State University, UTO
 * @version 01/28/2017
 **/

public class MovieLibraryImpl implements MovieLibrary {
	private HashSet<MovieDescription> library;
	
	public MovieLibraryImpl() {
		System.out.println("creating a new Movie Library");
		library = new HashSet<MovieDescription>();
		try{
			this.restoreFromFile("movies.json");
		} catch(Exception e) {
			System.out.println("exception" + e.getMessage());
		}
	}
	
	public MovieLibraryImpl(String jsonFile) {
		library = new HashSet<MovieDescription>();
		
		try{
			FileInputStream in = new FileInputStream(jsonFile);
			JSONObject obj = new JSONObject(new JSONTokener(in));
			
			String[] movies = JSONObject.getNames(obj);
			for (int i = 0; i < movies.length; i++) {
				MovieDescription movieDesc = 
					new MovieDescription((JSONObject)obj.getJSONObject(movies[i]));
								
				library.add(movieDesc);
				System.out.println("Successfully added " + movieDesc.getTitle() + " to library");
			}
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public boolean restoreFromFile(String restoreFileName) {
		library.clear();
		
		try{
			FileInputStream in = new FileInputStream(restoreFileName);
			JSONObject obj = new JSONObject(new JSONTokener(in));
			
			String[] movies = JSONObject.getNames(obj);
			for (int i = 0; i < movies.length; i++) {
				MovieDescription movieDesc = 
					new MovieDescription((JSONObject)obj.getJSONObject(movies[i]));
								
				library.add(movieDesc);
				System.out.println("Successfully added " + movieDesc.getTitle() + " to library");
			}
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean saveToFile(String saveFileName) {
		PrintWriter out;
		
		try {
			out = new PrintWriter(saveFileName);
			out.println(toJSONString());
			out.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
			e.printStackTrace();
		}
	
		return true;
	}
	
	public boolean add(MovieDescription aClip) {
		if (aClip != null) {			
			library.add(aClip);
			System.out.println("Successfully added " + aClip.getTitle() + " to library");
			return true;
		}
		
		return false;
	}
	
	public boolean remove(String aTitle) {
		MovieDescription movie = get(aTitle);
		
		if (movie != null) {
			library.remove(movie);
			return true;
		}
		
		return false;
	}
	
	public MovieDescription get(String aTitle) {
		Iterator<MovieDescription> iter = library.iterator();
		
		while (iter.hasNext()) {
			MovieDescription movie = iter.next();
			if (aTitle.equals(movie.getTitle())) {
				return movie;
			}
		}
		
		return null;
	}
	
	public String[] getTitles() {
		String[] titles = new String[library.size()];
		Iterator<MovieDescription> iter = library.iterator();
		int position = 0;
		
		while (iter.hasNext()) {
			titles[position] = iter.next().getTitle();
			position++;
		}
			
		return titles;
	}
	
	public String toJSONString() {
		String ret;
		JSONObject obj = new JSONObject();
		
		for (MovieDescription movie : library) {
			obj.put(movie.getTitle(), movie.toJson());
		}
		
		ret = obj.toString();
		
		return ret;
	}
}
