import java.util.ArrayList;

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
 * Assignment 2 of Ser321: MovieLibrary.
 *
 * @author Robert Beerman robert.beerman@asu.edu
 *         Arizona State University, UTO
 * @version 01/21/2017
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
}
