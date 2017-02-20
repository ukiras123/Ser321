package movie;

import org.json.JSONObject;

public class Main {
	public static void main(String[] args) {
		try {
			MovieLibraryImpl myLib = new MovieLibraryImpl("movies.json");
		} catch(Exception e) {
			System.out.println("exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
