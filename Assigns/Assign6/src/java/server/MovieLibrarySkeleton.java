package server;

import java.net.*;
import java.io.*;
import java.util.*;
import org.json.JSONObject;
import org.json.JSONArray;

public class MovieLibrarySkeleton extends Object {
	MovieLibrary movieLibrary;
	
	public MovieLibrarySkeleton (MovieLibrary movieLibrary) {
		this.movieLibrary = movieLibrary;
	}
	
	public String callMethod(String request) {
		JSONObject result = new JSONObject();
		
		try {
			JSONObject theCall = new JSONObject(request);
			System.out.println("Request is: " + theCall.toString());
			
			String method = theCall.getString("method");
			int id = theCall.getInt("id");
			JSONArray params = null;
			
			if (!theCall.isNull("params")) {
				params = theCall.getJSONArray("params");
			}
			
			result.put("id", id);
			result.put("jsonrpc", "2.0");
			
			if (method.equals("add")) {
				// add a movie description
			} else if (method.equals("remove")) {
				String title = params.getString(0);
				boolean removeResult = true;
				try {
					removeResult = movieLibrary.remove(title);
				} catch(Exception e) {
					System.out.println("Remove operation failed. Movie " + title + " not found.");
				} finally {
					result.put("result", removeResult);
				}
			} else if (method.equals("get")) {
				String title = params.getString(0);
				MovieDescription movie = movieLibrary.get(title);
				JSONObject movieJson = movie.toJson();
				System.out.println("get request found: " + movieJson.toString());
				result.put("result", movieJson);
			} else if (method.equals("getTitles")) {
				String[] titles = movieLibrary.getTitles();
				JSONArray resArr = new JSONArray();
				
				for (int i = 0; i < titles.length; i++) {
					resArr.put(titles[i]);
				}
				
				System.out.println("getTitles request found: " + resArr.toString());
				result.put("result", resArr);
			} else {
				System.out.println("Unable to match method: " + method + ". Returning 0.");
				result.put("result", 0.0);
			}
		} catch(Exception e) {
			System.out.println("exception in callMethod: " + e.getMessage());
		}
	
		return result.toString();
	}

}
