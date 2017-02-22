package client;

import server.MovieDescription;
import server.MovieLibrary;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONArray;

public class MovieLibraryTcpProxy extends Object implements MovieLibrary {
	private static final int buffSize = 4096;
	private static int id = 0;
	private String host;
	private int port;
	
	public MovieLibraryTcpProxy (String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public String callMethod(String method, Object[] params) {
		JSONObject theCall = new JSONObject();
		String ret = "{}";
		
		try {
			System.out.println("Request is: " + theCall.toString());
			
			theCall.put("method", method);
			theCall.put("id", id);
			theCall.put("jsonrpc", "2.0");
			ArrayList<Object> al = new ArrayList();
			
			for (int i = 0; i < params.length; i++) {
				al.add(params[i]);
			}
			
			JSONArray paramsJson = new JSONArray(al);
			theCall.put("params", paramsJson);
			
			Socket sock = new Socket(host, port);
			OutputStream os = sock.getOutputStream();
			InputStream is = sock.getInputStream();
			int numBytesReceived;
			int bufLen = 1024;
			String strToSend = theCall.toString();
			byte bytesReceived[] = new byte[buffSize];
			byte bytesToSend[] = strToSend.getBytes();
			
			os.write(bytesToSend, 0, bytesToSend.length);
			numBytesReceived = is.read(bytesReceived, 0, bufLen);
			ret = new String(bytesReceived, 0, numBytesReceived);
			
			System.out.println("callMethod received from server: " + ret);
			
			os.close();
			is.close();
			sock.close();
		} catch(Exception e) {
			System.out.println("exception in callMethod: " + e.getMessage());
		}
		
		return ret;
	}

}
