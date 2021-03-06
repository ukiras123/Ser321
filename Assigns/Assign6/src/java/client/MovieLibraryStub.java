package client;


import java.net.*;
import java.io.*;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONArray;

public class MovieLibraryStub extends Object {
	private static final int buffSize = 4096;
	private static int id = 0;
	private String host;
	private int port;
	
	public MovieLibraryStub (String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public String callMethod(String method, Object[] params) {
		JSONObject theCall = new JSONObject();
		String ret = "{}";
		
		try {
			theCall.put("method", method);
			theCall.put("id", id);
			theCall.put("jsonrpc", "2.0");
			ArrayList<Object> al = new ArrayList<Object>();
			
			for (int i = 0; i < params.length; i++) {
				al.add(params[i]);
			}
			
			JSONArray paramsJson = new JSONArray(al);
			theCall.put("params", paramsJson);
			
			System.out.println("Request is: " + theCall.toString());
			
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
	
	synchronized public JSONArray getTitles() {
		String[] ret = new String[]{};
		String result = callMethod("getTitles", new Object[0]);
		System.out.println("result of getTitles is: " + result);
		JSONObject res = new JSONObject(result);
		JSONArray titlesJson = res.optJSONArray("result");
		
		return titlesJson;
	}
	
	synchronized public boolean add(JSONObject movieDesc) {
		boolean movieAdded = false;
		String result = callMethod("add", new Object[]{movieDesc});
		JSONObject jsonResult = new JSONObject(result);
		
		movieAdded = jsonResult.optBoolean("result", false);
		
		return movieAdded;
	}
	
	synchronized public boolean remove(String title) {
		boolean movieRemoved = false;
		String result = callMethod("remove", new Object[]{title});
		
		System.out.println("result of remove is: " + result);
		
		JSONObject jsonResult = new JSONObject(result);
		movieRemoved = jsonResult.getBoolean("result");
		
		return movieRemoved;
	}
	
	synchronized public JSONObject get(String title) {
		String result = callMethod("get", new Object[]{title});
		System.out.println("result of get from server is: " + result);
		JSONObject jsonRes = new JSONObject(result);
		return jsonRes;		
	}

}
