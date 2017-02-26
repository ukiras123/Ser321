package server;

import java.net.*;
import java.io.*;
import java.util.*;

public class ThreadedMovieSockServer extends Thread {
	private Socket conn;
	private int id;
	
	public ThreadedMovieSockServer (Socket sock, int id) {
		this.conn = sock;
		this.id = id;
	}
	
	public void run() {
		try {
			File movieFile = null;
			ObjectInputStream in = new ObjectInputStream(conn.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(conn.getOutputStream());
			String fileName = (String)in.readObject();
			
			System.out.println("From client " + id + " get movie file " + fileName);
			
			if (fileName != null && !fileName.isEmpty()) {
				movieFile = new File(System.getProperty("user.dir") + "/MediaFiles/" + fileName);
				
				if (movieFile.exists()) {
					int fileSize = (int)movieFile.length();
					byte[] fileByteArray = new byte[fileSize];
					
					out.writeInt(fileSize);
					BufferedInputStream bis = new BufferedInputStream(
														new FileInputStream(movieFile));
					bis.read(fileByteArray, 0, fileByteArray.length);
					out.write(fileByteArray, 0, fileByteArray.length);
					out.flush();
					bis.close();
				} else {
					System.out.println("File not found! Ensure your media file is in the 'MediaFiles' subdirectory.");
				}
			}
						
			in.close();
			out.close();
			conn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		Socket sock;
		int id = 0;
		int port = 2020;
		
		try {
		/*
			if (args.length != 1) {
				System.out.println("Usage: java ThreadedMovieSocketServer <port num>");
        		System.exit(0);
			}
			
			int port = Integer.parseInt(args[0]);
			
			if (port <= 1024) {
				port = 2020;
			}
			*/
			
			ServerSocket serv = new ServerSocket(port);
			
			while (true) {
				System.out.println("Threaded movie server waiting for connections on port " + port);
				sock = serv.accept();
				System.out.println("Threaded movie server connected to client-" + id);
				ThreadedMovieSockServer serverThread = new ThreadedMovieSockServer(sock, id++);
				serverThread.start();
			}
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
