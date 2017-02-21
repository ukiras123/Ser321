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

public interface MovieLibrary {
	boolean add(MovieDescription aClip);
	boolean remove(String aTitle);
	MovieDescription get(String aTitle);
	String[] getTitles();
}
