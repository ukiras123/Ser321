package server;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
<<<<<<< HEAD
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.IOException;

import org.json.JSONString;
=======
import org.json.JSONString;
import java.io.FileInputStream;
import java.io.PrintWriter;

>>>>>>> 8fa2ddab5d46a08d820f8a7388a1d94d23c026be
import org.json.JSONObject;
import org.json.JSONTokener;

import edu.asu.ser.jsonrpc.common.JsonRpcException;
import edu.asu.ser.jsonrpc.server.HttpServer;

<<<<<<< HEAD
/**
 * Copyright (c) 2017 Robert Beerman,
 *
 * The author grants to the ASU Software Engineering program the right to copy
 * and execute this software for evaluation purposes only.
 * 
 * The included main method is used, with minor modifications, by permission
 * from Timothy Lindquist under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Purpose: This class is representation of a MovieLibrary http server
 * Assignment 4 of Ser321: MovieLibrary http server on Raspberry Pi
 *
 * @author Robert Beerman robert.beerman@asu.edu
 *         Arizona State University, UTO
 * @version 02/10/2017
 **/
=======
import java.io.IOException;
>>>>>>> 8fa2ddab5d46a08d820f8a7388a1d94d23c026be

class MovieLibraryHttpServer extends Object implements MovieLibrary {
	private HashSet<MovieDescription> library;

	public MovieLibraryHttpServer() {
		System.out.println("creating a new Movie Library");
		library = new HashSet<MovieDescription>();
		try{
			this.restoreFromFile("movies.json");
		} catch(Exception e) {
			System.out.println("exception" + e.getMessage());
		}
	}
	
	public boolean restoreFromFile(String restoreFileName) throws JsonRpcException {
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
			return false;
		}
		
		return true;
	
	}
	
	public boolean saveToFile(String saveFileName) throws JsonRpcException {
		PrintWriter out;
		
		try {
			out = new PrintWriter(saveFileName);
			out.println(toJSONString());
			out.close();
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	
		return true;
	}
	
	public boolean add(MovieDescription aClip) throws JsonRpcException{
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
	
	public static void main(String[] args) throws IOException {
		String port = "8080";
		if (args.length > 0) {
			port = args[0];
		}
		
		HttpServer server = new HttpServer(
			new MovieLibraryHttpServer(), Integer.parseInt(port));
			server.start();
		
	}
}

