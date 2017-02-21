package movie;

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
 * Purpose: This interface represents the contract for implementing a MovieLibrary
 * class for Assignment 3 of Ser321: MovieLibrary.
 *
 * @author Robert Beerman robert.beerman@asu.edu
 *         Arizona State University, UTO
 * @version 01/28/2017
 **/

public interface MovieLibrary {
	boolean add(MovieDescription aClip);
	boolean remove(String aTitle);
	MovieDescription get(String aTitle);
	String[] getTitles();
	boolean restoreFromFile(String restoreFileName);
	boolean saveToFile(String saveFileName);
	String toJSONString();
}
