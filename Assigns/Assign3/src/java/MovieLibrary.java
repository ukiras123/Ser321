package movie;

/**
<<<<<<< HEAD
 * Copyright (c) 2017 Robert Beerman,
=======
 * Copyright (c) 2017 Robert Beerman
>>>>>>> 8fa2ddab5d46a08d820f8a7388a1d94d23c026be
 *
 * The author grants to the ASU Software Engineering program the right to copy
 * and execute this software for evaluation purposes only.
 * 
 * The included main method is used, with minor modifications, by permission
 * from Timothy Lindquist under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 *
<<<<<<< HEAD
 * Purpose: This class is a representation of a movie description developed for
 * Assignment 2 of Ser321: MovieLibrary.
 *
 * @author Robert Beerman robert.beerman@asu.edu
 *         Arizona State University, UTO
 * @version 01/21/2017
=======
 * Purpose: This interface represents the contract for implementing a MovieLibrary
 * class for Assignment 3 of Ser321: MovieLibrary.
 *
 * @author Robert Beerman robert.beerman@asu.edu
 *         Arizona State University, UTO
 * @version 01/28/2017
>>>>>>> 8fa2ddab5d46a08d820f8a7388a1d94d23c026be
 **/

public interface MovieLibrary {
	boolean add(MovieDescription aClip);
	boolean remove(String aTitle);
	MovieDescription get(String aTitle);
	String[] getTitles();
<<<<<<< HEAD
=======
	boolean restoreFromFile(String restoreFileName);
	boolean saveToFile(String saveFileName);
	String toJSONString();
>>>>>>> 8fa2ddab5d46a08d820f8a7388a1d94d23c026be
}
