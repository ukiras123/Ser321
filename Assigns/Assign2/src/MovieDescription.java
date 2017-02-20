import java.util.ArrayList;

/**
 * Copyright (c) 2017 Robert Beerman,
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
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
   	
   	sb.append("Movie " + title + "\n   Rated " + rated
   		+ "\n   Released " + released + "\n   Runtime " + runtime
   		+ "\n   Plot: " + plot + "\n   Filename " + filename
   		+ "\n   Actors:");
   		
   	for (String actor : actors) {
   		if (!first) {
	   		sb.append(",");
   		}
   		sb.append(" " + actor);
   		first = false;   		
   	}
   	
   	sb.append("\n   Genre:");
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
   
   public static void main(String[] args) {
   	MovieDescription testEmptyDesc = new MovieDescription();
   	System.out.println(testEmptyDesc);
   	
   	ArrayList<String> actorList = new ArrayList<String>();
   	actorList.add("Rob");
   	actorList.add("Shelby");
   	ArrayList<String> genreList = new ArrayList<String>();
   	genreList.add("Comedy");
   	genreList.add("Animation");
   	
   	MovieDescription testFullDesc = new MovieDescription("Minions Puppy","NR","10 Dec 2013",
             "4:16 min","Dave seeing many owners walk their dogs wants a puppy of his own. "+
             "He finds a mini-UFO who becomes his pal. This short film released with Despic"+
             "able Me 2 chronicles how Dave helps the UFO return home.","MinionsPuppy.mp4",
             actorList, genreList);
   	System.out.println(testFullDesc);
   
   }
}
