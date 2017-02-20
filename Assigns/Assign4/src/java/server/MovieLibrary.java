package server;

import edu.asu.ser.jsonrpc.common.JsonRpcException;

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
 * Purpose: This interface represents the contract for MovieLibrary objects
 * developed for Assignment 4 of Ser321: MovieLibrary http server on Raspberry Pi
 *
 * @author Robert Beerman robert.beerman@asu.edu
 *         Arizona State University, UTO
 * @version 02/10/2017
 **/

public interface MovieLibrary {
	public boolean add(MovieDescription aClip) throws JsonRpcException;
	public boolean remove(String aTitle) throws JsonRpcException;
	public MovieDescription get(String aTitle) throws JsonRpcException;
	public String[] getTitles() throws JsonRpcException;
	public boolean restoreFromFile(String restoreFileName) throws JsonRpcException;
	public boolean saveToFile(String saveFileName) throws JsonRpcException;
	public String toJSONString() throws JsonRpcException;
}
