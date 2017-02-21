package movie;

import java.util.ArrayList;
<<<<<<< HEAD
=======
import org.json.JSONObject;
import org.json.JSONArray;
>>>>>>> 8fa2ddab5d46a08d820f8a7388a1d94d23c026be

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
<<<<<<< HEAD
 * Assignment 2 of Ser321: MovieLibrary.
 *
 * @author Robert Beerman robert.beerman@asu.edu
 *         Arizona State University, UTO
 * @version 01/21/2017
=======
 * Assignment 3 of Ser321: MovieLibrary.
 *
 * @author Robert Beerman robert.beerman@asu.edu
 *         Arizona State University, UTO
 * @version 01/28/2017
>>>>>>> 8fa2ddab5d46a08d820f8a7388a1d94d23c026be
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
<<<<<<< HEAD
=======
   
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
>>>>>>> 8fa2ddab5d46a08d820f8a7388a1d94d23c026be

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
   
<<<<<<< HEAD
=======
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
   
>>>>>>> 8fa2ddab5d46a08d820f8a7388a1d94d23c026be
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
<<<<<<< HEAD
   
   public static void main(String args[]) {
      try {
         MovieLibraryImpl myLib = new MovieLibraryImpl();
         MovieDescription puppy = new MovieDescription("Minions Puppy","NR","10 Dec 2013",
             "4:16 min","Dave seeing many owners walk their dogs wants a puppy of his own. "+
             "He finds a mini-UFO who becomes his pal. This short film released with Despic"+
             "able Me 2 chronicles how Dave helps the UFO return home.","MinionsPuppy.mp4",
             "Dave","Animation");
         puppy.addGenre("Family");
         puppy.addGenre("Cartoon");
         puppy.addActor("Gru");
         boolean test = myLib.add(puppy);
         
         if (test) {
         	System.out.println("Added: "+puppy.toString());
        	}
        	
         MovieDescription bananaSong = new MovieDescription("Minions Banana Song","PG",
                                                            "12 Dec 2015","3 min",
             "Banana is a song sung by The Minions in the teaser trailer of Despicable Me 2. "+
             "It is a parody of the Beach Boys Barbara Ann. One minion gets annoyed by "+
             "another, most likely Stuart, who keeps on playing his party horn while they "+
             "are singing. So, at the end, he punches Stuart.","MinionsBananaSong.mp4",
             "Dave","Animation");
         bananaSong.addGenre("Family");
         bananaSong.addGenre("Cartoon");
         bananaSong.addActor("Gru");
         
         test = myLib.add(bananaSong);
         
         bananaSong = myLib.get("Minions Banana Song");
         System.out.println("Used library get to fetch: "+bananaSong.toString());
         System.out.println("After adding two movies, the library contains the titles: ");
         System.out.print("   ");
         String[] mTitles = myLib.getTitles();
         
         for(int i=0; i<mTitles.length; i++){
            System.out.print(mTitles[i]+((i==mTitles.length-1)?"":", "));
         }
         System.out.println();
         
         test = myLib.remove(bananaSong.getTitle());
         
         System.out.println("Movie titles after removing Minions Banana Song the titles are:");
         System.out.print("   ");
         mTitles = myLib.getTitles();
         StringBuffer sb = new StringBuffer();
         
         for (int i=0; i<mTitles.length; i++){
            sb.append(mTitles[i]+((i==mTitles.length-1)?"":", "));
         }
         
         System.out.println(sb.toString());
      }catch (Exception e) {
         System.out.println("Oops, you didn't enter the right stuff");
      }
   }
=======
>>>>>>> 8fa2ddab5d46a08d820f8a7388a1d94d23c026be
}
