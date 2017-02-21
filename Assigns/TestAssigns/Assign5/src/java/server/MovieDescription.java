package server;

import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

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
 * Purpose: This class is a representation of a movie description developed for
 * Assignment 3 of Ser321: MovieLibrary.
 *
 * @author Robert Beerman robert.beerman@asu.edu
 *         Arizona State University, UTO
 * @version 01/28/2017
 **/

public class MovieDescription {
   private String title, rated, released, runtime, plot, filename;
   private ArrayList<String> genres, actors;

   public MovieDescription() {
      this.title = "";
      this.rated = "";
      this.released = "";
      this.runtime = "";
      this.plot = "";
      this.filename = "";
      this.actors = new ArrayList<String>();
      this.genres = new ArrayList<String>();
   }

   public MovieDescription(String title, String rated, String released, 
			String runtime, String plot, String filename, ArrayList<String> actors,
         ArrayList<String> genre) {
      this.title = title;
      this.rated = rated;
      this.released = released;
      this.runtime = runtime;
      this.plot = plot;
      this.filename = filename;
      this.actors = actors;
      this.genres = genre;
   }
   
   public MovieDescription(String title, String rated, String released, 
			String runtime, String plot, String filename, String actor,
         String genre) {
      this.title = title;
      this.rated = rated;
      this.released = released;
      this.runtime = runtime;
      this.plot = plot;
      this.filename = filename;
      
      actors = new ArrayList<String>();
      actors.add(actor);
     
		genres = new ArrayList<String>();
      genres.add(genre);
   }
   
   public MovieDescription(JSONObject obj) {
   	title = obj.getString("Title");
   	rated = obj.getString("Rated");
   	released = obj.getString("Released");
   	runtime = obj.getString("Runtime");
   	plot = obj.getString("Plot");
   	filename = obj.getString("Filename");
   	
   	actors = new ArrayList<String>();
   	JSONArray actorArray = obj.getJSONArray("Actors");
   	for (int i = 0; i < actorArray.length(); i++) {
   		actors.add(actorArray.get(i).toString());
   	}
   	
   	genres = new ArrayList<String>();
   	JSONArray genreArray = obj.getJSONArray("Genre");
   	for (int i = 0; i < genreArray.length(); i++) {
   		genres.add(genreArray.get(i).toString());
   	}
   }
   
   public MovieDescription(String jsonObjAsString) {
   	JSONObject obj = new JSONObject(jsonObjAsString);
   	
   	title = obj.getString("Title");
   	rated = obj.getString("Rated");
   	released = obj.getString("Released");
   	runtime = obj.getString("Runtime");
   	plot = obj.getString("Plot");
   	filename = title + ".mp4";
   	
   	actors = new ArrayList<String>();
   	String actorsStr = obj.getString("Actors");
   	String[] actorArray = actorsStr.split(",");
   	
   	for (int i = 0; i < actorArray.length; i++) {
   		actorArray[i] = actorArray[i].trim();
   		actors.add(actorArray[i]);
   	}
   	
   	genres = new ArrayList<String>();
   	String genreStr = obj.getString("Genre");
   	String[] genreArray = genreStr.split(",");
   	
   	for (int i = 0; i < genreArray.length; i++) {
   		genreArray[i] = genreArray[i].trim();
   		genres.add(genreArray[i]);
   	}
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }
   
   public String getRated() {
      return rated;
   }
   
   public void setRated(String rated) {
      this.rated = rated;
   }
   
   public String getReleased() {
   	return released;
   }
   
   public void setReleased(String released) {
   	this.released = released;
   }
   
   public String getRuntime() {
      return runtime;
   }
   
   public void setRuntime(String runtime) {
      this.runtime = runtime;
   }
     
   public String getPlot() {
      return plot;
   }
   
   public void setPlot(String plot) {
      this.plot = plot;
   }
   
   public String filename() {
      return filename;
   }
   
   public void setFilename(String filename) {
      this.filename = filename;
   }
   
   public ArrayList<String> getActors() {
      return actors;
   }
   
   public void setActors(ArrayList<String> actors) {
      this.actors = actors;
   }
   
   public void addActor(String actor) {
   	actors.add(actor);
   }
   
   public ArrayList<String> getGenres() {
      return genres;
   }
   
   public void setGenre(ArrayList<String> genres) {
      this.genres = genres;
   }
   
   public void addGenre(String addedGenre) {
      genres.add(addedGenre);
   }
   
   public JSONObject toJson() {
   	JSONObject obj = new JSONObject();
   	
   	obj.put("Title", title);
   	obj.put("Rated", rated);
   	obj.put("Released", released);
   	obj.put("Runtime", runtime);
   	obj.put("Filename", filename);
   	obj.put("Plot", plot);
   	
   	String[] actorArray = new String[actors.size()];
   	for (int i = 0; i < actorArray.length; i++) {
   		actorArray[i] = actors.get(i);
   	}
   	obj.put("Actors", actorArray);
   	
   	
   	String[] genreArray = new String[genres.size()];
   	for (int i = 0; i < genreArray.length; i++) {
   		genreArray[i] = genres.get(i);
   	}
   	obj.put("Genre", genreArray);
   	
   	return obj;
   }
   
   public String toString() {
   	StringBuilder sb = new StringBuilder();
   	boolean first = true;
   	
   	sb.append("Movie: " + title + "\n   Rated: " + rated
   		+ "\n   Released: " + released + "\n   Runtime: " + runtime
   		+ "\n   Plot: " + plot + "\n   Filename: " + filename
   		+ "\n   Actors:");
   		
   	for (String actor : actors) {
   		if (!first) {
	   		sb.append(",");
   		}
   		sb.append(" " + actor);
   		first = false;   		
   	}
   	
   	sb.append("\n   Genre(s):");
   	first = true;
   	
   	for (String genre : genres) {
   		if (!first) {
	   		sb.append(",");
   		}
   		sb.append(" " + genre);
   		first = false;
   	}
   
   	return  sb.toString();
   }
}
