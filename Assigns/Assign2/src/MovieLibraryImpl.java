import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

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

public class MovieLibraryImpl implements MovieLibrary {
	private HashSet<MovieDescription> library;
	
	public MovieLibraryImpl() {
		library = new HashSet<MovieDescription>();
	}
	
	public boolean add(MovieDescription aClip) {
		if (aClip != null) {			
			library.add(aClip);
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
}
